package com.india.innovates.pucho.events;

import com.india.innovates.pucho.models.SuccessSignUpResponse;

import java.util.List;

/**
 * Created by Raghunandan on 26-12-2015.
 */
public class SuccessSignUpResponseEvent {

    private List<SuccessSignUpResponse> listSignUpResponse;

    public SuccessSignUpResponseEvent(List<SuccessSignUpResponse> listSignUpResponse)
    {
        this.listSignUpResponse = listSignUpResponse;
    }

    public List<SuccessSignUpResponse> getListSignUpResponse() {
        return listSignUpResponse;
    }
}
