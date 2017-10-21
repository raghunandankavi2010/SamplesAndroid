package com.peakaeriest.ladyspyder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peakaeriest.ladyspyder.R;


public class ProfileFragment extends Fragment {

    TextView tvChangeLang;
    TextView tvName, tvEmail, tvMobile, tvFlatStreet, tvFlatStreet1, tvStateCity, tvPostPin, tv_change;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.kd_fragment_profile, container, false);

        return view;
    }



}
