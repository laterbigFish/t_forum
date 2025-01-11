package com.example.forum.common.dao;

import com.example.forum.common.model.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper

public interface ArticleMapper {
    int insert(Article record);
    Article selectByPrimaryKey(Long id);
    List<Article> selectAll();
    int updateByPrimaryKey(Article record);
}