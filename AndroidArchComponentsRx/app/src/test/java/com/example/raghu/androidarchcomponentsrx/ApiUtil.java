package com.example.raghu.androidarchcomponentsrx;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.raghu.androidarchcomponentsrx.models.Example;
import com.example.raghu.androidarchcomponentsrx.models.User;
import com.example.raghu.androidarchcomponentsrx.vo.Resource;


public class ApiUtil {
    public static <T> LiveData<Resource<T>> successCall(T data) {
        return createCall(Resource.success(data));
    }
    public static <T> LiveData<Resource<T>> createCall(Resource<T> response) {
        MutableLiveData<Resource<T>> data = new MutableLiveData<>();
        data.setValue(response);
        return data;
    }

}