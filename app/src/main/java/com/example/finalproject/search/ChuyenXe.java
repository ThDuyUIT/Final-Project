package com.example.finalproject.search;

import java.io.Serializable;

public class ChuyenXe implements Serializable {
    private int resourceImg;
    private String title;
    private String price;

    public ChuyenXe(int resourceImg, String title, String price) {
        this.resourceImg = resourceImg;
        this.title = title;
        this.price = price;
    }

    public int getResourceImg() {
        return resourceImg;
    }

    public void setResourceImg(int resourceImg) {
        this.resourceImg = resourceImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
