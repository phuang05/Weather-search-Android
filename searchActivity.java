package com.example.penghuang.hw9_01.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.penghuang.hw9_01.Class.Favourite;
import com.example.penghuang.hw9_01.Fragment.main_fragment;
import com.example.penghuang.hw9_01.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class searchActivity extends AppCompatActivity {


    private main_fragment mfragment;
    private FloatingActionButton floatingActionButton;

        private android.support.v7.app.ActionBar actionBar;


        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
            setContentView(R.layout.activity_search);

            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);

            final Intent intent = getIntent();
            Log.d("tag","dogetIntent");
            handleIntent(intent);

            final Intent intentTmp = intent;
            floatingActionButton = findViewById(R.id.floatActionButton);
            final List<String > favouriteList = Favourite.getInstance().getFavouriteList();
            if (favouriteList.contains(intentTmp.getStringExtra(SearchManager.QUERY))){
                floatingActionButton.setImageResource(R.drawable.map_marker_minus);
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(searchActivity.this,intentTmp.getStringExtra(SearchManager.QUERY)+" was removed from favorites", Toast.LENGTH_SHORT).show();
                        favouriteList.remove(intentTmp.getStringExtra(SearchManager.QUERY));
                        floatingActionButton.setImageResource(R.drawable.map_marker_plus);
                        floatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(searchActivity.this,intentTmp.getStringExtra(SearchManager.QUERY)+" was added to favorites", Toast.LENGTH_SHORT).show();
                                floatingActionButton.setImageResource(R.drawable.map_marker_minus);
                                favouriteList.add(intentTmp.getStringExtra(SearchManager.QUERY));
                            }
                        });
                    }
                });
            }
            else {

                floatingActionButton.setImageResource(R.drawable.map_marker_plus);
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(searchActivity.this,intentTmp.getStringExtra(SearchManager.QUERY)+" was added to favorites", Toast.LENGTH_SHORT).show();

                        favouriteList.add(intentTmp.getStringExtra(SearchManager.QUERY));

                        floatingActionButton.setImageResource(R.drawable.map_marker_minus);
                        floatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(searchActivity.this,intentTmp.getStringExtra(SearchManager.QUERY)+" was removed from favorites", Toast.LENGTH_SHORT).show();
                                floatingActionButton.setImageResource(R.drawable.map_marker_plus);
                                favouriteList.remove(intentTmp.getStringExtra(SearchManager.QUERY));
                            }
                        });
                    }
                });
            }
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


        @Override
        protected void onNewIntent(Intent intent) {
            Log.d("TAG","onNewIntent");

            super.onNewIntent(intent);
            setIntent(intent);
            handleIntent(intent);

        }


        private void handleIntent(Intent intent){
            Log.d("tag","dohandleIntent");
            if (Intent.ACTION_SEARCH.equals(
                    intent.getAction()
            )){
                String query = intent.getStringExtra(SearchManager.QUERY);
                actionBar.setTitle(query);
                doMySearch(query);
            }
        }



        private void doMySearch(String query){
            Log.d("tag",query);
            mfragment = new main_fragment().newInstance(query);
            getSupportFragmentManager().beginTransaction().add(R.id.detail_container,mfragment).commitAllowingStateLoss();


        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return false;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }



    }

