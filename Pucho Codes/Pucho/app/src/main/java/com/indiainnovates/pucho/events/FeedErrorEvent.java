package com.indiainnovates.pucho.events;

/**
 * Created by Raghunandan on 09-02-2016.
 */
public class FeedErrorEvent {


    private Throwable e;

    public FeedErrorEvent(Throwable e)
    {
        this.e = e;
    }

    public Throwable getErrorEvent() {
        return e;
    }
}
