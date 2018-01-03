package com.example.raghu.databindingaac;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

public class ViewModel extends android.arch.lifecycle.AndroidViewModel {

    public BindableString someText = new BindableString();
    public BindableString error = new BindableString();
    public BindableString someText2 = new BindableString();
    public BindableString error2 = new BindableString();

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
        if(TextUtils.isEmpty(someText2.get())){
            error2.set("Edit Text 2is Empty");
        }else {
            error2.set("");
        }
    }
}
