package com.example.penghuang.hw9_01.Class;

import android.text.method.SingleLineTransformationMethod;

/**
 * Created by penghuang on 11/24/19.
 */

public class LocationInfo {
    private String street;
    private String city;
    private String state;



    public LocationInfo(String street, String city, String state){
        setCity(city);
        setStreet(street);
        setState(state);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }
}
