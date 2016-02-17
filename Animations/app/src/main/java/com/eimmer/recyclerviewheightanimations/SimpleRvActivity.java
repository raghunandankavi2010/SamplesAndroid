package com.eimmer.recyclerviewheightanimations;

import android.support.v4.app.Fragment;

import com.eimmer.recyclerviewheightanimations.fragments.SimpleAnimationFragment;

public class SimpleRvActivity extends RecyclerViewActivity {

    @Override
    protected Fragment getFragment() {
        return new SimpleAnimationFragment();
    }
}
