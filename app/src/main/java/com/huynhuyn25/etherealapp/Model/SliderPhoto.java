package com.huynhuyn25.etherealapp.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class SliderPhoto extends Photo implements Serializable {
    private String Title;
    private String Description;

    public SliderPhoto() {
    }

    public SliderPhoto(@Nullable String namePhoto, @Nullable String url, String title, String description) {
        super(namePhoto, url);
        Title = title;
        Description = description;
    }

    public SliderPhoto(@Nullable String namePhoto, @Nullable String url) {
        super(namePhoto, url);
    }

    public SliderPhoto(@Nullable String namePhoto, @Nullable String dinhDangPhoto, @Nullable String url, String title, String description) {
//        super(namePhoto, dinhDangPhoto, url);
        Title = title;
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
