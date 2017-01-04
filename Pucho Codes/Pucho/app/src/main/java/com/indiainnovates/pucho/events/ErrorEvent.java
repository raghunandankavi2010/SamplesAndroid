package com.indiainnovates.pucho.events;

import com.indiainnovates.pucho.models.LoginResponse;

/**
 * Created by Raghunandan on 26-12-2015.
 */
public class ErrorEvent {

    private Throwable e;

    public ErrorEvent(Throwable e)
    {
        this.e = e;
    }

    public Throwable getErrorEvent() {
        return e;
    }

}
