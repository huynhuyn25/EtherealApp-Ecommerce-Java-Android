package com.huynhuyn25.etherealapp.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class AvaPhoto extends Photo implements Serializable {
//    public AvaPhoto(int photo) {
//        super(photo);
//    }

    public AvaPhoto() {
    }

    public AvaPhoto(@Nullable String namePhoto, @Nullable String url) {
        super(namePhoto, url);
    }

}
