package com.example.finalproject.search.ticket;

import java.io.Serializable;

public class Ticket implements Serializable {
    private int resourceID;
    private String srcImg;
    private String nameRoute;
    private String nameTicket;
    private String busNumber;
    private String departureTime;
    private String departureDate;
    private String numberSeat;
    private String priceTicket;
    private String startPoint;
    private String endPoint;
    private String idAccount;
    private String idTransition;
    private String idTicket;
    private String statusPayment;
    private String statusTicket;
    private String priceTotal;
    private String featuredRoute;

    public Ticket() {
    }

    public Ticket(int resourceID, String nameRoute, String nameTicket, String busNumber, String departureTime, String departureDate, String numberSeat, String priceTicket, String fullName, String phone, String email) {
        this.resourceID = resourceID;
        this.nameRoute = nameRoute;
        this.nameTicket = nameTicket;
        this.busNumber = busNumber;
        this.departureTime = departureTime;
        this.departureDate = departureDate;
        this.numberSeat = numberSeat;
        this.priceTicket = priceTicket;
//        this.fullName = fullName;
//        this.phone = phone;
//        this.email = email;
    }

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

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getNumberSeat() {
        return numberSeat;
    }

    public void setNumberSeat(String numberSeat) {
        this.numberSeat = numberSeat;
    }

    public String getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(String priceTicket) {
        this.priceTicket = priceTicket;
    }

//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getIdTransition() {
        return idTransition;
    }

    public void setIdTransition(String idTransition) {
        this.idTransition = idTransition;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(String srcImg) {
        this.srcImg = srcImg;
    }

    public String getStatusTicket() {
        return statusTicket;
    }

    public void setStatusTicket(String statusTicket) {
        this.statusTicket = statusTicket;
    }

    public String getFeaturedRoute() {
        return featuredRoute;
    }

    public void setFeaturedRoute(String featuredRoute) {
        this.featuredRoute = featuredRoute;
    }
}