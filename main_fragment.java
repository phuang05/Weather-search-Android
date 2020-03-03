package com.example.penghuang.hw9_01.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.penghuang.hw9_01.Activity.DetailActivity;
import com.example.penghuang.hw9_01.Activity.searchActivity;
import com.example.penghuang.hw9_01.Adapter.LinearAdapter;
import com.example.penghuang.hw9_01.Adapter.MyListAdapter;
import com.example.penghuang.hw9_01.Adapter.MyPagerAdapter;
import com.example.penghuang.hw9_01.Api.ApiCall;
import com.example.penghuang.hw9_01.Class.Favourite;
import com.example.penghuang.hw9_01.Class.weatherInfo;
import com.example.penghuang.hw9_01.Class.Eight_days_info;
import com.example.penghuang.hw9_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link main_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link main_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class main_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters

    private String city;
    private double lat;
    private double lng;
    private Activity parentActivity;
    private CardView mBtnDetail;
    private TextView currentTemperature;
    private ImageView currentSummaryPic;
    private TextView currentSummaryText;
    private TextView currentLocationText;
    private TextView currentLocationHumidity;
    private TextView currentLocationWindSpeed;
    private TextView currentLocationVisibility;
    private TextView currentLocationPressure;
    private ListView mLv1;
    private String awsUrl = "http://571hw-hw8.pekp2tptpj.us-west-1.elasticbeanstalk.com";
    private HashMap<String,Integer> summaryToIconResId = new HashMap<>();
    private weatherInfo currentLocation = new weatherInfo();
    private static boolean isFirst = true;

    private OnFragmentInteractionListener mListener;
    private Bundle inputBundle;
    public String cityText;
    private LinearLayout progress;
    private LinearLayout mainLayout;
    private LinearLayout indicatorLayout;
//    private static FragmentPagerAdapter thisFragmentPagerAdapter;
    private static MyPagerAdapter thisFragmentPagerAdapter;

    private static List<main_fragment> thisFragmentContainer;
    public main_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param city City Name.
     * @return A new instance of fragment main_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static main_fragment newInstance(String city) {
        main_fragment fragment = new main_fragment();
        Bundle args = new Bundle();
        args.putString("city",city);
        isFirst = true;
        thisFragmentPagerAdapter = Favourite.getInstance().fragmentPagerAdapter;
        fragment.setArguments(args);
        return fragment;
    }
    public static main_fragment newInstanceAndfragAdapter(String city, MyPagerAdapter fragmentPagerAdapter,   List<main_fragment> fragmentContainer) {
        main_fragment fragment = new main_fragment();
        Bundle args = new Bundle();
        args.putString("city",city);
        Log.d("putcity",city);
        thisFragmentPagerAdapter = fragmentPagerAdapter;
        thisFragmentContainer = fragmentContainer;
        fragment.setArguments(args);
        return fragment;
    }
    public static main_fragment newInstanceByLatLng(double lat,double lng) {
        main_fragment fragment = new main_fragment();
        Bundle args = new Bundle();
        args.putDouble("lat",lat);
        args.putDouble("lng",lng);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = view.findViewById(R.id.progressLayout);

        progress.setVisibility(View.VISIBLE);
        mainLayout = view.findViewById(R.id.view_1);
        mainLayout.setVisibility(View.GONE);



        if (getArguments() != null) {

            inputBundle = getArguments();
            Log.d("oncreatecity",getArguments().getString("city"));
//            Log.d("oncreatecity",cityText);

            if (inputBundle.getString("city")!=null) {
                cityText = inputBundle.getString("city");
                city = inputBundle.getString("city").split(",")[0];

                currentLocation.setCityText(cityText);
            }
            else{
                lat = inputBundle.getDouble("lat");
                lng = inputBundle.getDouble("lng");
            }
        }

        parentActivity = getActivity();
        currentTemperature = view.findViewById(R.id.currentTemperature);
        currentSummaryPic = view.findViewById(R.id.currentLocationSummaryPic);
        currentSummaryText = view.findViewById(R.id.currentLocationSummaryText);
        currentLocationText = view.findViewById(R.id.currentLocationText);
        currentLocationHumidity = view.findViewById(R.id.currentLocationHumidity);
        currentLocationWindSpeed = view.findViewById(R.id.currentLocationWindSpeed);
        currentLocationVisibility = view.findViewById(R.id.currentLocationVisibility);
        currentLocationPressure = view.findViewById(R.id.currentLocationPressure);


        mBtnDetail = (CardView) view.findViewById(R.id.cardView);

//        String tempUrl = "https://lh3.googleusercontent.com/1rZuQB0nilW1Y6fg2cpWsuY6kQ9uWsVnqF4f8K9Yk1nFNgSjWe0xcVBnoQTCOl93AuDRZUfgIkM6lj1fc_WxpOp0JCPEDG4SJq2Ea5o=s660";


        mLv1 = (ListView) view.findViewById(R.id.view_list);


        summaryToIconResId.put("default", R.drawable.weather_sunny);
        summaryToIconResId.put("clear-day", R.drawable.weather_sunny);
        summaryToIconResId.put("clear-night", R.drawable.weather_night);
        summaryToIconResId.put("rain", R.drawable.weather_rainy);
        summaryToIconResId.put("sleet", R.drawable.weather_snowy_rainy);
        summaryToIconResId.put("snow", R.drawable.weather_snowy);
        summaryToIconResId.put("wind", R.drawable.weather_windy_variant);
        summaryToIconResId.put("fog", R.drawable.weather_fog);
        summaryToIconResId.put("cloudy", R.drawable.weather_cloudy);
        summaryToIconResId.put("partly-cloudy-night", R.drawable.weather_night_partly_cloudy);
        summaryToIconResId.put("partly-cloudy-day", R.drawable.weather_partly_cloudy);


        if (city!=null) {
            getCityWeatherInfo(city);
            Log.d("getcityweatherinfo",city);
        }
        else{
            getCurrentDailyWeatherInfo(lat,lng);
//            getCurrentSummaryWeatherInfo(lat,lng);
        }
        final String str = cityText ;
        final FloatingActionButton floatingActionButton = view.findViewById(R.id.floatActionButton);
        final List<String > favouriteList = Favourite.getInstance().getFavouriteList();
//        if (favouriteList.contains(str)){
        Log.d("cityandcur",cityText+"    "+Favourite.getInstance().getCurrentLocation());
        if (cityText.equals(Favourite.getInstance().getCurrentLocation())){
            floatingActionButton.hide();

        }
        else{
            floatingActionButton.setImageResource(R.drawable.map_marker_minus);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),str+"was removed from favorites", Toast.LENGTH_SHORT).show();

                    Log.d("remove",favouriteList.get(0));
                    Favourite.getInstance().getFavouriteList().remove(str);
                    Log.d("remove",str);
                    Log.d("remove",favouriteList+"");


                    thisFragmentPagerAdapter.notifyDataSetChanged();

                }
            });
        }


    }


    public void getCityWeatherInfo(String city){
        ApiCall.getGeo(parentActivity,city,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

//                currentTemperature.setText(re);
                Log.d("getGeo return:   ", "" + response);

                try {
                    if (!response.get(0).toString().equals("error")) {
                        lat = Double.valueOf(response.get(0).toString());
                        lng = Double.valueOf(response.get(1).toString());

                    }
                    else{
                        cityText = "not Found";
                        lat = 0;
                        lng = 0;
                    }
                    getCurrentDailyWeatherInfo(lat,lng);
//                    getCurrentSummaryWeatherInfo(lat,lng);
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





    public void getCurrentSummaryWeatherInfo(double lat, double lng) {
        String jsonUrl = awsUrl + "/forecast" + "?lat=" + lat + "&lng=" + lng;
//        final weatherInfo info = new weatherInfo();
        ApiCall.makeJson(parentActivity, jsonUrl, new Response.Listener<JSONObject>
                () {
            @Override
            public void onResponse(JSONObject result) {
//                currentTemperature.setText(re);
                Log.d("weatherinfo return:   ", "" + result);
                try {

                    currentLocation.setDailySummary(result.getJSONObject("daily").getString("summary"));
                    JSONObject currently = result.getJSONObject("currently");
                    currentLocation.setTemperature(Integer.toString(Math.round(currently.getInt("temperature"))));
                    currentLocation.setSummary(currently.getString("summary"));
                    currentLocation.setIcon(currently.getString("icon"));
//                    currentLocation.setTimeZone(result.getString("timezone"));
                    currentLocation.setTimeZone(cityText);
                    currentLocation.setHumidity(currently.getDouble("humidity"));
                    currentLocation.setWindSpeed(currently.getDouble("windSpeed"));
                    currentLocation.setVisibility(currently.getDouble("visibility"));
                    currentLocation.setPressure(currently.getDouble("pressure"));
                    currentLocation.setOzone(currently.getDouble("ozone"));
                    setCurrentSummary(currentLocation);

//                    currentLocation = info;
//                    info.se; currently.getString("lcoation"));


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
//        return;

    }

    public void setCurrentSummary(weatherInfo info){
        Log.d("tags", "setCurrentSummary");
        currentTemperature.setText(info.getTemperature());
        currentSummaryText.setText(info.getSummary());
        currentSummaryPic.setImageResource(summaryToIconResId.get(info.getIcon()));
        currentLocationText.setText(info.getTimeZone());
        currentLocationHumidity.setText(String.format("%.0f",info.getHumidity()*100)+"%");
        currentLocationWindSpeed.setText(String.format("%.2f",info.getWindSpeed())+"mph");
        currentLocationVisibility.setText(String.format("%.2f",info.getVisibility())+"km");
        currentLocationPressure.setText(String.format("%.2f",info.getPressure())+"mb");
        final weatherInfo infotemp = info;
        mBtnDetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(parentActivity,DetailActivity.class);
                intent.putExtra("city",city);
                intent.putExtra("info",infotemp);

                startActivity(intent);
            }
        });
        progress.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
    }



    //method for search current hourly
    public void  getCurrentDailyWeatherInfo(double lat,double lng){
        String jsonUrl = awsUrl+"/hourly" +"?lat="+lat+"&lng="+lng+"?exclude=minutely,hourly,alerts,flags";
        final double lattemp = lat;
        final double lngtemp = lng;
        ApiCall.makeJson(parentActivity, jsonUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray data = response.getJSONObject("daily").getJSONArray("data");
                    List<Eight_days_info> eight_days_info_list = new ArrayList<Eight_days_info>();
                    Log.d("TAG", "datai");
                    for (int i = 0; i < 8; i++) {
                        JSONObject datai = (JSONObject) data.get(i);
                        eight_days_info_list.add(new Eight_days_info(
                                new SimpleDateFormat("MM/dd/yyyy").format(new Date(1000 * datai.getLong("time"))),
                                summaryToIconResId.get(datai.getString("icon")),
                                datai.getInt("temperatureLow"),
                                datai.getInt("temperatureHigh")));
                    }
                    currentLocation.setEight_days_info_list(eight_days_info_list);
                    setDailyInfo(currentLocation);
                    getCurrentSummaryWeatherInfo(lattemp,lngtemp);
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
        return ;



    }

    public void setDailyInfo(weatherInfo info){
        Log.d("tags", "setDailyInfo");
        List<Eight_days_info> eight_days_infos = info.getEight_days_info_list();
        mLv1.setAdapter(new MyListAdapter(parentActivity,R.id.list_item,eight_days_infos));

//        mLv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(parentActivity,"pos:" + i,Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }




    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
//        progress.setVisibility(View.GONE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
