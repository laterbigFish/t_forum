package com.example.forum.service.impl;

import com.example.forum.common.Result;
import com.example.forum.common.dao.ArticleReplyMapper;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.exception.ForumException;
import com.example.forum.common.model.Article;
import com.example.forum.common.model.ArticleReply;
import com.example.forum.service.IArticleReplyService;
import com.example.forum.service.IArticleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ArticleReplyServiceImpl implements IArticleReplyService {
    @Resource
    private ArticleReplyMapper articleReplyMapper;
    @Resource
    private IArticleService articleService;
    @Override
    public void create( ArticleReply articleReply ){

        if(articleReply==null || articleReply.getArticleId()==null
        || articleReply.getPostUserId()==null || articleReply.getContent()==null) throw new ForumException(Result.FAIL(ResultCode.ERROR_IS_NULL));

        //设置一些基本信息
        Date date = new Date();

        articleReply.setState((byte)0);
        articleReply.setReplyId(null);
        articleReply.setReplyUserId(null);
        articleReply.setLikeCount(0);
        articleReply.setDeleteState((byte)0);
        articleReply.setCreateTime(date);
        articleReply.setUpdateTime(date);

        int insert = articleReplyMapper.insert(articleReply);

        if(insert!=1) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        Long articleId = articleReply.getArticleId();

        if(articleId<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        Article article = articleService.selectByPrimaryKey(articleId);

        if(article==null) throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));

        if(article.getState()==1 || article.getDeleteState()==1)

            throw new ForumException(Result.FAIL(ResultCode.FAILED_FORBIDDEN));

        articleService.addArticleReply(articleId); //帖子的回复数加一
        log.info("帖子回复成功{}",articleId);

    }

    @Override
    public List<ArticleReply> selectByArticleId( Long articleId ){
        if(articleId==null || articleId<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED_PARAMS_VALIDATE));


        return articleReplyMapper.selectByArticleId(articleId);
    }
}
