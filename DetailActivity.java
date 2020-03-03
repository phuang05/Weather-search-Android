package com.example.penghuang.hw9_01.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.penghuang.hw9_01.Class.Favourite;
import com.example.penghuang.hw9_01.Class.weatherInfo;
import com.example.penghuang.hw9_01.Fragment.WeeklyFragment;
import com.example.penghuang.hw9_01.Fragment.main_fragment;
import com.example.penghuang.hw9_01.Fragment.photosFragment;
import com.example.penghuang.hw9_01.Fragment.todayFragment;
import com.example.penghuang.hw9_01.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DetailActivity extends AppCompatActivity   {


    private android.support.v7.app.ActionBar actionBar;
    private ArrayList<Fragment> fragmentContainer;
    private ViewPager viewPager;
    private weatherInfo info;
    private TabLayout.Tab todayTab;
    private TabLayout.Tab weeklyTab;
    private TabLayout.Tab photosTab;
    private String city;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);


        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        Log.d("tag","DetailActivity on create"+intent.getStringExtra("city"));
        info = (weatherInfo) intent.getSerializableExtra("info");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);


        fragmentContainer = new ArrayList<Fragment>();

//        fragmentContainer.add();
        fragmentContainer.add(new todayFragment().newInstance(info));
        fragmentContainer.add(new WeeklyFragment().newInstance(info));
        fragmentContainer.add(new photosFragment().newInstance(city));
//        fragmentContainer.add(new main_fragment().newInstance("new york"));
        this.viewPager = findViewById(R.id.viewPager);
        this.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentContainer.get(position);
            }

            @Override
            public int getCount() {
                return fragmentContainer.size();
            }
        });

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        todayTab = tabLayout.getTabAt(0);
        weeklyTab = tabLayout.getTabAt(1);
        photosTab = tabLayout.getTabAt(2);
        todayTab.setIcon(R.drawable.calendar_today);
        todayTab.setText("TODAY");

        weeklyTab.setIcon(R.drawable.trending_up);
        weeklyTab.setText("WEEKLY");
        photosTab.setIcon(R.drawable.google_photos);
        photosTab.setText("PHOTOS");
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int tabIconColor = ContextCompat.getColor(tabLayout.getContext(),R.color.tabSelectedIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                int tabIconColor = ContextCompat.getColor(tabLayout.getContext(),R.color.tabUnselectedIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        actionBar.setTitle(info.getCityText());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return false;
            case R.id.twitter:
                Intent intent = new Intent();
                String url = "https://twitter.com/intent/tweet?text=Check Out "
                        +info.getCityText() + " 's Weather! It is "+ info.getTemperature() +"˚F! "
                        + "&hashtags=CSCI571WeatherSearch";
                Uri uri = Uri.parse(url);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onDestroy() {

        SharedPreferences userinfo = getSharedPreferences("favoriteList",MODE_PRIVATE);
        SharedPreferences.Editor editor = userinfo.edit();
        Set<String> stringSet = new HashSet<>() ;

        for (int i =  Favourite.getInstance().getFavouriteList().size() -1;i > 0; i--) {
            stringSet.add(Favourite.getInstance().getFavouriteList().get(i));
        }
        editor.putStringSet("favoritelist",stringSet);
        Log.d("writeset",""+stringSet);
        editor.commit();
        super.onDestroy();
    }
}
