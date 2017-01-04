package com.indiainnovates.pucho.dagger.components;

import android.app.Application;

import com.indiainnovates.pucho.RegistrationIntentService;
import com.indiainnovates.pucho.api.AnswerFetchApi;
import com.indiainnovates.pucho.api.AskQuestionApi;
import com.indiainnovates.pucho.api.ComposeAnswerApi;
import com.indiainnovates.pucho.api.FeedApi;
import com.indiainnovates.pucho.api.LoginApi;
import com.indiainnovates.pucho.api.MyQuestionsApi;
import com.indiainnovates.pucho.api.PostSearchQueryApi;
import com.indiainnovates.pucho.api.QuestionsApi;
import com.indiainnovates.pucho.api.SignUpApi;
import com.indiainnovates.pucho.dagger.UserScope;
import com.indiainnovates.pucho.dagger.modules.ApplicationModule;
import com.indiainnovates.pucho.dagger.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Raghunandan on 16-12-2015.
 */
@UserScope
@Component(dependencies = ApplicationComponent.class,modules =NetworkModule.class)
public interface NetworkComponent  {
    void inject(LoginApi loginApi);
    void inject(SignUpApi signUpApi);
    void inject(QuestionsApi questionsApi);
    void inject(RegistrationIntentService registrationIntentService);
    void inject(AskQuestionApi askQuestionApi);
    void inject(ComposeAnswerApi composeAnswerApi);
    void inject(FeedApi feedApi);
    void inject(AnswerFetchApi answerFetchApi);
    void inject(PostSearchQueryApi postSearchQueryApi);
    void inject(MyQuestionsApi myQuestionsApi);


}
