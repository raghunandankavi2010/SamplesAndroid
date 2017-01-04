package com.indiainnovates.pucho.dagger.components;

import android.app.Application;
import android.content.SharedPreferences;

import com.indiainnovates.pucho.AnswersActivity;

import com.indiainnovates.pucho.ComposeAnswer;

import com.indiainnovates.pucho.MyQuestionComposeAnswer;
import com.indiainnovates.pucho.MyQuestions;
import com.indiainnovates.pucho.MyQuestionsDetailAnswerActivity;
import com.indiainnovates.pucho.PostQuestion;
import com.indiainnovates.pucho.QuestionFeed;
import com.indiainnovates.pucho.SearchActivity;
import com.indiainnovates.pucho.SearchQuestionDetailActivity;
import com.indiainnovates.pucho.SignUpActivity;
import com.indiainnovates.pucho.adapters.CustomCursorAdapter;
import com.indiainnovates.pucho.adapters.FeedAdapter;
import com.indiainnovates.pucho.adapters.MyQuestionsAdapter;
import com.indiainnovates.pucho.adapters.SearchAdapter;
import com.indiainnovates.pucho.dagger.modules.ApplicationModule;
import com.indiainnovates.pucho.LoginActivity;
import com.indiainnovates.pucho.fragments.FavoriteFragment;
import com.indiainnovates.pucho.fragments.FeedFragment;
import com.indiainnovates.pucho.fragments.TrendingFragment;

import com.indiainnovates.pucho.presenters.AnswersPresenter;
import com.indiainnovates.pucho.presenters.ComposeAnswerPresenter;
import com.indiainnovates.pucho.presenters.LoginPresenter;
import com.indiainnovates.pucho.presenters.MyQuestionDetailAnswerPresenter;
import com.indiainnovates.pucho.presenters.SignUpPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Raghunandan on 14-12-2015.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity loginActivity);
    void inject(SignUpActivity singupActivity);
    void inject(LoginPresenter loginPresenter);
    void inject(SignUpPresenter signUpPresenter);
    void inject(QuestionFeed questionFeed);
    void inject(FeedFragment feedFragment);
    void inject(FavoriteFragment favoriteFragment);
    void inject(TrendingFragment trendingFragment);
    //void inject(RegistrationIntentService registrationIntentService);
    void inject(PostQuestion postQuestion);
    void inject(ComposeAnswer composeAnswer);
    void inject(ComposeAnswerPresenter composeAnswerPresenter);
    void inject(AnswersActivity answersActivity);
    void inject(AnswersPresenter answersPresenter);
    void inject(FeedAdapter feedAdapter);
    void inject(SearchAdapter searchAdapter);
    void inject(MyQuestionsAdapter myQuestionsAdapter);
    void inject(CustomCursorAdapter customCursorAdapter);
    void inject(SearchActivity searchActivity);
    void inject(SearchQuestionDetailActivity searchQuestionDetailActivity);
    void inject(MyQuestions myQuestions);
    void inject(MyQuestionsDetailAnswerActivity myQuestionsDetailAnswerActivity);
    void inject(MyQuestionComposeAnswer myQuestionComposeAnswer);
    void inject(MyQuestionDetailAnswerPresenter myQuestionDetailAnswerPresenter);


    Application provideApplication();
    SharedPreferences provideSharedPreferences();

}