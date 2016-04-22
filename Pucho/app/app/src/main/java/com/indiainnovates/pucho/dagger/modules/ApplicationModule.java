package com.indiainnovates.pucho.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.indiainnovates.pucho.api.AskQuestionApi;
import com.indiainnovates.pucho.api.ComposeAnswerApi;
import com.indiainnovates.pucho.api.FeedApi;
import com.indiainnovates.pucho.api.LoginApi;
import com.indiainnovates.pucho.api.QuestionsApi;
import com.indiainnovates.pucho.api.SignUpApi;
import com.indiainnovates.pucho.api.AnswerFetchApi;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created by Raghunandan on 14-12-2015.
 */
@Module
public class ApplicationModule {

    private Application app;
    private String PREF_NAME = "prefs";



    public ApplicationModule(Application app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public Picasso getPicasso() {
        return new Picasso.Builder(app).build();
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return app;
    }

    @Singleton
    @Provides
    public SharedPreferences getAppPreferences() {
        return app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public LoginApi provideLoginApi() {
        return new LoginApi();
    }

    @Singleton
    @Provides
    public SignUpApi provideSignUpApi() {
        return new SignUpApi();
    }


    @Singleton
    @Provides
    public QuestionsApi provideQuestionApi() {
        return new QuestionsApi();
    }

    @Singleton
    @Provides
    public AskQuestionApi provideAskQuestionApi() {
        return new AskQuestionApi();
    }


    @Singleton
    @Provides
    public ComposeAnswerApi provideComposeAnswerApi() {
        return new ComposeAnswerApi();
    }


    @Singleton
    @Provides
    public FeedApi provideFeedApi() {
        return new FeedApi();
    }

    @Singleton
    @Provides
    public AnswerFetchApi provideAnswerFetchApi() {
        return new AnswerFetchApi();
    }
}