package com.indiainnovates.pucho.api;

import com.indiainnovates.pucho.PuchoApplication;
//import com.indiainnovates.pucho.dagger.DependencyInjector;
import com.indiainnovates.pucho.models.LoginResponse;
import com.indiainnovates.pucho.utils.UrlStrings;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Raghunandan on 28-12-2015.
 */
public class QuestionsApi {

    //@Inject
    //OkHttpClient okHttpClient;

    @Inject
    Retrofit retrofit;

    public QuestionsApi()
    {
        PuchoApplication.netcomponent().inject(this);
        //DependencyInjector.getNetworkComponent().inject(this);
    }

}
