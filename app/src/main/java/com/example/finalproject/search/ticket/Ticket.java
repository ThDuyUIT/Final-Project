package com.example.finalproject.search.ticket;

import java.io.Serializable;

public class Ticket implements Serializable {
    private int resourceID;
    private String nameTicket;
    private String busNumber;
    private String departureTime;
    private String priceTicket;

    public Ticket (int resourceID, String nameTicket, String busNumber, String departureTime, String priceTicket) {
        this.resourceID = resourceID;
        this.nameTicket = nameTicket;
        this.departureTime = departureTime;
        this.priceTicket = priceTicket;
        this.busNumber = busNumber;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getNameTicket() {
        return nameTicket;
    }

    public void setNameTicket(String nameTicket) {
        this.nameTicket = nameTicket;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(String priceTicket) {
        this.priceTicket = priceTicket;
    }
}
