package com.example.socify.Classes;

public class Person {
    String name;
    String username;
    String follow_status;
    String uri;

    public Person(){}

    public Person(String name, String username,String follow_status,String uri){
        this.name = name;
        this.username = username;
        this.follow_status = follow_status;
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }



    public String getName(){
        return name;
    }

    public String getUri(){
        return uri;
    }

    public String getFollow_status(){
        return follow_status;
    }

}
