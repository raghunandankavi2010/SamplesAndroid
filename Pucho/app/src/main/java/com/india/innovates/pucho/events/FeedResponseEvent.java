package com.india.innovates.pucho.events;

import com.india.innovates.pucho.models.FeedResponse;

/**
 * Created by Raghunandan on 31-01-2016.
 */
public class FeedResponseEvent {


    private FeedResponse feedResponse;
    public FeedResponseEvent(FeedResponse feedResponse) {

        this.feedResponse = feedResponse;
    }

    public FeedResponse getFeedResponse() {
        return feedResponse;
    }
}
