package com.indiainnovates.pucho.screen_contracts;

import com.indiainnovates.pucho.models.Answers;

import java.util.List;

/**
 * Created by Raghunandan on 20-03-2016.
 */
public interface MyQuestionsAnswerScreen {

    void hideRecyclerView();
    void onDisaplyRecylerView();

    void onProgresBarDisplay();
    void hideProgressBar();
    void onShareButtonClicked();

    void onError(Throwable e);
    void onFetchedAnswer(List<Answers> answers);
}
