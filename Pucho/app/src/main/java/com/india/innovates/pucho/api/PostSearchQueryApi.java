package com.india.innovates.pucho.api;

import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.models.SearchQueryResponse;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Raghunandan on 27-02-2016.
 */
public class PostSearchQueryApi {

    @Inject
    Retrofit retrofit;

    public PostSearchQueryApi()
    {
        PuchoApplication.netcomponent().inject(this);
    }

    public Observable<SearchQueryResponse> searchQuery(String searchString, int from) {

        Observable<SearchQueryResponse> response = retrofit.create(Api.class).postSearchQUery(searchString,from,true);
        response.cache();
        return response;

    }
}
