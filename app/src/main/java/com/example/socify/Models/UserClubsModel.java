package com.example.socify.Models;

import android.widget.ImageView;
import android.widget.TextView;

public class UserClubsModel {

    public int clubprof;
    public String  clubSubject, lastMessage, time;

    public UserClubsModel(int clubprof, String clubSubject, String lastMessage, String time) {
        this.clubprof = clubprof;
        this.clubSubject = clubSubject;
        this.lastMessage = lastMessage;
        this.time = time;
    }
}
