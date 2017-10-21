package com.peakaeriest.ladyspyder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.adapters.CustomGrid;


public class CategoriesFragment extends Fragment {

    GridView grid;
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    };
    int[] imageId = {
            R.drawable.ic_men,
            R.drawable.ic_women,
            R.drawable.ic_baby,
            R.drawable.ic_jewellery,
            R.drawable.ic_women,
            R.drawable.ic_baby,
            R.drawable.ic_jewellery,
            R.drawable.ic_women,
            R.drawable.ic_baby,
            R.drawable.ic_jewellery,
            R.drawable.ic_women,
            R.drawable.ic_baby,
            R.drawable.ic_jewellery,
            R.drawable.ic_women,
            R.drawable.ic_baby

    };

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.kd_fragment_categories, container, false);

        CustomGrid adapter = new CustomGrid(getActivity(), web, imageId);
        grid = (GridView) view.findViewById(R.id.gv_costumes);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }


}
