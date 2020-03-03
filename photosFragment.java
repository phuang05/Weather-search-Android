package com.example.penghuang.hw9_01.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.penghuang.hw9_01.Adapter.LinearAdapter;
import com.example.penghuang.hw9_01.Api.ApiCall;
import com.example.penghuang.hw9_01.Class.Eight_days_info;
import com.example.penghuang.hw9_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link photosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link photosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class photosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
    private String city;
    private List<String> cityImageUrls;
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    public photosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param city city
     * @return A new instance of fragment photosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static photosFragment newInstance(String city) {
        photosFragment fragment = new photosFragment();
        Bundle args = new Bundle();
        args.putString("city",city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = getArguments().getString("city");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photos, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        cityImageUrls = new ArrayList<>();
        ApiCall.getCityImageUrl(view.getContext(),city,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

//                currentTemperature.setText(re);
                try {
                    JSONArray data = response.getJSONArray("items");

                    for (int i = 0; i < 8; i++) {
                        JSONObject itemi = (JSONObject) data.get(i);
                        cityImageUrls.add(itemi.getString("link"));
                    }

                    recyclerView.setAdapter(new LinearAdapter(view.getContext(),cityImageUrls));
                    view.findViewById(R.id.progressLayout).setVisibility(View.GONE);
                    view.findViewById(R.id.recycleView).setVisibility(View.VISIBLE);


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
