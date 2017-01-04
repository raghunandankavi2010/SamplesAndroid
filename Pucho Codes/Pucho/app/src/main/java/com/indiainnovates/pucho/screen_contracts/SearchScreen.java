package com.indiainnovates.pucho.screen_contracts;

import com.indiainnovates.pucho.models.SearchQueryResponse;

/**
 * Created by Raghunandan on 27-02-2016.
 */
public interface SearchScreen {

    void onError(Throwable e);
    void onSearchResponse(SearchQueryResponse searchQueryResponse);
}
