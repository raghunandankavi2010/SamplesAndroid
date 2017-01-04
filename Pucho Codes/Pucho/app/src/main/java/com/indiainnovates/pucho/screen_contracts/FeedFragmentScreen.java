package com.indiainnovates.pucho.screen_contracts;

import com.indiainnovates.pucho.models.FeedResponse;

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
    void onFeedFetched(FeedResponse feedResponse);
    void onError(Throwable e);
}
