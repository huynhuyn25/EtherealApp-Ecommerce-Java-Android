package com.huynhuyn25.etherealapp.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Photo implements Serializable {
//    private int photo;
    private int idPhoto;
    private String namePhoto;
    private String url;
//    public Photo(int photo) {
//        this.photo = photo;
//    }

    public Photo() {
    }

    public Photo(@Nullable String namePhoto,@Nullable String url) {
        this.namePhoto = namePhoto;
//        this.dinhDangPhoto = dinhDangPhoto;
        this.url = url;
    }

//    public int getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(int photo) {
//        this.photo = photo;
//    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getNamePhoto() {
        return namePhoto;
    }

    public void setNamePhoto(String namePhoto) {
        this.namePhoto = namePhoto;
    }

//    public String getDinhDangPhoto() {
//        return dinhDangPhoto;
//    }
//
//    public void setDinhDangPhoto(String dinhDangPhoto) {
//        this.dinhDangPhoto = dinhDangPhoto;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "{" +
                "\"idPhoto\":" + idPhoto +
                ", \"namePhoto\":\"" + namePhoto + '\"' +
                ", \"url\":\"" + url + '\"' +
                '}';
    }
}
