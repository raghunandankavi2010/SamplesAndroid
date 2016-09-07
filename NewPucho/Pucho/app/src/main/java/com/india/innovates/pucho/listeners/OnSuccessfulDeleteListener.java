package com.india.innovates.pucho.listeners;

/**
 * Created by Raghunandan on 21-03-2016.
 */
public interface OnSuccessfulDeleteListener {

    void onSuccess();
    void onFailure();
    void onError(Throwable e);
}
