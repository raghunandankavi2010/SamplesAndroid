package com.indiainnovates.pucho.screen_contracts;

/**
 * Created by Raghunandan on 31-01-2016.
 */
public interface FeedFragmentScreen {

    void onDisplayProgressBar();
    void onDisplayRecyclerView();
    void onDisplayErrorText();

    void onHideProgressBar();
    void onHideRecyclerView();
    void onHideErrorText();
}
