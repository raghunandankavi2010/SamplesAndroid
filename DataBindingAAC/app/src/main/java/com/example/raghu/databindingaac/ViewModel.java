package com.example.raghu.databindingaac;

import android.util.Log;

public class ViewModel extends android.arch.lifecycle.ViewModel {

    public ObservableString someText = new ObservableString();

    public void printData() {
        Log.i("TAG",""+someText.get());
    }
}
