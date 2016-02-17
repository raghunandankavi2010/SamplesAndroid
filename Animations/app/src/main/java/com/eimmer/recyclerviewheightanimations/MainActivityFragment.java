package com.eimmer.recyclerviewheightanimations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivityFragment extends Fragment {

    public enum AdapterType {
        COLOR,
        QUESTIONS
    }

    public static AdapterType ADAPTER_TYPE = AdapterType.COLOR;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_main, container, false);

        inflate.findViewById(R.id.simpleExample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SimpleRvActivity.class));
            }
        });

        inflate.findViewById(R.id.questionExample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADAPTER_TYPE = AdapterType.QUESTIONS;
                startActivity(new Intent(getActivity(), SimpleRvActivity.class));
            }
        });

        return inflate;
    }
}
