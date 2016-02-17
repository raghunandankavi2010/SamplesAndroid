package com.eimmer.recyclerviewheightanimations.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eimmer.recyclerviewheightanimations.MainActivityFragment;
import com.eimmer.recyclerviewheightanimations.MyAnimator;
import com.eimmer.recyclerviewheightanimations.R;
import com.eimmer.recyclerviewheightanimations.adapters.QuestionAdapter;
import com.eimmer.recyclerviewheightanimations.adapters.SimpleAdapter;

public class SimpleAnimationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if (MainActivityFragment.ADAPTER_TYPE == MainActivityFragment.AdapterType.COLOR) {
            recyclerView.setAdapter(new SimpleAdapter());
        } else {
            recyclerView.setItemAnimator(new MyAnimator());
            recyclerView.setAdapter(new QuestionAdapter());
        }

        return rootView;
    }

}
