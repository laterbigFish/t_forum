package com.example.forum.service;

import com.example.forum.common.model.Message;

import java.util.List;

public interface IMessageService {
    public void sendById( Long userId, Long receiveUserId, String content );

    public int selectByReceiveUserId( Long receiveUserId );

    public List<Message> getAllMessage( Long receiveUserId );

    public void updateByMessageId( Long id );

    public Message selectById( Long id );

    public void reply( Long repliedId, Message message );

}
