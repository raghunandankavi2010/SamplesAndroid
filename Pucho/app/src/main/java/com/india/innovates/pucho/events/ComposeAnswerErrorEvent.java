package com.india.innovates.pucho.events;

/**
 * Created by Raghunandan on 09-02-2016.
 */
public class ComposeAnswerErrorEvent {

    private Throwable e;

    public ComposeAnswerErrorEvent(Throwable e)
    {
        this.e = e;
    }

    public Throwable getErrorEvent() {
        return e;
    }
}
