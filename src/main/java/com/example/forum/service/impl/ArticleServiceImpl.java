package com.example.forum.service.impl;

import com.example.forum.common.Result;
import com.example.forum.common.dao.ArticleMapper;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.exception.ForumException;
import com.example.forum.common.model.Article;
import com.example.forum.common.model.Board;
import com.example.forum.common.model.User;
import com.example.forum.service.IArticleService;
import com.example.forum.service.IBoardService;
import com.example.forum.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;  //使用这种方式 要小心 不能进行循环引用
    @Resource
    private IBoardService iBoardService;
    @Resource
    private IUserService iUserService;

    @Transactional

    @Override
    public void create( Article article ){ //此处article.getId()并不需要进行判断
        if(article==null ||
                !StringUtils.hasLength(article.getContent()) ||
                !StringUtils.hasLength(article.getTitle())||
                article.getBoardld()==null||
                article.getUserId()==null){
                log.warn("参数校验失败喽{}",ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ForumException(Result.FAIL(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        article.setVisitCount(0);
        article.setReplyCount(0);
        article.setLikeCount(0);
        article.setDeleteState((byte) 0);
        article.setState((byte) 0);
        Date date = new Date();
        article.setCreateTime(date);
        article.setUpdateTime(date);
        //写入数据库
        int insert = articleMapper.insert(article);

        if(insert<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        //更新用户发帖数
        User user = iUserService.selectById(article.getId()); //获取发布该文章的用户的信息
        if(user==null){
            log.warn("发帖失败{}", ResultCode.FAILED.toString());
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }
        iUserService.addOneArticleCountById(article.getUserId());

        Board board = iBoardService.selectByPrimaryKey(article.getBoardld());

        if(board==null){
            log.warn("该板块不存在{}",ResultCode.FAILED_NOT_EXISTS);
            throw new ForumException(Result.FAIL(ResultCode.FAILED_CREATE));
        }
        iBoardService.addOneArticleCountById(article.getBoardld());
        log.info ("{}发送帖子成功", ResultCode.SUCCESS.toString());
    }

    @Override
    public List<Article> selectAllByUser(){
        //结果是谁用谁校验
        List<Article> articles = articleMapper.selectAllByUser();
        return articles;
    }

    @Override
    public List<Article> selectAllByBoardId( Long boardId ){
         if(boardId==null || boardId<=0){
             log.warn(ResultCode.FAILED.toString());
             throw new ForumException(Result.FAIL(ResultCode.FAILED));
         }

        Board board = iBoardService.selectByPrimaryKey(boardId);

        if(board==null){
            log.warn("板块不存在");
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }

        List<Article> articles = articleMapper.selectAllByBoardId(boardId);

        if(articles==null) articles = new ArrayList<>();

        return articles;
    }

    @Override
    public Article selectDetailById( Long id ){

        if(id==null || id<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        Article article = articleMapper.selectDetailById(id);

        if(article==null) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        // 增加访问数
        article.setVisitCount(article.getVisitCount()+1);
        article.setDeleteState((byte) 0);
        int i = articleMapper.updateByPrimaryKey(article);

        if(i!=1) throw new ForumException(Result.FAIL(ResultCode.ERROR_SERVICES));


        article = articleMapper.selectDetailById(id); //返回的是最新的数据

        return article;
    }

    @Override
    public void modify( Long id, String title, String content ){
        if(id==null || id<=0 || !StringUtils.hasLength(title) || !StringUtils.hasLength(content)){
            throw new ForumException(Result.FAIL(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        Article article =  articleMapper.selectDetailById(id);

        if(article==null) {
            throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));
        }

        if(article.getDeleteState()==null || article.getDeleteState()!=(byte)1) article.setDeleteState((byte) 0);
        article.setTitle(title);
        article.setContent(content);
        article.setUpdateTime(new Date()); //更新时间
        int i = articleMapper.updateByPrimaryKey(article);

        if(i!=1) throw new ForumException(Result.FAIL(ResultCode.FAILED));

    }

    @Override
    public Article selectByPrimaryKey( Long id ){
        if(id==null || id<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED_PARAMS_VALIDATE));

        Article article = articleMapper.selectByPrimaryKey(id);  //这种查找方式没有任何表关联

        if(article==null) throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));

        return article;
    }

    @Override
    public void thumbsUpById( Long id ){

        if(id == null || id<=0) throw  new ForumException(Result.FAIL(ResultCode.FAILED_PARAMS_VALIDATE));

        Article article =  articleMapper.selectByPrimaryKey(id);


        if(article==null) throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));

        if(article.getDeleteState()==1) throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));

        if(article.getDeleteState()==null || article.getDeleteState()!=(byte)1) article.setDeleteState((byte)0);

        //进行点赞数的更新
        article.setLikeCount(article.getLikeCount()+1);

        articleMapper.updateByPrimaryKey(article);
    }

    @Override
    public void deleteById( Long id ){

        if(id==null || id<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        Article article = articleMapper.selectByPrimaryKey(id);

        if(article==null) throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));

        //如果已经被删除了那么就不用进行后续操作了

        if(article.getDeleteState()!=null && article.getDeleteState()==1) throw new ForumException(Result.FAIL(ResultCode.FAILED));
        article.setDeleteState((byte) 1);

        //进行进行逻辑删除
        int i = articleMapper.updateByPrimaryKey(article);

        if(i!=1) throw  new ForumException(Result.FAIL(ResultCode.FAILED));
        Board board = iBoardService.selectByPrimaryKey(article.getBoardld());

        if(board==null)  throw new ForumException(Result.FAIL(ResultCode.FAILED));
        //随后进行 板块 和用户的操作
        iBoardService.subtractArticleCountById(article.getBoardld());

        User user = iUserService.selectById(article.getUserId());

        if(user==null) throw  new ForumException(Result.FAIL(ResultCode.FAILED));
        iUserService.subtractArticleCountById(article.getUserId());


    }

    @Override
    public void addArticleReply( Long id ){

        if(id==null || id<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        Article article = articleMapper.selectByPrimaryKey(id);

        if(article==null) throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));

        //位于次电脑上的一个Bug

        if(article.getDeleteState()==null || article.getDeleteState()!=1) article.setDeleteState((byte) 0);

        if(article.getDeleteState()==1) throw new ForumException(Result.FAIL(ResultCode.FAILED_FORBIDDEN));

        if(article.getState()==1) throw new ForumException(Result.FAIL(ResultCode.FAILED_FORBIDDEN));

        article.setReplyCount(article.getReplyCount()+1);

        int i = articleMapper.updateByPrimaryKey(article);

        if(i!=1) throw new ForumException(Result.FAIL(ResultCode.FAILED));

    }

    @Override
    public List<Article> selectByUserId( Long userId ){

        if(userId==null || userId<=0){
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }

        User user = iUserService.selectById(userId);

        if(user==null){
            throw new ForumException(Result.FAIL(ResultCode.FAILED_USER_NOT_EXISTS));
        }
        //查询文章
        List<Article> articles = articleMapper.selectByUserId(user.getId());

        return articles;
    }

}
