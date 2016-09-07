package com.india.innovates.pucho.api;

import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.models.FeedResponse;

import javax.inject.Inject;

import retrofit2.Retrofit;
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
