package com.india.innovates.pucho.presenters;

import com.india.innovates.pucho.api.PostSearchQueryApi;
import com.india.innovates.pucho.models.SearchQueryResponse;
import com.india.innovates.pucho.screen_contracts.SearchScreen;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Raghunandan on 27-02-2016.
 */
public class SearchPresenter {

    private PostSearchQueryApi postSearchQueryApi;
    private String searchString;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private SearchScreen searchScreen;
    private int from;

    @Inject
    public SearchPresenter(PostSearchQueryApi postSearchQueryApi)
    {
        this.postSearchQueryApi = postSearchQueryApi;
    }

    public void setContext(SearchScreen searchScreen) {
        this.searchScreen = searchScreen;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void getSearchResults() {

        Observable<SearchQueryResponse> observable = postSearchQueryApi.searchQuery(getSearchString(),getFrom());
       compositeSubscription.add(observable.subscribeOn(Schedulers.io())
       .observeOn(AndroidSchedulers.mainThread())
       .subscribe(new Subscriber<SearchQueryResponse>() {
           @Override
           public void onCompleted() {

           }

           @Override
           public void onError(Throwable e) {

               searchScreen.onError(e);
           }

           @Override
           public void onNext(SearchQueryResponse searchQueryResponse) {
              searchScreen.onSearchResponse(searchQueryResponse);
           }
       }));
    }

    public void onActivityDestroy() {

    }

    public void setInitialFromCount(int initialFromCount) {
        this.from = initialFromCount;
    }

    public int getFrom() {
        return from;
    }
}
