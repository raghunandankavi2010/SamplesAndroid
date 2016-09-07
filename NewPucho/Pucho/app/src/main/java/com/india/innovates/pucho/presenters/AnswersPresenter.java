package com.india.innovates.pucho.presenters;

import com.india.innovates.pucho.api.AnswerFetchApi;
import com.india.innovates.pucho.events.AnswerErrorEvent;
import com.india.innovates.pucho.events.AnswersEvent;

import com.india.innovates.pucho.listeners.OnSuccessfulDeleteListener;
import com.india.innovates.pucho.models.Answers;
import com.india.innovates.pucho.models.FollowModel;
import com.india.innovates.pucho.screen_contracts.AnswerScreen;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;



import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Raghunandan on 01-02-2016.
 */
public class AnswersPresenter implements OnSuccessfulDeleteListener {

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

        listObservable.subscribeOn(Schedulers.io())
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
                });
    }

    public void onDestroyActivity() {

        if (listObservable != null)
            listObservable.unsubscribeOn(Schedulers.io());
        if (answerScreen != null)
            answerScreen = null;


    }

    public void hideRecyclerView() {
        if (answerScreen != null)
            answerScreen.hideRecyclerView();
    }

    public void displayProgressBar() {
        if (answerScreen != null)
            answerScreen.hideProgressBar();
    }

    public void hideProgressBar() {
        if (answerScreen != null)
            answerScreen.hideProgressBar();
    }

    public void displayRecyclerView() {
        if (answerScreen != null)
            answerScreen.onDisaplyRecylerView();
    }

    public void setShareButtonClicked() {
        if (answerScreen != null)
            answerScreen.onShareButtonClicked();
    }

    public void deleteQuestion(int question_id) {
        answerFetchApi.delete_question(question_id, this);
    }

    @Override
    public void onSuccess() {

        if (answerScreen != null)
            answerScreen.onDelete_Success();
    }

    @Override
    public void onFailure() {
        if (answerScreen != null)
            answerScreen.onDelete_Failure();
    }

    public void followQuestion(int user_id, int question_id) {
        Observable<FollowModel> likeModelObservable = answerFetchApi.follow_question(user_id,question_id);
        compositeSubscription.add(likeModelObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FollowModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        if(answerScreen!=null)
                        answerScreen.onErrorFollowResponse(e);
                    }

                    @Override
                    public void onNext(FollowModel followModel) {

                        if(answerScreen!=null)
                        answerScreen.onSuccess(followModel);
                    }
                }));
    }

    @Override
    public void onError(Throwable e) {
        if(answerScreen!=null)
            answerScreen.onError(e);
    }


}
