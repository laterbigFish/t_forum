package com.example.forum.controller;

import com.example.forum.common.Result;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.exception.ForumException;
import com.example.forum.common.model.Board;
import com.example.forum.service.IBoardService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/board")
@RestController
public class BoardController {
    @Value("${forum.index.board-num:9}")
    private Integer indexBoardNum;
    /**
     * 查询首页板块列表
     * @return
     */
    @Resource
    private IBoardService iBoardService;
    @GetMapping("/topList")
    public Result<List<Board>> topList(){
        log.info("配置首页板块个数为{}",indexBoardNum);
        List<Board> boards = iBoardService.selectByNum(indexBoardNum);

        if(boards==null) boards = new ArrayList<>();

        return Result.SUCCESS(boards);
    }

    @GetMapping("/getById")

    public Result<Board> getByKey(@RequestParam("id")  @NotNull Long id){

        Board board = iBoardService.selectByPrimaryKey(id);

        if(board==null || board.getDeleteState()==1){
            throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));
        }
        //返回结果
        return Result.SUCCESS(board);
    }

}
