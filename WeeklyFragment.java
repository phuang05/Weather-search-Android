package com.example.penghuang.hw9_01.Fragment;

import android.content.ComponentName;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.penghuang.hw9_01.Class.Eight_days_info;
import com.example.penghuang.hw9_01.Class.weatherInfo;
import com.example.penghuang.hw9_01.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeeklyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeeklyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private LineChart lineChart;

    // TODO: Rename and change types of parameters
    private weatherInfo info;

    private HashMap<String,Integer> summaryToIconResId = new HashMap<>();
    private OnFragmentInteractionListener mListener;
    private List<Entry> temperatureHigh = new ArrayList<>();
    private List<Entry> temperatureLow = new ArrayList<>();
    private TextView summary;
    private ImageView icon;

    public WeeklyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param info weatherInfo

     * @return A new instance of fragment WeeklyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyFragment newInstance(weatherInfo info) {
        WeeklyFragment fragment = new WeeklyFragment();
        Bundle args = new Bundle();
        args.putSerializable("info",info);
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
        return inflater.inflate(R.layout.fragment_weekly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        icon = view.findViewById(R.id.icon);
        icon.setImageResource(summaryToIconResId.get(info.getIcon()));
        summary = view.findViewById(R.id.summary);
        summary.setText(info.getDailySummary());
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.WHITE);
        legend.setFormSize(19f);
        legend.setTextSize(19f);
        YAxis rightyAxis = lineChart.getAxisRight();
        rightyAxis.setEnabled(true);
        rightyAxis.setDrawLabels(true);
        rightyAxis.setTextColor(Color.WHITE);
        rightyAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightyAxis.setTextSize(10f);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawLabels(true);
        yAxis.setTextColor(Color.WHITE);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(10f);
        XAxis xaxis = lineChart.getXAxis();
        xaxis.setPosition(XAxis.XAxisPosition.TOP);
        xaxis.setDrawLabels(true);
        xaxis.setTextColor(Color.WHITE);
        xaxis.setTextSize(10f);
        List<Eight_days_info> eight_days_info_list = info.getEight_days_info_list();
        for (int i = 0; i < eight_days_info_list.size();i++){
            temperatureHigh.add(new Entry(i,eight_days_info_list.get(i).getTemperatureHigh()));
            temperatureLow.add(new Entry(i,eight_days_info_list.get(i).getTemperatureLow()));
        }
        LineDataSet highDataSet = new LineDataSet(temperatureHigh,"Maximum Temperature");
        highDataSet.setColor(Color.YELLOW);

        LineDataSet lowDataSet = new LineDataSet(temperatureLow,"Minimum Temperature");
        lowDataSet.setColor(Color.rgb(204, 153, 255));
        LineData lineData = new LineData();
        lineData.addDataSet(lowDataSet);
        lineData.addDataSet(highDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
        view.findViewById(R.id.progressLayout).setVisibility(View.GONE);
        view.findViewById(R.id.layout).setVisibility(View.VISIBLE);



        super.onViewCreated(view, savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
