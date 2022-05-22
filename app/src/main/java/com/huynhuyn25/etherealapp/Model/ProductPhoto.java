package com.huynhuyn25.etherealapp.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class ProductPhoto extends Photo implements Serializable {
    public ProductPhoto(int photo) {
//        super(photo);
    }

    public ProductPhoto() {
    }

    public ProductPhoto(@Nullable String namePhoto, @Nullable String url) {
        super(namePhoto, url);
    }
}
