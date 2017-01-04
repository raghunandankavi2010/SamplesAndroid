package com.indiainnovates.pucho.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.R;

/**
 * Created by Raghunandan on 25-01-2016.
 */
public class TrendingFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((PuchoApplication)getActivity().getApplication()).component().inject(this);

        View view = inflater.inflate(R.layout.trending_fragment,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }
}
