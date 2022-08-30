package com.example.socify.FireBaseClasses;

import java.util.ArrayList;

public class UserDetails {

    String ImgUri, name, username, password, passyear, college_name, course;
    ArrayList <String> tags;

    public void setImgUri(String imgUri) {
        ImgUri = imgUri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassyear(String passyear) {
        this.passyear = passyear;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getImgUri() {
        return ImgUri;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPassyear() {
        return passyear;
    }

    public String getCollege_name() {
        return college_name;
    }

    public String getCourse() {
        return course;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
