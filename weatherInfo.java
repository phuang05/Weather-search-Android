package com.example.penghuang.hw9_01.Class;

import com.example.penghuang.hw9_01.Class.Eight_days_info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by penghuang on 11/25/19.
 */

public class weatherInfo implements Serializable{
    private double lat= 0;
    private double lng= 0;
    private String summary;
    private String city;
    private String state;
    private String street;
    private String temperature;
    private double humidity= 0;
    private double windSpeed= 0;
    private double visibility= 0;
    private double pressure= 0;
    private String icon;
    private String timeZone;
    private double precipitation= 0;
    private double cloudCover= 0;
    private String dailySummary;
    private String cityText;
    private double ozone = 0;

    private List<Eight_days_info> eight_days_info_list= new ArrayList<Eight_days_info>();

    /////////set
    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setEight_days_info_list(List<Eight_days_info> eight_days_info_list) {
        this.eight_days_info_list = eight_days_info_list;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public void setDailySummary(String dailySummary) {
        this.dailySummary = dailySummary;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

    ///////////////////////get
    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getStreet() {
        return street;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getVisibility() {
        return visibility;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getSummary() {
        return summary;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getIcon() {
        return icon;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public List<Eight_days_info> getEight_days_info_list() {
        return eight_days_info_list;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public String getDailySummary() {
        return dailySummary;
    }

    public String getCityText() {
        return cityText;
    }

    public double getOzone() {
        return ozone;
    }
}
