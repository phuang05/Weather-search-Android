package com.example.penghuang.hw9_01.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.penghuang.hw9_01.Fragment.main_fragment;

import java.util.List;

/**
 * Created by penghuang on 12/1/19.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> favouriteList;
    private long baseId = 0;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return main_fragment.newInstance(favouriteList.get(position));
    }


    @Override
    public int getCount() {
        return favouriteList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
//                            return super.getItemPosition(object);
        return MyPagerAdapter.POSITION_NONE;
    }


    public void notifyChangeInPosition(int n){
        baseId += getCount() + n;
    }
}
