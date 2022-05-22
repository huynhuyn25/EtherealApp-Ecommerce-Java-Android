package com.huynhuyn25.etherealapp.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class CategoryPhoto extends Photo implements Serializable {
    public CategoryPhoto(int photo) {
//        super(photo);
    }

    public CategoryPhoto() {
    }

    public CategoryPhoto(@Nullable String namePhoto, @Nullable String url) {
        super(namePhoto, url);
    }
}
