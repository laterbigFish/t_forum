package com.example.forum.service.impl;

import com.example.forum.common.Result;
import com.example.forum.common.dao.MessageMapper;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.exception.ForumException;
import com.example.forum.common.model.Message;
import com.example.forum.service.IMessageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService {

    @Resource
    MessageMapper messageMapper;

    @Override
    public void sendById( Long userId, Long receiveUserId, String content ){

        if(userId==null || receiveUserId==null) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        Message message = new Message();
        //进行赋值

        message.setContent(content);
        message.setState((byte) 0);
        message.setPostUserId(userId);
        message.setReceiveUserId(receiveUserId);

        Date time = new Date();
        message.setCreateTime(time);
        message.setDeleteState((byte)0);
        message.setUpdateTime(time);

        //更新数据

        int i = messageMapper.insert(message);

        if(i<0){
            throw  new ForumException(Result.FAIL(ResultCode.FAILED));
        }
    }

    @Override
    public int selectByReceiveUserId( Long receiveUserId ){

        if(receiveUserId==null || receiveUserId<=0){
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }

        int i = messageMapper.selectByReceiveUserId(receiveUserId);

        return i;
    }

    /**
     *
     * @param receiveUserId 接受者id
     * @return
     */
    @Override
    public List<Message> getAllMessage( Long receiveUserId ){

        if(receiveUserId==null || receiveUserId<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        List<Message> messages = messageMapper.selectAllByReceiveUserId(receiveUserId);

        return messages;
    }

    @Override
    public void updateByMessageId( Long id ){

        if(id==null ||  id<=0){
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }
        Message message = messageMapper.selectByPrimaryKey(id);

        if(message==null){
            throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));
        }
        message.setState((byte)1);

        int i = messageMapper.updateByPrimaryKey(message);

        if(i!=1) throw new ForumException(Result.FAIL(ResultCode.FAILED));
    }

    @Override
    public Message selectById( Long id ){
        if(id==null || id<=0){
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }
        Message message = messageMapper.selectByPrimaryKey(id);

        return message;
    }

    @Override
    public void reply( Long repliedId, Message message ){
        if(repliedId==null || repliedId<=0) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        Message message1 = messageMapper.selectByPrimaryKey(repliedId);

        if(message1==null || message1.getDeleteState()==1) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        //更新状态为已回复

        message1.setState((byte) 2);

        int i = messageMapper.updateByPrimaryKey(message1);

        if(i!=1) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        //回复的内容写入数据库

        sendById(message.getPostUserId(),message.getReceiveUserId(),message.getContent());


    }
}
