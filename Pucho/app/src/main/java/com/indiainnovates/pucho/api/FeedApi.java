package com.indiainnovates.pucho.api;

import android.util.Log;

import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.models.FeedResponse;
import com.squareup.okhttp.ResponseBody;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;

/**
 * Created by Raghunandan on 31-01-2016.
 */
public class FeedApi {

    @Inject
    Retrofit retrofit;
    private String language;


    public FeedApi()
    {
        PuchoApplication.netcomponent().inject(this);
    }

    public Observable<FeedResponse> fetchFeed(int pageCount) {
        // 10 is per_page
        /*this.language = lan;
        if(lan.equals("Hindi"))
        {
            this.language ="hi";
        }else
        {
            this.language="en";
        }*/
        Observable<FeedResponse> response = retrofit.create(Api.class).fetch_Feed(pageCount,10,true);
        response.cache();
        return response;
    }
}
