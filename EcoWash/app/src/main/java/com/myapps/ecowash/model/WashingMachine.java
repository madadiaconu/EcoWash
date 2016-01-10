package com.myapps.ecowash.model;

public class WashingMachine {

    private String name;
    private int maximumLoad;

    public WashingMachine(String name, int maximumLoad) {
        this.name = name;
        this.maximumLoad = maximumLoad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaximumLoad() {
        return maximumLoad;
    }

    public void setMaximumLoad(int maximumLoad) {
        this.maximumLoad = maximumLoad;
    }

    @Override
    public String toString() {
        return "WashingMachine{" +
                "name='" + name + '\'' +
                ", maximumLoad=" + maximumLoad +
                '}';
    }
}
