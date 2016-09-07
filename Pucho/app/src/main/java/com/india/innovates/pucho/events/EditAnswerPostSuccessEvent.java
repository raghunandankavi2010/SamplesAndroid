package com.india.innovates.pucho.events;

/**
 * Created by Raghunandan on 18-04-2016.
 */
public class EditAnswerPostSuccessEvent {

    private String success;
    public EditAnswerPostSuccessEvent(String success)
    {
        this.success =success;
    }

    public String getSuccess() {
        return success;
    }
}
