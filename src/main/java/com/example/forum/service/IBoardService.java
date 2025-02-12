package com.example.forum.service;

import com.example.forum.common.model.Board;
import org.springframework.stereotype.Service;

import java.util.List;
public interface IBoardService {
    /**
     * 查询num条信息
     * @param num
     * @return
     */
    List<Board> selectByNum(Integer num);
    /**
     * 更新对应板块的帖子数量
     * @param id 板块id
     */
    public  void addOneArticleCountById(Long id);

    /**
     * 通过id查找板块
     * @param id  板块的id
     * @return
     */
    Board selectByPrimaryKey(Long id);

    /**
     * 板块中的帖子数量减一
     * @param id
     */
    void subtractArticleCountById(Long id);
}
