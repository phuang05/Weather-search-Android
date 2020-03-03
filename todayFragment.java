package com.example.penghuang.hw9_01.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.penghuang.hw9_01.Class.weatherInfo;
import com.example.penghuang.hw9_01.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link todayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link todayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class todayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private weatherInfo info;
    private HashMap<String,Integer> summaryToIconResId = new HashMap<>();

    private OnFragmentInteractionListener mListener;

    public todayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param info weatherinfo
     * @return A new instance of fragment todayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static todayFragment newInstance(weatherInfo info) {
        todayFragment fragment = new todayFragment();
        Bundle args = new Bundle();
        args.putSerializable("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        if (getArguments() != null) {
            info = (weatherInfo) getArguments().getSerializable("info");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView windSpeedText = view.findViewById(R.id.windSpeedText);
        TextView pressureText = view.findViewById(R.id.pressureText);
        TextView precipitationText = view.findViewById(R.id.precipitationText);
        TextView temperatureText = view.findViewById(R.id.temperatureText);
        ImageView iconImage = view.findViewById(R.id.iconImage);
        TextView iconText = view.findViewById(R.id.iconText);
        TextView humidityText = view.findViewById(R.id.humidityText);
        TextView visibilityText = view.findViewById(R.id.visibilityText);
        TextView cloudCoverText = view.findViewById(R.id.cloudCoverText);
        TextView ozoneText = view.findViewById(R.id.ozoneText);

        windSpeedText.setText(String.format("%.2f",info.getWindSpeed())+"mph");
        pressureText.setText(String.format("%.2f",info.getPressure())+"mb");
        precipitationText.setText(String.format("%.2f",info.getPrecipitation())+"mmph");
        temperatureText.setText(info.getTemperature()+"˚F");
        iconImage.setImageResource(summaryToIconResId.get(info.getIcon()));
//        iconText.setText(info.getSummary());
        String iconTmp = info.getIcon();
//        Log.d("icontmp",iconTmp);
        if (iconTmp.equals("partly-cloudy-day")) iconTmp = "cloudy day";
        else if (iconTmp.equals("partly-cloudy-night")) iconTmp = "cloudy night";
        iconText.setText(iconTmp.replace('-',' '));
        humidityText.setText(String.format("%.0f",info.getHumidity()*100)+"%");
        visibilityText.setText(String.format("%.2f",info.getVisibility())+"km");
        cloudCoverText.setText(String.format("%.0f",info.getCloudCover()*100)+"%");
        ozoneText.setText(String.format("%.2f",info.getOzone())+"DU");
        view.findViewById(R.id.progressLayout).setVisibility(View.GONE);
        view.findViewById(R.id.layout).setVisibility(View.VISIBLE);

    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
