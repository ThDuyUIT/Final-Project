package com.example.finalproject.search;

import com.example.finalproject.authentication.Account;

import java.io.Serializable;

public class ChuyenXe implements Serializable {
    private int resourceImg;
    private String title;
    private String price;
    private Account account;
    private String idAccount;

    public ChuyenXe() {
    }

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }
}
