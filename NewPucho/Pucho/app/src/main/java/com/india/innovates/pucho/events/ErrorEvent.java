package com.india.innovates.pucho.events;

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
