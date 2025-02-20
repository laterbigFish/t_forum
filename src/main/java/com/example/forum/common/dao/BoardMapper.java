package com.example.forum.common.dao;

import com.example.forum.common.model.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper

public interface BoardMapper {
    int insert(Board record);

    Board selectByPrimaryKey(Long id);

    List<Board> selectAll();

    int updateByPrimaryKey(Board record);

    /**
     *
     * @param num
     * @return
     */
    List<Board> selectByNum(@Param("num") Integer num);
}