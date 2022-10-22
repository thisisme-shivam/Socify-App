package com.example.socify.Classes;

public class CommentMember {

    String comment, uid, username, time, commentkey;

    public String getComment() {
        return comment;
    }

    public String getCommentkey() {
        return commentkey;
    }

    public void setCommentkey(String commentkey) {
        this.commentkey = commentkey;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuserName() {
        return username;
    }

    public void setuserName(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
