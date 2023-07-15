package com.example.finalproject.admin.managefunction.XE;

import java.io.Serializable;

public class Xe implements Serializable {
    private String idNumber;
    private String imgXe;
    private String nameXe;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getImgXe() {
        return imgXe;
    }

    public void setImgXe(String imgXe) {
        this.imgXe = imgXe;
    }

    public String getNameXe() {
        return nameXe;
    }

    public void setNameXe(String nameXe) {
        this.nameXe = nameXe;
    }
}
