package com.example.forum.service.impl;

import com.example.forum.common.Result;
import com.example.forum.common.dao.UserMapper;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.exception.ForumException;
import com.example.forum.common.model.User;
import com.example.forum.common.utils.encryption.DigestEncryption;
import com.example.forum.common.utils.encryption.UUIDUTIL;
import com.example.forum.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void CreateNormalUser( User user ){
        //做一个非空校验
        if(user==null || !StringUtils.hasLength(user.getUsername())
           || !StringUtils.hasLength(user.getNickname())
                || !StringUtils.hasLength(user.getPassword())
           || !StringUtils.hasLength(user.getSalt())){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛出异常
            throw new ForumException(Result.FAIL(ResultCode.FAILED_PARAMS_VALIDATE));  //抛出异常
        }
      //查询用户名
        User exist = userMapper.selectByUserName(user.getUsername());
        //判断数据是否存在
        if(exist!=null){
            log.warn(Result.FAIL(ResultCode.FAILED_NOT_EXISTS).toString());
            throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));
        }
        //新增用户  添加默认值
        user.setGender((byte)2);
        user.setArticleCount(0);
        user.setIsAdmin((byte)0);
        user.setState((byte)0);
        user.setDeleteState((byte)0);
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        //进行插入数据库

        int insert = userMapper.insert(user);

        if(insert!=1){
            log.info(ResultCode.FAILED_CREATE.toString());
            throw new ForumException(Result.FAIL(ResultCode.FAILED_CREATE.toString()));
        }

        log.info("用户添加成功{}", ResultCode.SUCCESS);
    }

    @Override
    public User selectByUserName(  String username ){
        if(!StringUtils.hasLength(username)){
            throw  new ForumException(Result.FAIL(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        return userMapper.selectByUserName(username);
    }
    @Override
    public User login( String username, String password ){
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            throw new ForumException(Result.FAIL(ResultCode.FAILED_LOGIN));
        }

        User user = userMapper.selectByUserName(username);

        if(user==null){
            log.warn("登录失败");
            throw  new ForumException(Result.FAIL(ResultCode.FAILED_USER_NOT_EXISTS));
        }
        //对密码进行校验
        String s = DigestEncryption.md5(password, user.getSalt());

        if(!s.equalsIgnoreCase(user.getPassword())){
            log.warn("{}密码输入错误", ResultCode.FAILED_LOGIN);
            throw  new ForumException(Result.FAIL(ResultCode.FAILED_LOGIN));
        }
        //返回用户信息
        log.info("登陆成功 userName = {}", user.getUsername());
        return user;
    }

    @Override
    public User selectById( Long id ){

        if(id==null){
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }
        User user = userMapper.selectByPrimaryKey(id);

        if(user==null){
            throw new ForumException(Result.FAIL(ResultCode.FAILED_USER_NOT_EXISTS));
        }
        return user;
    }

    @Override
    public User updateByPrimaryKey(  String username, String nickname, Byte gender, String email, String phoneNum, String remark ){

        User user = userMapper.selectByUserName(username);
        if(user==null || username==null)  return null;

        user.setNickname(nickname);
        user.setGender(gender);
        user.setEmail(email);
        user.setPhoneNum(phoneNum);
        user.setRemark(remark);
        userMapper.updateByPrimaryKey(user);
        return user;
    }

    @Override
    public void addOneArticleCountById( Long id ){

        if(id==null || id<=0){
            throw new ForumException(ResultCode.FAILED_USER_COUNT.toString());
        }

        User user = userMapper.selectByPrimaryKey(id);

        if(user==null) throw new ForumException(Result.FAIL(ResultCode.FAILED_USER_COUNT));
         //此时依旧没有使用动态sql
        user.setArticleCount(user.getArticleCount()+1);

        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public void subtractArticleCountById( Long id ){
        if(id==null || id<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        User user = userMapper.selectByPrimaryKey(id);

        if(user==null) throw  new ForumException(Result.FAIL(ResultCode.ERROR_IS_NULL));

        if(user.getArticleCount()==0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        user.setArticleCount(user.getArticleCount()-1);
        if(user.getArticleCount()<0)        user.setArticleCount(0);

        //进行发帖数的更新
        int i = userMapper.updateByPrimaryKey(user);

        if(i!=1) throw new ForumException(Result.FAIL(ResultCode.FAILED));
    }

    @Override
    public void modifyInfo( User user ){
        if(user==null|| user.getId()==null || user.getId()<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));

        User user1 = userMapper.selectByPrimaryKey(user.getId());

        if(user1==null) throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));

        boolean flag = false;
        //进行用户的唯一校验
        if(!StringUtils.isEmpty(user.getUsername())  &&  !user.getUsername().equals(user1.getUsername())){
            User user2 = userMapper.selectByUserName(user.getUsername());
            //用户名 重复
            if(user2!=null){
                throw new ForumException(Result.FAIL(ResultCode.FAILED));
            }
            user1.setUsername(user.getUsername());
            flag = true;
        }

        if(!StringUtils.isEmpty(user.getNickname()) && !user.getNickname().equals(user1.getNickname())){

            user1.setNickname(user.getNickname());
            flag = true;
        }

        if(user.getGender()!=null && !user.getGender().equals(user1.getGender())){
            user1.setGender(user.getGender());

            if(user.getGender()>2 || user.getGender()<0){
                user1.setGender((byte) 2);
            }
            flag = true;
        }

        if( !StringUtils.isEmpty(user.getEmail()) && !user.getEmail().equals(user1.getEmail())){
            user1.setEmail(user.getEmail());

            flag = true;
        }
       if(!StringUtils.isEmpty(user.getPhoneNum()) && !user.getPhoneNum().equals(user1.getPhoneNum())){
           user1.setPassword(user.getPhoneNum());
           flag = true;
       }
       if(!StringUtils.isEmpty(user.getRemark()) && !user.getRemark().equals(user1.getRemark())){
           user1.setRemark(user.getRemark());
           flag = true;
       }
       if(!flag) throw  new ForumException(Result.FAIL(ResultCode.FAILED));


       int i = userMapper.updateByPrimaryKey(user1);

       user1.setDeleteState((byte) 0);

        if(i!=1) throw new ForumException(Result.FAIL(ResultCode.FAILED));
    }

    @Override
    public void modifyPwd( Long id, String password, String newPassword ){
        if(password==null || newPassword==null ||  id==null || id<=0){
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }
        //查找用户
        User user = userMapper.selectByPrimaryKey(id);
        if(user==null){
            throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));
        }
        //校验密码
        String password1 = user.getPassword();

        String s = DigestEncryption.md5(password, user.getSalt());

        //忽略大小写
        if(!password1.equalsIgnoreCase(s)){
          throw  new ForumException(Result.FAIL(ResultCode.FAILED));
        }

        //修改密码 同时修改密码和盐值
        String salt = UUIDUTIL.UUID_32();

        String newPwd = DigestEncryption.md5(newPassword,salt);

        user.setPassword(newPwd);
        user.setSalt(salt);

        //更新数据
        int i = userMapper.updateByPrimaryKey(user);

        if(i!=1){
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }
    }
}
