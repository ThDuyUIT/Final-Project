package com.example.finalproject.authentication;

import java.io.Serializable;

public class Account implements Serializable {
    private String HoTen;
    private String SDT;
    private String TenTK;
    private String GioiTinh = "Unknow";
    private String AnhDaiDien = "Unknow";

    public  Account(){

    }

    public Account(String hoTen, String SDT, String tenTK) {
        HoTen = hoTen;
        this.SDT = SDT;
        TenTK = tenTK;
    }

    public Account(String hoTen, String SDT, String tenTK, String gioiTinh, String anhDaiDien) {
        HoTen = hoTen;
        this.SDT = SDT;
        TenTK = tenTK;
        GioiTinh = gioiTinh;
        AnhDaiDien = anhDaiDien;
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

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getAnhDaiDien() {
        return AnhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        AnhDaiDien = anhDaiDien;
    }
}
