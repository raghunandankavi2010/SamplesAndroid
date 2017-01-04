package com.indiainnovates.pucho.api;

import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.models.SearchQueryResponse;

import javax.inject.Inject;

import retrofit.Retrofit;
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

        Observable<SearchQueryResponse> response = retrofit.create(Api.class).postSearchQUery(searchString,from);
        response.cache();
        return response;

    }
}
