package com.huynhuyn25.etherealapp.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class BannerPhoto extends Photo implements Serializable {
    public BannerPhoto(int photo) {
//        super(photo);
    }

    public BannerPhoto() {
    }

    public BannerPhoto(@Nullable String namePhoto, @Nullable String url) {
        super(namePhoto, url);
    }
}
