package com.example.forum.common.dao;

import com.example.forum.common.model.User;
import java.util.List;

public interface UserMapper {
    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}