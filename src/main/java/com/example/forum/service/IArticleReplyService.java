package com.example.forum.service;

import com.example.forum.common.model.ArticleReply;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArticleReplyService {

    @Transactional(isolation = Isolation.DEFAULT) //根据数据库磨默认的方式 默认的情况
    void create( ArticleReply articleReply );

    /**
     * 查询回复帖子
     * @param articleId
     * @return
     */
    List<ArticleReply> selectByArticleId( Long articleId);

}
