package com.myapps.ecowash.model;

import java.util.ArrayList;
import java.util.List;

public class AvailabilityByHour {

    private String date;
    private int hour;
    private List<WashingMachine> washingMachines;

    public AvailabilityByHour(String date, int hour, List<WashingMachine> washingMachines) {
        this(date,hour);
        this.washingMachines = washingMachines;
    }

    public AvailabilityByHour(String date, int hour) {
        this.date = date;
        this.hour = hour;
        this.washingMachines = new ArrayList<>();
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

    public List<WashingMachine> getWashingMachines() {
        return washingMachines;
    }

    public void setWashingMachines(List<WashingMachine> washingMachines) {
        this.washingMachines = washingMachines;
    }

    public void addWashingMachine (WashingMachine washingMachine){
        if (washingMachines == null){
            washingMachines = new ArrayList<>();
        }
        washingMachines.add(washingMachine);
    }

    public void addWashingMachines(List<WashingMachine> washingMachineList){
        if (washingMachines == null){
            washingMachines = new ArrayList<>();
        }
        washingMachines.addAll(washingMachineList);
    }

    @Override
    public String toString() {
        return "AvailabilityByHour{" +
                "date='" + date + '\'' +
                ", hour=" + hour +
                ", washingMachines=" + washingMachines +
                '}';
    }
}
