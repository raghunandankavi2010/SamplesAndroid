package com.example.raghu.databindingaac;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

public class ViewModel extends android.arch.lifecycle.AndroidViewModel {

    public ObservableString someText = new ObservableString();
    public ObservableString error = new ObservableString();

    public ViewModel(Application application) {
        super(application);
    }



    public void printData() {
        Log.i("TAG",""+someText.get());
    }

    public void reset() {
        error.set(null);
        someText.set(null);

    }

    public void check() {
        if(TextUtils.isEmpty(someText.get())){
            error.set("Edit Text is Empty");
        }else {
            error.set("");
        }
    }
}
