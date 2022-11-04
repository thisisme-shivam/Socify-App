package com.example.socify.Classes;

public class NotificationMember {
    String imgurl;

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    String userUid;
    String username;
    String info;
    String postid;
    String date;
    String time;
    String type;

    public String getImgurl() {
        return imgurl;
    }

    public String getUsername() {
        return username;
    }

    public String getUserUid() {
        return userUid;
    }

    public String getInfo(){return info;}


    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }


    public String getType() {
        return type;
    }

    public void setpostid(String postid) {
    }
    public String getpostid(){
        return postid;
    }
}

