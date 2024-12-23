package com.example.forum.common.dao;

import com.example.forum.common.model.Article;
import java.util.List;

public interface ArticleMapper {
    int insert(Article record);

    Article selectByPrimaryKey(Long id);

    List<Article> selectAll();

    int updateByPrimaryKey(Article record);
}