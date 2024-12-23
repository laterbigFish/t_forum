package com.example.forum.common.dao;

import com.example.forum.common.model.Message;
import java.util.List;

public interface MessageMapper {
    int insert(Message record);

    Message selectByPrimaryKey(Long id);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);
}