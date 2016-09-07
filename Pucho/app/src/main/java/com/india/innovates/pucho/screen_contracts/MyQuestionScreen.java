package com.india.innovates.pucho.screen_contracts;

import com.india.innovates.pucho.models.MyQuestionsFetched;

import java.util.List;

/**
 * Created by Raghunandan on 18-03-2016.
 */
public interface MyQuestionScreen{

        void displayProgressBar();

        void dismissProgressBar();

        void displayRecyclerView();

        void hideRecycelrView();

        void fetchedQuestions(List<MyQuestionsFetched> myQuestions);

        void onError(Throwable e);


}
