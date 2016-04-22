package com.indiainnovates.pucho.presenters;

import com.indiainnovates.pucho.api.AnswerFetchApi;
import com.indiainnovates.pucho.events.AnswerErrorEvent;
import com.indiainnovates.pucho.events.AnswersEvent;
import com.indiainnovates.pucho.events.ErrorEvent;
import com.indiainnovates.pucho.models.Answers;
import com.indiainnovates.pucho.screen_contracts.AnswerScreen;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Raghunandan on 01-02-2016.
 */
public class AnswersPresenter {

    private int questionId;
    private AnswerFetchApi answerFetchApi;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Observable<List<Answers>> listObservable;
    private AnswerScreen answerScreen;

    public void setAnswerScreen(AnswerScreen answerScreen) {
        this.answerScreen = answerScreen;
    }


    @Inject
    public AnswersPresenter(AnswerFetchApi answerFetchApi) {
        this.answerFetchApi = answerFetchApi;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;

    }

    public int getQuestionId() {
        return questionId;
    }

    public void fetchAnswer() {
        listObservable = answerFetchApi.fetchAnswer(getQuestionId());

        compositeSubscription.add(listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Answers>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new AnswerErrorEvent(e));
                    }

                    @Override
                    public void onNext(List<Answers> answers) {

                        EventBus.getDefault().post(new AnswersEvent(answers));
                    }
                }));
    }

    public void onDestroyActivity() {

        if (listObservable != null)
            //compositeSubscription.unsubscribe();
           listObservable.unsubscribeOn(Schedulers.io());

        answerScreen = null;


    }

    public void hideRecyclerView() {
        answerScreen.hideRecyclerView();
    }

    public void displayProgressBar() {
        answerScreen.hideProgressBar();
    }

    public void hideProgressBar() {
        answerScreen.hideProgressBar();
    }

    public void displayRecyclerView() {
        answerScreen.onDisaplyRecylerView();
    }
}
