package com.example.finalproject.authentication;

import java.io.Serializable;

public class Account implements Serializable {
    private String HoTen;
    private String SDT;
    private String TenTK;

    public  Account(){

    }

    public Account(String hoTen, String SDT, String tenTK) {
        HoTen = hoTen;
        this.SDT = SDT;
        TenTK = tenTK;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTenTK() {
        return TenTK;
    }

    public void setTenTK(String tenTK) {
        TenTK = tenTK;
    }
}
