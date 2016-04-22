package com.indiainnovates.pucho.presenters;

import android.app.usage.UsageEvents;

import com.indiainnovates.pucho.api.FeedApi;
import com.indiainnovates.pucho.events.ErrorEvent;
import com.indiainnovates.pucho.events.FeedErrorEvent;
import com.indiainnovates.pucho.events.FeedResponseEvent;
import com.indiainnovates.pucho.fragments.FeedFragment;
import com.indiainnovates.pucho.models.FeedResponse;
import com.indiainnovates.pucho.screen_contracts.FeedFragmentScreen;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Raghunandan on 31-01-2016.
 */
public class FeedPresenter {

    private FeedApi feedApi;
    private FeedFragmentScreen feedFragmentScreen;
    private int initialPageCount;
   // private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Observable<FeedResponse> feedResponseObservable;

    @Inject
    public FeedPresenter(FeedApi feedApi) {
        this.feedApi = feedApi;
    }

    public void setPresenter(FeedFragmentScreen Screen) {
        this.feedFragmentScreen = Screen;
    }

    public void hideRecyclerView() {
        feedFragmentScreen.onHideRecyclerView();
    }

    public void displayProgressBar() {

        feedFragmentScreen.onDisplayProgressBar();
    }

    public void fetchFeed(String language) {

        feedResponseObservable = feedApi.fetchFeed(getInitialPageCount());
        feedResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FeedResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        feedFragmentScreen.onError(e);
                        //EventBus.getDefault().post(new FeedErrorEvent(e));
                    }

                    @Override
                    public void onNext(FeedResponse feedResponse) {

                        feedFragmentScreen.onFeedFetched(feedResponse);
                        //EventBus.getDefault().post(new FeedResponseEvent(feedResponse));
                    }
                });
    }

    public void setInitialPageCount(int initialPageCount) {
        this.initialPageCount = initialPageCount;
    }

    public int getInitialPageCount() {
        return initialPageCount;
    }

    public void onDestroyFragment() {

        //feedFragmentScreen = null;
        if(feedResponseObservable!=null)
        feedResponseObservable.unsubscribeOn(Schedulers.io());
    }

    public void hideErrorText() {
        feedFragmentScreen.onHideErrorText();
    }

    public void displayErrorText() {
        feedFragmentScreen.onDisplayErrorText();
    }

    public void hideProgressBar() {

        feedFragmentScreen.onHideProgressBar();
    }

    public void displayRecyclerView() {
        feedFragmentScreen.onDisplayRecyclerView();
    }
}