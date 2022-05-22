package com.huynhuyn25.etherealapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductCart implements Serializable {
    private int idProductCart;
    private Product product;
    @SerializedName("soluong")
    private int soLuong;

    public ProductCart() {
    }

    public ProductCart(Product product, int soLuong) {
        this.product = product;
        this.soLuong = soLuong;

    }
    public int getIdProductCart() {
        return idProductCart;
    }

    public void setIdProductCart(int idProductCart) {
        this.idProductCart = idProductCart;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }


}
