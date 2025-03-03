package com.example.forum.service.impl;

import com.example.forum.common.dao.ArticleMapper;
import com.example.forum.common.model.Article;
import com.example.forum.service.IArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
@SpringBootTest
class ArticleServiceImplTest {
    @Resource
    private IArticleService articleService;
    @Resource
    private ObjectMapper objectMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void create(){
        Article article = new Article();
        article.setUserId(1L);
        article.setBoardId(1L);
        article.setTitle("单元测试");
        article.setContent("这是内容");
        articleService.create(article);
    }

    @Test
    void selectAllByUser() throws JsonProcessingException{
        List<Article> articles = articleService.selectAllByUser();
        System.out.println(objectMapper.writeValueAsString(articles));
    }

    @Test
    void selectAllByBoardId() throws JsonProcessingException{
        List<Article> articles = articleService.selectAllByBoardId(2L);
        if (articles == null) System.out.println("为空");
        System.out.println(objectMapper.writeValueAsString(articles));

    }

    @Test
    void selectDetailById() throws JsonProcessingException{
        Article article = articleService.selectDetailById(9L);
        String s = objectMapper.writeValueAsString(article);
    }

    @Test
    void modify(){
        articleService.modify(1L,"更新后的单元测试","更新后的内容");
        System.out.println("更新成功");

    }

    @Test
    void thumbsUpById(){
        articleService.thumbsUpById(1L);
        System.out.println("操作完成");
    }

    @Test
    void deleteById(){
        articleService.deleteById(1L);
    }

    @Test
    void selectByUserId() throws JsonProcessingException{
        List<Article> articles = articleService.selectByUserId(1L);
        System.out.println(objectMapper.writeValueAsString(articles));
    }

    @Test
    void testCreate(){
        Article article = new Article();
        article.setTitle("sfsadfsa"); //标题
        article.setContent("asfdsaf"); //正文
        article.setUserId(1L); //作者id
        article.setBoardId(1L);
        article.setVisitCount(0);
        article.setReplyCount(0);
        article.setLikeCount(0);
        article.setDeleteState((byte) 0);
        article.setState((byte) 0);
        Date date = new Date();
        article.setCreateTime(date);
        article.setUpdateTime(date);
        articleService.create(article);
    }

}