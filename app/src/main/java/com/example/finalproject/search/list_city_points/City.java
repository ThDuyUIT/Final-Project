package com.example.finalproject.search.list_city_points;

import java.io.Serializable;

public class City implements Serializable {
    private String nameCity;
    private String idCity;
    private String imageCity;

    public City() {    }

    public City(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public String getImageCity() {
        return imageCity;
    }

    public void setImageCity(String imageCity) {
        this.imageCity = imageCity;
    }
}
