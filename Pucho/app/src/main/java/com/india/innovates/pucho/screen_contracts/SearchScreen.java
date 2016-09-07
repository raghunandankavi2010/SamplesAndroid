package com.india.innovates.pucho.screen_contracts;

import com.india.innovates.pucho.models.SearchQueryResponse;

/**
 * Created by Raghunandan on 27-02-2016.
 */
public interface SearchScreen {

    void onError(Throwable e);
    void onSearchResponse(SearchQueryResponse searchQueryResponse);
}
