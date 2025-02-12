package com.example.forum.common.model;

import java.util.Date;

public class Article {
    private Long id;

    private Long boardld;

    private Long userId;

    private String title;

    private Integer visitCount;

    private Integer replyCount;

    private Integer likeCount;

    private Byte state;

    private Byte deleteState;

    private Date createTime;

    private Date updateTime;

    private String content;

    //判断是否是用户本人
    private Boolean Own;

    public Boolean getOwn(){
        return Own;
    }

    public void setOwn( Boolean own ){
        Own = own;
    }

    //添加一个关联对象
    private User user;

    private Board board;

    public Board getBoard(){
        return board;
    }

    public void setBoard( Board board ){
        this.board = board;
    }

    public User getUser(){
        return user;
    }

    public void setUser( User user ){
        this.user = user;
    }

    @Override
    public String toString(){
        return "Article{" +
                "id=" + id +
                ", boardld=" + boardld +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", visitCount=" + visitCount +
                ", replyCount=" + replyCount +
                ", likeCount=" + likeCount +
                ", state=" + state +
                ", deleteState=" + deleteState +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                ", user=" + user +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardld() {
        return boardld;
    }

    public void setBoardld(Long boardld) {
        this.boardld = boardld;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}