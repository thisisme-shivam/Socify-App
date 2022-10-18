package com.example.socify.Classes;

public class Person {
    String uid;
    String name;
    String username;
    boolean follow_status;
    String uri;

    public Person(){}

    public Person(String name, String username,boolean follow_status,String uri, String uid){
        this.name = name;
        this.username = username;
        this.follow_status = follow_status;
        this.uri = uri;
        this.uid = uid;
    }


    public String getUsername() {
        return username;
    }

    public String getUid(){return uid;}

    public String getName(){
        return name;
    }

    public String getUri(){
        return uri;
    }

    public boolean getFollow_status(){
        return follow_status;
    }

}
