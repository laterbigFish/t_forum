package com.example.forum.common.dao;

import com.example.forum.common.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper

public interface ArticleMapper {

    int insert(Article record);

    Article selectByPrimaryKey(Long id);

    List<Article> selectAll();

    int updateByPrimaryKey(Article record);

    /**
     * 查询所有与 user 关联的列表
     * @return
     */
    List<Article> selectAllByUser();

    /**
     ** 根据板块Id查找文章
     * @param boardId
     * @return
     */
    List<Article> selectAllByBoardId(@Param("boardId") Long boardId);

    /**
     * 根据帖子Id查询帖子详情
     * @param id
     * @return
     */
    Article selectDetailById(@Param("id") Long id);

    /**\
     * 根据用户id查询文章
     * @param userId
     * @return
     */
    List<Article> selectByUserId(@Param("userId") Long userId);
}