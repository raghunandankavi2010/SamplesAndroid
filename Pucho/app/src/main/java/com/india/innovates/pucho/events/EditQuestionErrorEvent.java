package com.india.innovates.pucho.events;

/**
 * Created by Raghunandan on 14-04-2016.
 */
public class EditQuestionErrorEvent {
    private String t;
    public EditQuestionErrorEvent(String t)
    {
        this.t = t;
    }

    public String getT() {
        return t;
    }
}
