package com.example.raghu.databindingaac;

import android.databinding.BaseObservable;

import java.util.Objects;

/**
 * Created by raghu on 3/1/18.
 */

public class BindableString extends BaseObservable {
    private String value;
    public String get() {
        return value != null ? value : "";
    }
    public void set(String value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            notifyChange();
        }
    }
    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }
}