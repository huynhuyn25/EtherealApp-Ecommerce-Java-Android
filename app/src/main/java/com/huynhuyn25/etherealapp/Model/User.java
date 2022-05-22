package com.huynhuyn25.etherealapp.Model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private int idUser;
    @SerializedName("username")
    private String Username;
    @SerializedName("name")
    private String Name;
    private String password;
    private String email;
    private String numberPhone;
    private String dateOfBirth;
    private Cart cart;
    private AvaPhoto avaPhoto;
    private int vaitro;
    public User() {
    }

    public User(int idUser, String username, String name, String password, String email, String numberPhone, String dateOfBirth, Cart cart, AvaPhoto avaPhoto,int vaitro) {
        this.idUser = idUser;
        Username = username;
        Name = name;
        this.password = password;
        this.email = email;
        this.numberPhone = numberPhone;
        this.dateOfBirth = dateOfBirth;
        this.cart = cart;
        this.avaPhoto = avaPhoto;
        this.vaitro=vaitro;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public AvaPhoto getAvaPhoto() {
        return avaPhoto;
    }

    public void setAvaPhoto(AvaPhoto avaPhoto) {
        this.avaPhoto = avaPhoto;
    }

    @Override
    public String toString() {
        return "{" +
                "\"idUser\":" + idUser +
                ", \"username\":\"" + Username + '\"' +
                ", \"name\":\"" + Name + '\"' +
                ", \"password\":\"" + password + '\"'+
                ", \"email\":\"" + email + '\"' +
                ", \"numberPhone\":\"" + numberPhone + '\"' +
                ", \"dateOfBirth\":\"" + dateOfBirth + '\"' +
                ", avaPhoto:" + avaPhoto.toString() +
                ", \"vaitro\":" + vaitro +
                '}';
    }

    public int getVaitro() {
        return vaitro;
    }

    public void setVaitro(int vaitro) {
        this.vaitro = vaitro;
    }
}
