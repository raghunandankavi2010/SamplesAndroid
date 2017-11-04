package com.ladyspyd.fragments;

import android.app.Fragment;

import com.ladyspyd.activities.LSMainActivity;

public abstract class BaseFragment extends Fragment {
    public android.support.v7.app.ActionBar getActionBar() {
        return ((LSMainActivity) getActivity()).getSupportActionBar();
    }
}