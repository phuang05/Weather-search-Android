package com.example.penghuang.hw9_01.Class;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.penghuang.hw9_01.Adapter.MyPagerAdapter;
import com.example.penghuang.hw9_01.Fragment.main_fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by penghuang on 11/30/19.
 */

public class Favourite {
    public static Favourite instance = new Favourite();
    public List<String> favouriteList = new ArrayList<>();
    public MyPagerAdapter fragmentPagerAdapter;
    private String currentLocation;
    private Favourite(){
        currentLocation="";

//        favouriteList.add("New York, NY, USA");
//        favouriteList.add("Seattle, WA, USA");

    }

    public static Favourite getInstance(){
        return instance;
    }

    public List<String> getFavouriteList() {
        return favouriteList;
    }

    public MyPagerAdapter getFragmentPagerAdapter() {
        return fragmentPagerAdapter;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}
