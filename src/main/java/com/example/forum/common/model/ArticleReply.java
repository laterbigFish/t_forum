package com.example.forum.common.model;

import java.util.Date;


public class ArticleReply {
    private Long id;
     //发布帖子的id
    private Long articleId;
    //评论人的id
    private Long postUserId;

    //下面两个是楼中楼功能  在这里忽略
    private Long replyId;

    private Long replyUserId;

    private String content;
    // 忽略
    private Integer likeCount;

    private Byte state;

    private Byte deleteState;

    private Date createTime;

    private Date updateTime;

    //关联对象 帖子回复的发送者
    private User user;

    public Long getArticleId(){
        return articleId;
    }

    public void setArticleId( Long articleId ){
        this.articleId = articleId;
    }

    public User getUser(){
        return user;
    }

    public void setUser( User user ){
        this.user = user;
    }

    @Override
    public String toString(){
        return "ArticleReply{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", postUserId=" + postUserId +
                ", replyId=" + replyId +
                ", replyUserId=" + replyUserId +
                ", content='" + content + '\'' +
                ", likeCount=" + likeCount +
                ", state=" + state +
                ", deleteState=" + deleteState +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", user=" + user +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(Long postUserId) {
        this.postUserId = postUserId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Byte deleteState) {
        this.deleteState = deleteState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}