package com.indiainnovates.pucho.dagger.components;

import android.app.Application;
import android.content.SharedPreferences;

import com.indiainnovates.pucho.AnswersActivity;
import com.indiainnovates.pucho.AnswersDetailedActivity;
import com.indiainnovates.pucho.ComposeAnswer;

import com.indiainnovates.pucho.PostQuestion;
import com.indiainnovates.pucho.QuestionFeed;
import com.indiainnovates.pucho.RegistrationIntentService;
import com.indiainnovates.pucho.SignUpActivity;
import com.indiainnovates.pucho.dagger.modules.ApplicationModule;
import com.indiainnovates.pucho.LoginActivity;
import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.fragments.FavoriteFragment;
import com.indiainnovates.pucho.fragments.FeedFragment;
import com.indiainnovates.pucho.fragments.TrendingFragment;

import com.indiainnovates.pucho.presenters.AnswersPresenter;
import com.indiainnovates.pucho.presenters.ComposeAnswerPresenter;
import com.indiainnovates.pucho.presenters.LoginPresenter;
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
    void inject(AnswersDetailedActivity answersDetailedActivity);
    void inject(AnswersActivity answersActivity);
    void inject(AnswersPresenter answersPresenter);

    Application provideApplication();
    SharedPreferences provideSharedPreferences();

}