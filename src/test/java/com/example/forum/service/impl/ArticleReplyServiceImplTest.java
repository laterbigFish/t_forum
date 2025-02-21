package com.example.forum.service.impl;

import com.example.forum.common.model.ArticleReply;
import com.example.forum.service.IArticleReplyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ArticleReplyServiceImplTest {
    @Resource
    private IArticleReplyService articleReplyService;
    @Resource
    private ObjectMapper objectMapper;
    @Test
    void create(){
        ArticleReply articleReply = new ArticleReply();

        articleReply.setArticleId(1L);
        articleReply.setContent("现价的帖子2");
        articleReply.setPostUserId(1L);
        articleReplyService.create(articleReply);
        System.out.println("回复成功");
    }

    @Test
    void selectByArticleId() throws JsonProcessingException{
        List<ArticleReply> listResult = articleReplyService.selectByArticleId(1L);
        System.out.println(objectMapper.writeValueAsString(listResult));
    }


}