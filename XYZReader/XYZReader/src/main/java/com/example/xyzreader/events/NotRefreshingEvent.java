package com.example.xyzreader.events;

/**
 * Created by Raghunandan on 23-04-2016.
 */
public class NotRefreshingEvent {

    private boolean bool;
    public NotRefreshingEvent(boolean bool){
        this.bool = bool;
    }

    public boolean isBool() {
        return bool;
    }
}
