package com.myapps.ecowash.model;

import java.util.Date;

public class Reservation {
    private String objectId;
    private Date date;
    private int hour;
    private String user;
    private String washingMachine;

    public Reservation(String objectId, Date date, int hour, String user, String washingMachine) {
        this.objectId = objectId;
        this.date = date;
        this.hour = hour;
        this.user = user;
        this.washingMachine = washingMachine;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWashingMachine() {
        return washingMachine;
    }

    public void setWashingMachine(String washingMachine) {
        this.washingMachine = washingMachine;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "objectId='" + objectId + '\'' +
                ", date=" + date +
                ", hour=" + hour +
                ", user='" + user + '\'' +
                ", washingMachine='" + washingMachine + '\'' +
                '}';
    }
}
