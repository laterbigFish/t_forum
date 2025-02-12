package com.example.forum.service.impl;

import com.example.forum.common.Result;
import com.example.forum.common.dao.BoardMapper;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.exception.ForumException;
import com.example.forum.common.model.Board;
import com.example.forum.service.IBoardService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class BoardServiceImpl implements IBoardService {

    @Resource
    private BoardMapper boardMapper;
    @Override
    public List<Board> selectByNum( Integer num ){
       if(num<=0){
           log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
           throw new ForumException(Result.FAIL(ResultCode.FAILED_PARAMS_VALIDATE));
       }

        return boardMapper.selectByNum(num);
    }

    @Override
    public void addOneArticleCountById( Long id ){
        if(id==null || id<=0){

            throw new ForumException(Result.FAIL(ResultCode.FAILED_BOARD_COUNT.toString()));
        }
        //在这里不使用动态sql
        Board board = boardMapper.selectByPrimaryKey(id);

        if(board==null)  throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));

        board.setArticleCount(board.getArticleCount()+1);

        int i = boardMapper.updateByPrimaryKey(board);//通过id进行判断

        if(i!=1) throw new ForumException(Result.FAIL(ResultCode.FAILED));

    }

    @Override
    public Board selectByPrimaryKey( Long id ){
        if(id==null || id<=0){
            log.warn("id不满足条件{}",ResultCode.FAILED_PARAMS_VALIDATE);
            throw new ForumException(Result.FAIL(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        //返回结果 至于是不是null 交给前端处理
        return boardMapper.selectByPrimaryKey(id);
    }

    @Override
    public void subtractArticleCountById( Long id ){

        if(id==null || id<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        Board board = boardMapper.selectByPrimaryKey(id);

        if(board==null) throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));

        if(board.getArticleCount()==0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        board.setArticleCount(board.getArticleCount()-1);

//        if(board.getArticleCount()<0)   board.setArticleCount(0);
        int i = boardMapper.updateByPrimaryKey(board);

        if(i!=1) throw new ForumException(Result.FAIL(ResultCode.FAILED));

    }

}
