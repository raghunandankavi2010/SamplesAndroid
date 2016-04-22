package com.example.xyzreader.events;

/**
 * Created by Raghunandan on 23-04-2016.
 */
public class RefreshingEvent {

    private boolean bool;
    public RefreshingEvent(boolean bool){
        this.bool = bool;
    }

    public boolean isBool() {
        return bool;
    }
}
