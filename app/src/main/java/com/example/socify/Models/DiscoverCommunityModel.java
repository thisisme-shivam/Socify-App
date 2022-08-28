package com.example.socify.Models;

import android.media.Image;
import android.widget.ImageView;

public class DiscoverCommunityModel {

    public int communityImg;
    public String communityName;

    public DiscoverCommunityModel(int communityImg, String communityName) {
        this.communityImg = communityImg;
        this.communityName = communityName;
    }
}
