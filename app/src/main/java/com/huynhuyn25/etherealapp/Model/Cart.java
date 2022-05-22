package com.huynhuyn25.etherealapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private int idCart;
    private List<ProductCart> listProductCart;
    @SerializedName("total")
    private double Total;

    public Cart() {
    }

    public Cart(List<ProductCart> listProductCart, double total) {
        this.listProductCart = listProductCart;
        Total = total;
    }

    public Cart(int idCart, List<ProductCart> listProductCart, double total) {
        this.idCart = idCart;
        this.listProductCart = listProductCart;
        Total = total;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public List<ProductCart> getListProductCart() {
        return listProductCart;
    }

    public void setListProductCart(List<ProductCart> listProductCart) {
        this.listProductCart = listProductCart;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }
}
