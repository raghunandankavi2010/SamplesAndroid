package com.indiainnovates.pucho.presenters;

import com.indiainnovates.pucho.PostQuestion;
import com.indiainnovates.pucho.api.AskQuestionApi;
import com.indiainnovates.pucho.events.AskQuestionErrorEvent;
import com.indiainnovates.pucho.events.ErrorEvent;
import com.indiainnovates.pucho.events.PostQuestionResponeEvent;
import com.indiainnovates.pucho.listeners.OnCheckPostQuestionContentEmptyListener;
import com.indiainnovates.pucho.models.QuestionContentModel;
import com.indiainnovates.pucho.screen_contracts.PostQuestionScreen;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Raghunandan on 29-01-2016.
 */
public class AskQuestionPresenter implements OnCheckPostQuestionContentEmptyListener {

    AskQuestionApi askQuestionApi;

    private String questionContent;

    private PostQuestionScreen postQuestionScreen;
    private int userId;

    //private CompositeSubscription compositeSubscription = new CompositeSubscription();

    private Observable<List<QuestionContentModel>> observableQuestionContent;

    @Inject
    public AskQuestionPresenter(AskQuestionApi askQuestionApi)
    {
        this.askQuestionApi = askQuestionApi;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setPresenterScreen(PostQuestionScreen postQuestionScreen) {
        this.postQuestionScreen = postQuestionScreen;
    }

    public void onDestroyActivity()
    {
        if(observableQuestionContent!=null)
        observableQuestionContent.unsubscribeOn(Schedulers.io());
        this.postQuestionScreen = null;

    }

    @Override
    public void onContentEmpty() {

        EventBus.getDefault().post("Empty");
        //postQuestionScreen.onQuestionContentEmpty();
    }

    @Override
    public void onSuccess() {

        EventBus.getDefault().post("Success");
        //postQuestionScreen.onSuccess();
    }

    public void sendContentToServer() {


        observableQuestionContent = askQuestionApi
                .sendQuestionContentToServer(getQuestionContent(),getUserId());

        observableQuestionContent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<QuestionContentModel>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        EventBus.getDefault().post(new AskQuestionErrorEvent(e));
                    }

                    @Override
                    public void onNext(List<QuestionContentModel> questionContentModels) {

                        EventBus.getDefault().post(new PostQuestionResponeEvent(questionContentModels));
                    }
                });

    }

    public void postButtonClicked() {
        postQuestionScreen.onPostButtonClicked();
    }

    public void validateContentEmpty() {

       askQuestionApi.validateifContentEmpty(getQuestionContent(),this);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
