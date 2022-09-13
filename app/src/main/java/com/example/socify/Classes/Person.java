package com.example.socify.Classes;

public class Person {
    String name;
    String usernmae;
    String follow_status;
    String uri;

    public Person(){}

    public void setAllInfo(String name, String usernmae,String follow_status,String uri){
        this.name = name;
        this.usernmae = usernmae;
        this.follow_status = follow_status;
        this.uri = uri;
    }

    public String getUsername() {
        return usernmae;
    }

    public String getName(){
        return usernmae;
    }

    public String getUri(){
        return uri;
    }

    public String getFollow_status(){
        return follow_status;
    }

}
