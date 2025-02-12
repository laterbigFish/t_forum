package com.example.forum.service;

import com.example.forum.common.model.User;
import org.springframework.transaction.annotation.Transactional;


public interface IUserService {
    /**
     * 常见一个普通用户
     * @param user
     */
    public void CreateNormalUser( User user);

    /**
     * 根据用户名查找信息
     * @param username
     * @return
     */
    User selectByUserName(String username);

    /**
     * 处理内登录
     * @param username
     * @return
     */
    User login(String username,String password);

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    User selectById(Long id);

    /**
     * 根据username进行修改数据
     * @param username
     * @param nickname
     * @param gender
     * @param email
     * @param phoneNum
     * @param remark
     * @return
     */
    User updateByPrimaryKey(String username,String nickname,Byte gender,String email,String phoneNum ,String remark);

    /**
     * 更新user的帖子数
     * @param id
     */
    public  void addOneArticleCountById(Long id);

    /**
     * 用户发帖数减一
     * @param id
     */
    void subtractArticleCountById(Long id);

    /**
     * 修改个人中心的数据
     * @param user
     */
    @Transactional
    void modifyInfo(User user);

    /**
     * 修改密码
     * @param id
     * @param password
     * @param newPassword
     */
    @Transactional
    void modifyPwd(Long id,String password,String newPassword);
}
