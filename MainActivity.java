package com.example.penghuang.hw9_01.Activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
//import android.widget.SearchView;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.penghuang.hw9_01.Adapter.AutoSuggestAdapter;
import com.example.penghuang.hw9_01.Adapter.MyPagerAdapter;
import com.example.penghuang.hw9_01.Api.ApiCall;
import com.example.penghuang.hw9_01.Class.Favourite;
import com.example.penghuang.hw9_01.Fragment.main_fragment;
import com.example.penghuang.hw9_01.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private main_fragment mfragment;
    private List<main_fragment> fragmentContainer = new ArrayList<main_fragment>();
    private ViewPager mainViewPager ;
//    private FragmentPagerAdapter fragmentPagerAdapter;
    private MyPagerAdapter fragmentPagerAdapter;


    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoSuggestAdapter<String> autoSuggestAdapter;
    private LinearLayout indicatorLayout;
    final static String replaceHolder = "Los Angeles, CA, USA";





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences userinfo = getSharedPreferences("favoriteList",MODE_PRIVATE);
//        SharedPreferences.Editor editor = userinfo.edit();
//        editor.putStringSet("favoritelist",null);
//        editor.commit();
//        Set<String> stringSet = new HashSet<>() ;
//        stringSet.add("New York, NY, US");
////        for (String str : stringSet) {
////            stringSet.add(str);
////        }
//        editor.putStringSet("favoritelist",stringSet);
//        Log.d("writeset",""+stringSet);
        Favourite.getInstance().getFavouriteList().add(replaceHolder);
        Set<String> stringSet = userinfo.getStringSet("favoritelist",null);
        Log.d("readset",""+stringSet);
        if (stringSet!=null) {
            for (String str : stringSet) {
                Favourite.getInstance().getFavouriteList().add(str);
            }
        }

        setContentView(R.layout.activity_main);
        ApiCall.getCurrentlocation(this,null,new Response.Listener<JSONObject>
                () {
            @Override
            public void onResponse(JSONObject result) {
//                currentTemperature.setText(re);
                Log.d("weatherinfo return:   ", "" + result);
                try {
                    String city = result.getString("city");
                    String region = result.getString("region");
                    String countryCode = result.getString("countryCode");
                    Favourite.getInstance().setCurrentLocation(city+", "+region+", "+countryCode);
                    Favourite.getInstance().getFavouriteList().remove(0);
                    Favourite.getInstance().getFavouriteList().add(0,city+", "+region+", "+countryCode);
//                    mfragment = new main_fragment().newInstance(city+", "+region+", "+countryCode);
//                    fragmentContainer.add(mfragment);

                    mainViewPager = findViewById(R.id.mainViewPager);
                    fragmentPagerAdapter =  new MyPagerAdapter(getSupportFragmentManager()) {
                        private long baseId = 0;

                        @Override
                        public Fragment getItem(int position) {
                            return main_fragment.newInstance(Favourite.getInstance().getFavouriteList().get(position));
                        }


                        @Override
                        public int getCount() {
                            return Favourite.getInstance().getFavouriteList().size();
                        }

                        @Override
                        public int getItemPosition(@NonNull Object object) {
//                            return super.getItemPosition(object);
                            return MyPagerAdapter.POSITION_NONE;
                        }

                        @Override
                        public void notifyDataSetChanged() {
                            super.notifyDataSetChanged();
                            List<ImageView> imageList = new ArrayList<>();
                            indicatorLayout =  (LinearLayout)findViewById(R.id.indicatorLayout);
                            indicatorLayout.removeAllViews();
                            for (int i = 0; i < Math.max(1,Favourite.getInstance().getFavouriteList().size());i++){
                                ImageView imageView = new ImageView(getBaseContext());
                                imageList.add(imageView);
                                View v = new View(getBaseContext());
                                v.setBackgroundResource(R.drawable.indicator);
                                v.setEnabled(false);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30,30);
                                if (i!=0){
                                    layoutParams.leftMargin = 10;
                                }
                                indicatorLayout.addView(v,layoutParams);
                            }
                        }

                        public void notifyChangeInPosition(int n){
                            baseId += getCount() + n;
                        }
                    };
                    Favourite.getInstance().fragmentPagerAdapter = fragmentPagerAdapter;

//                    List<String> favouriteList = Favourite.getInstance().getFavouriteList();
//                    for (int i =0;i<favouriteList.size();i++){
//                        fragmentContainer.add(new main_fragment().newInstanceAndfragAdapter(favouriteList.get(i),fragmentPagerAdapter,fragmentContainer));
//                    }
                    mainViewPager.setAdapter(fragmentPagerAdapter);
                    mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        int lastPosition= 0;
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            indicatorLayout.getChildAt(lastPosition).setEnabled(false);
                            indicatorLayout.getChildAt(position).setEnabled(true);
                            lastPosition = position;
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        autoSuggestAdapter = new AutoSuggestAdapter(this, android.R.layout.simple_dropdown_item_1line);
        final android.support.v7.widget.SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchAutoComplete.setDropDownBackgroundResource(R.color.colorWhite);
        searchAutoComplete.setAdapter(autoSuggestAdapter);


        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName("com.example.penghuang.hw9_01","com.example.penghuang.hw9_01.Activity.searchActivity");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        searchView.setOnQueryTextListener(
                  new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Log.d("tags","doqueryTextSubmit");

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Log.d("tag","textChange");
//                        ApiCall.autoComplete(MainActivity.this,newText,null,null);
//                        handler.removeMessages(TRIGGER_AUTO_COMPLETE);
//                        handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
//                                AUTO_COMPLETE_DELAY);
                        return false;
                    }
                }
        );
        searchAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(searchAutoComplete.getText())) {
                        makeApiCall(searchAutoComplete.getText().toString());
                    }
                }
                return false;
            }
        });



        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String queryString = (String) adapterView.getItemAtPosition(position);
                searchAutoComplete.setText(""+queryString);

            }
        });






//        return super.onCreateOptionsMenu(menu);
        return true;
    };


    private void makeApiCall(String text) {
        ApiCall.autoComplete(this, text, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //parsing logic, please change it as per your requirement
                List<String> stringList = new ArrayList<>();

                try {
//                    JSONObject responseObject = new JSONObject(response);


                    JSONObject responseObject = response;
                    JSONArray array = responseObject.getJSONArray("predictions");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
//                        JSONObject format = row.getJSONObject("structured_formatting");
//                        stringList.add(format.getString("main_text"));
                        stringList.add((row.getString("description")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if ((fragmentPagerAdapter!=null) ) {
//            fragmentContainer.add(mfragment);
//            List<String> favouriteList = Favourite.getInstance().getFavouriteList();
//            for (int i = 0; i < favouriteList.size(); i++) {
//                fragmentContainer.add(new main_fragment().newInstanceAndfragAdapter(favouriteList.get(i),fragmentPagerAdapter,fragmentContainer));
//            }

            fragmentPagerAdapter.notifyDataSetChanged();
        }
        List<ImageView> imageList = new ArrayList<>();
        indicatorLayout =  (LinearLayout)findViewById(R.id.indicatorLayout);
        indicatorLayout.removeAllViews();
        for (int i = 0; i < Math.max(1,Favourite.getInstance().getFavouriteList().size());i++){
            ImageView imageView = new ImageView(this);
            imageList.add(imageView);
            View v = new View(this);
            v.setBackgroundResource(R.drawable.indicator);
            v.setEnabled(false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30,30);
            if (i!=0){
                layoutParams.leftMargin = 10;
            }
            indicatorLayout.addView(v,layoutParams);
        }
        indicatorLayout.getChildAt(0).setEnabled(true);

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
//        editor.clear();
        editor.commit();
        super.onDestroy();
    }
}

