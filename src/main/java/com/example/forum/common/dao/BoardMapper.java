package com.example.forum.common.dao;

import com.example.forum.common.model.Board;
import java.util.List;

public interface BoardMapper {
    int insert(Board record);

    Board selectByPrimaryKey(Long id);

    List<Board> selectAll();

    int updateByPrimaryKey(Board record);
}