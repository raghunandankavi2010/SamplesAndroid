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


    public FeedApi()
    {
        PuchoApplication.netcomponent().inject(this);
    }

    public Observable<FeedResponse> fetchFeed(int pageCount) {
        // 10 is per_page
        Observable<FeedResponse> response = retrofit.create(Api.class).fetch_Feed(pageCount,10);
        response.cache();
        return response;
    }
}
