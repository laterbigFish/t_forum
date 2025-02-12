package com.example.forum.common.dao;

import com.example.forum.common.model.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper

public interface MessageMapper {
    int insert(Message record);

    Message selectByPrimaryKey(Long id);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);

    int selectByReceiveUserId(Long receiveUserId);

    List<Message> selectAllByReceiveUserId(Long receiveUserId);
}