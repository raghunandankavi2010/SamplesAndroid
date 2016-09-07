package com.india.innovates.pucho.presenters;

import com.india.innovates.pucho.api.MyQuestionsApi;
import com.india.innovates.pucho.models.MyQuestionsFetched;
import com.india.innovates.pucho.screen_contracts.MyQuestionScreen;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Raghunandan on 18-03-2016.
 */
public class MyQuestionsPresenter {

    private MyQuestionsApi myQuestionsApi;
    private MyQuestionScreen myQuestionScreen;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Observable<List<MyQuestionsFetched>> observable;

    @Inject
    public MyQuestionsPresenter(MyQuestionsApi myQuestionsApi)
    {
        this.myQuestionsApi = myQuestionsApi;
    }

    public void setContext(MyQuestionScreen myQuestionScreen) {
        this.myQuestionScreen = myQuestionScreen;
    }

    public void fetchMyQuestions(int userId) {

        Observable<List<MyQuestionsFetched>> observable = myQuestionsApi.fetch_My_Questions(userId);

        compositeSubscription.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MyQuestionsFetched>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {

                        myQuestionScreen.onError(e);
                    }

                    @Override
                    public void onNext(List<MyQuestionsFetched> myQuestionses) {

                        myQuestionScreen.fetchedQuestions(myQuestionses);
                    }


                }));
    }

    public void displayProgressBar() {
        myQuestionScreen.displayProgressBar();
    }

    public void dismissProgressBar() {
        myQuestionScreen.dismissProgressBar();
    }

    public void onActivityDestroy() {
        if(observable!=null)
        observable.unsubscribeOn(Schedulers.io());
        if(myQuestionScreen!=null)
            myQuestionScreen = null;
    }
}


