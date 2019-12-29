package com.example.studentclient;

import org.litepal.LitePal;

public class Comment extends LitePal {
    private int discussNo;
    private String content;
    private String from;
    private String to;
    private String commentTime;

    public int getDiscussNo() {
        return discussNo;
    }

    public void setDiscussNo(int discussNo) {
        this.discussNo = discussNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
