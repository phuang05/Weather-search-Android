package com.example.penghuang.hw9_01.Class;

import java.io.Serializable;

/**
 * Created by penghuang on 11/24/19.
 */

public class Eight_days_info implements Serializable{
    private String date;
    private int icon;
    private int temperatureLow;
    private int temperatureHigh;

    public Eight_days_info(String date,int icon, int temperatureLow, int temperatureHigh){
        setDate(date);
        setIcon(icon);
        setTemperatureHigh(temperatureHigh);
        setTemperatureLow(temperatureLow);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTemperatureHigh(int temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public void setTemperatureLow(int temperatureLow) {
        this.temperatureLow = temperatureLow;
    }

    public int getTemperatureHigh() {
        return temperatureHigh;
    }

    public int getTemperatureLow() {
        return temperatureLow;
    }

    public String getDate() {
        return date;
    }

    public int getIcon() {
        return icon;
    }
}

