package com.huynhuyn25.etherealapp.Model;

import java.io.Serializable;

public class ProductOrder implements Serializable {
    private int idProductOrder;
    private Product product;
    private int soLuong;

    public ProductOrder() {
    }

    public ProductOrder(Product product, int soLuong) {
        this.product = product;
        this.soLuong = soLuong;

    }

    public int getIdProductOrder() {
        return idProductOrder;
    }

    public void setIdProductOrder(int idProductOrder) {
        this.idProductOrder = idProductOrder;
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
