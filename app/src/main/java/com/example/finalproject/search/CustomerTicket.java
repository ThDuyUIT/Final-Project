package com.example.finalproject.search;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerTicket implements Parcelable {
    private String routeStart;
    private String routeEnd;
    private String routeDate;

    private String busName;
    private String busNumber;
    private String routeTime;

    private String priceTicket;
    private String seatNumber;
    private String nameCus;
    private String phoneCus;
    private String emailCus;
    private String otherNote;

    public CustomerTicket(String routeStart, String routeEnd, String routeDate, String busName, String busNumber, String routeTime, String priceTicket, String seatNumber, String nameCus, String phoneCus, String emailCus, String otherNote) {
        this.routeStart = routeStart;
        this.routeEnd = routeEnd;
        this.routeDate = routeDate;
        this.busName = busName;
        this.busNumber = busNumber;
        this.routeTime = routeTime;
        this.priceTicket = priceTicket;
        this.seatNumber = seatNumber;
        this.nameCus = nameCus;
        this.phoneCus = phoneCus;
        this.emailCus = emailCus;
        this.otherNote = otherNote;
    }

    protected CustomerTicket(Parcel in) {
        routeStart = in.readString();
        routeEnd = in.readString();
        routeDate = in.readString();
        busName = in.readString();
        busNumber = in.readString();
        routeTime = in.readString();
        seatNumber = in.readString();
        nameCus = in.readString();
        phoneCus = in.readString();
        emailCus = in.readString();
        otherNote = in.readString();
    }

    public String getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(String priceTicket) {
        this.priceTicket = priceTicket;
    }

    public static final Creator<CustomerTicket> CREATOR = new Creator<CustomerTicket>() {
        @Override
        public CustomerTicket createFromParcel(Parcel in) {
            return new CustomerTicket(in);
        }

        @Override
        public CustomerTicket[] newArray(int size) {
            return new CustomerTicket[size];
        }
    };

    public String getRouteStart() {
        return routeStart;
    }

    public void setRouteStart(String routeStart) {
        this.routeStart = routeStart;
    }

    public String getRouteEnd() {
        return routeEnd;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }

    public String getRouteDate() {
        return routeDate;
    }

    public void setRouteDate(String routeDate) {
        this.routeDate = routeDate;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getRouteTime() {
        return routeTime;
    }

    public void setRouteTime(String routeTime) {
        this.routeTime = routeTime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getNameCus() {
        return nameCus;
    }

    public void setNameCus(String nameCus) {
        this.nameCus = nameCus;
    }

    public String getPhoneCus() {
        return phoneCus;
    }

    public void setPhoneCus(String phoneCus) {
        this.phoneCus = phoneCus;
    }

    public String getEmailCus() {
        return emailCus;
    }

    public void setEmailCus(String emailCus) {
        this.emailCus = emailCus;
    }

    public String getOtherNote() {
        return otherNote;
    }

    public void setOtherNote(String otherNote) {
        this.otherNote = otherNote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(routeStart);
        dest.writeString(routeEnd);
        dest.writeString(routeDate);
        dest.writeString(busName);
        dest.writeString(busNumber);
        dest.writeString(routeTime);
        dest.writeString(priceTicket);
        dest.writeString(seatNumber);
        dest.writeString(nameCus);
        dest.writeString(phoneCus);
        dest.writeString(emailCus);
        dest.writeString(otherNote);
    }
}


