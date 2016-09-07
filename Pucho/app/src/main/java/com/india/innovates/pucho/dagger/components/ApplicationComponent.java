package com.india.innovates.pucho.dagger.components;

import android.app.Application;
import android.content.SharedPreferences;

import com.india.innovates.pucho.AnswersActivity;
import com.india.innovates.pucho.ComposeAnswer;
import com.india.innovates.pucho.EditActivity;
import com.india.innovates.pucho.EditAnswerActivity;
import com.india.innovates.pucho.LoginActivity;
import com.india.innovates.pucho.MyQuestionComposeAnswer;
import com.india.innovates.pucho.MyQuestions;
import com.india.innovates.pucho.MyQuestionsDetailAnswerActivity;
import com.india.innovates.pucho.PostQuestion;
import com.india.innovates.pucho.QuestionFeed;
import com.india.innovates.pucho.SearchActivity;
import com.india.innovates.pucho.SearchQuestionDetailActivity;
import com.india.innovates.pucho.SignUpActivity;
import com.india.innovates.pucho.adapters.CustomCursorAdapter;
import com.india.innovates.pucho.adapters.FeedAdapter;
import com.india.innovates.pucho.adapters.MyQuestionsAdapter;
import com.india.innovates.pucho.adapters.SearchAdapter;
import com.india.innovates.pucho.dagger.modules.ApplicationModule;
import com.india.innovates.pucho.fragments.FavoriteFragment;
import com.india.innovates.pucho.fragments.FeedFragment;
import com.india.innovates.pucho.presenters.AnswersPresenter;
import com.india.innovates.pucho.presenters.ComposeAnswerPresenter;
import com.india.innovates.pucho.presenters.EditQuestionPresenter;
import com.india.innovates.pucho.presenters.LoginPresenter;
import com.india.innovates.pucho.presenters.MyQuestionDetailAnswerPresenter;
import com.india.innovates.pucho.presenters.SignUpPresenter;

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
    //void inject(TrendingFragment trendingFragment);
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
    void inject(EditQuestionPresenter editQuestionPresenter);
    void inject(EditActivity editActivity);
    void inject(EditAnswerActivity editAnswerActivity);


    Application provideApplication();
    SharedPreferences provideSharedPreferences();

}