package com.example.forum.common.dao;

import com.example.forum.common.model.ArticleReply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper

public interface ArticleReplyMapper {
    int insert(ArticleReply record);

    ArticleReply selectByPrimaryKey(Long id);

    List<ArticleReply> selectAll();

    int updateByPrimaryKey(ArticleReply record);
}