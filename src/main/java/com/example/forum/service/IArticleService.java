package com.example.forum.service;

import com.example.forum.common.model.Article;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArticleService {
    /**
     * 发布帖子
     * @param article 要发布的帖子
     */
    @Transactional    //这样可以别事务管理起来   如果被重写那么这个事务将会被继承
    public void create( Article article );

    /**
     * 查询Article 与 user 关联的表
     * @return
     */
    List<Article> selectAllByUser();

    /**
     * 通过板块id查找相应的文章
     * @param boardId
     * @return
     */
    List<Article> selectAllByBoardId(Long boardId);

    /**
     * 根据帖子Id查询详情
     * @param id
     * @return
     */
    Article selectDetailById(Long id);

    /**
     * 编辑帖子内容
     * @param id
     * @param title
     * @param content
     */
    void modify(Long id,String title,String content);

    /**
     * 根据id进行查找
     * @param id
     * @return
     */
    Article selectByPrimaryKey(Long id);

    /**
     * 进行帖子的点赞操作
     * @param id
     * @return
     */
    void thumbsUpById(Long id);

    @Transactional     //如果发生错误进行回滚操作
    void deleteById(Long id);

    /**
     * 帖子回复数加一
     * @param id
     */
    void addArticleReply(Long id);
    /**\
     * 根据用户id查询文章
     * @param userId
     * @return
     */
    List<Article> selectByUserId( Long userId);
}
