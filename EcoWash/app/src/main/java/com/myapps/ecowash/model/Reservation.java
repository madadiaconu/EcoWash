package com.myapps.ecowash.model;

public class Reservation {
    private String objectId;
    private String date;
    private int hour;
    private User user;
    private WashingMachine washingMachine;

    public Reservation(String objectId, String date, int hour, User user, WashingMachine washingMachine) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WashingMachine getWashingMachine() {
        return washingMachine;
    }

    public void setWashingMachine(WashingMachine washingMachine) {
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
