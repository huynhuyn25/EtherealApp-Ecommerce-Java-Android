package com.huynhuyn25.etherealapp.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;
    private CategoryPhoto categoryPhoto;

    public Category(int id, String name, CategoryPhoto categoryPhoto) {
        this.id = id;
        this.name = name;
        this.categoryPhoto = categoryPhoto;
    }

    public Category(String name, CategoryPhoto categoryPhoto) {
        this.name = name;
        this.categoryPhoto = categoryPhoto;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public CategoryPhoto getCategoryPhoto() {
        return categoryPhoto;
    }

    public void setCategoryPhoto(CategoryPhoto categoryPhoto) {
        this.categoryPhoto = categoryPhoto;
    }

    @Override
    public String toString() {
        return
                "name: " + name ;
    }
}
