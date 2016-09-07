package com.india.innovates.pucho.events;

/**
 * Created by Raghunandan on 09-02-2016.
 */
public class AskQuestionErrorEvent {

    private Throwable e;

    public AskQuestionErrorEvent(Throwable e)
    {
        this.e = e;
    }

    public Throwable getErrorEvent() {
        return e;
    }
}
