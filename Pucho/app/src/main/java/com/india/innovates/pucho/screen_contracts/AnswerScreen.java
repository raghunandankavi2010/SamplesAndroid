package com.india.innovates.pucho.screen_contracts;

import com.india.innovates.pucho.models.Answers;
import com.india.innovates.pucho.models.FollowModel;

import java.util.List;

/**
 * Created by Raghunandan on 01-02-2016.
 */
public interface AnswerScreen {

    void hideRecyclerView();
    void onDisaplyRecylerView();

    void onProgresBarDisplay();
    void hideProgressBar();
    void onShareButtonClicked();

    void onError(Throwable e);
    void onFetchedAnswer(List<Answers> answers);

    void onDelete_Success();
    void onDelete_Failure();

    void onErrorFollowResponse(Throwable t);
    void onSuccess(FollowModel likeModel);


}
