package com.huynhuyn25.etherealapp.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Product implements Serializable {
    private int idProduct;
    private String name;
    private double price;

    private Manufacturer manufacturer;
    private int soluongCon;
    private Category category;
    private ArrayList<ProductPhoto> listProductPhoto;

    public Product( String name, double price,@Nullable Manufacturer manufacturer, int soluongCon, @Nullable  Category category, ArrayList<ProductPhoto> listProductPhoto) {
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.soluongCon = soluongCon;
        this.category = category;
        this.listProductPhoto = listProductPhoto;
    }

    public Product() {
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getSoluongCon() {
        return soluongCon;
    }

    public void setSoluongCon(int soluongCon) {
        this.soluongCon = soluongCon;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArrayList<ProductPhoto> getListProductPhoto() {
        return listProductPhoto;
    }

    public void setListProductPhoto(ArrayList<ProductPhoto> listProductPhoto) {
        this.listProductPhoto = listProductPhoto;
    }

    @Override
    public String toString() {
        return
                "Manufacturer:" + manufacturer +
                "\nSoluongCon:" + soluongCon +
                "\nCategory:" + category ;
    }
    public static Comparator<Product>comGiaCaotoThap = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {

            double gia1 = o1.price;
            double gia2=o2.price;
            if(gia1>=gia2) return -1;

            return 1;
        }
    };
    public static Comparator<Product>comGiaThaptoCao = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            double gia1 = o1.price;
            double gia2=o2.price;
            if(gia1<=gia2) return -1;
            return 1;
        }
    };
}
