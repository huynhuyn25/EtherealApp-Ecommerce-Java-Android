package com.huynhuyn25.etherealapp.Presenter;

import com.huynhuyn25.etherealapp.Model.Product;

import java.util.ArrayList;
import java.util.Collections;

public class ProductPresenter {
    public ArrayList<Product> sortThaptocao(ArrayList<Product> products){
        Collections.sort(products, Product.comGiaThaptoCao);
        return products;
    }
    public ArrayList<Product> sortcaotoThap(ArrayList<Product> products){
        Collections.sort(products, Product.comGiaCaotoThap);
        return products;
    }
    public ArrayList<Product> locCategory(ArrayList<Product> products,String category){
        ArrayList<Product> listCategory = new ArrayList<>();
        for(Product product:products){
            if(product.getCategory().getName().equals(category))
                listCategory.add(product);
        }
        return listCategory;
    }
}
