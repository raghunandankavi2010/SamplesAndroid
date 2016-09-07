package com.india.innovates.pucho.presenters;

import com.india.innovates.pucho.api.ComposeAnswerApi;
import com.india.innovates.pucho.events.ComposeAnswerErrorEvent;
import com.india.innovates.pucho.events.PostAnswerResponseEvent;
import com.india.innovates.pucho.listeners.OnCheckPostAnswerContent;
import com.india.innovates.pucho.models.PostAnswerResponse;
import com.india.innovates.pucho.screen_contracts.ComposeAnswerScreen;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Raghunandan on 29-01-2016.
 */
public class ComposeAnswerPresenter implements OnCheckPostAnswerContent {

    private ComposeAnswerApi composeAnswerApi;
    private ComposeAnswerScreen composeAnswerScreen;
    //private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private String postAnswerContent;
    private int userId;
    private Observable<List<PostAnswerResponse>> listAnswerObservable;
    private int questionId;

    @Inject
    public ComposeAnswerPresenter(ComposeAnswerApi composeAnswerApi) {
        this.composeAnswerApi = composeAnswerApi;
    }

    public void setPresenterScreen(ComposeAnswerScreen composeAnswerScreen) {
        this.composeAnswerScreen = composeAnswerScreen;
    }

    public void onDestroyActivity() {


        if(listAnswerObservable!=null)
            listAnswerObservable.unsubscribeOn(Schedulers.io());
       // this.composeAnswerScreen = null;
    }

    public void onPostButtonClicked() {
        composeAnswerScreen.onPostAnswerClicked();
    }

    public void setPostAnswerContent(String postAnswerContent) {
        this.postAnswerContent = postAnswerContent;
    }

    public String getPostAnswerContent() {
        return postAnswerContent;
    }

    public void validatePostAnswerContent() {

        composeAnswerApi.validatePostAnswerContent(getPostAnswerContent(), this);
    }

    @Override
    public void onSuccess() {

        EventBus.getDefault().post("Success");
    }

    @Override
    public void onEmptyContent() {

        EventBus.getDefault().post("Empty");
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }


    public void sendContentToServer() {

        listAnswerObservable = composeAnswerApi.postAnswerServer(getUserId(), getQuestionId(),getPostAnswerContent());

       listAnswerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PostAnswerResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        EventBus.getDefault().post(new ComposeAnswerErrorEvent(e));
                    }

                    @Override
                    public void onNext(List<PostAnswerResponse> postAnswerResponses) {

                        EventBus.getDefault().post(new PostAnswerResponseEvent(postAnswerResponses));
                    }
                });
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }
}
