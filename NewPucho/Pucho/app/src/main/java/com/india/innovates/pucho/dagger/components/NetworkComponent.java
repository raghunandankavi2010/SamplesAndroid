package com.india.innovates.pucho.dagger.components;


import com.india.innovates.pucho.api.AnswerFetchApi;
import com.india.innovates.pucho.api.AskQuestionApi;
import com.india.innovates.pucho.api.ComposeAnswerApi;
import com.india.innovates.pucho.api.EditAnswerApi;
import com.india.innovates.pucho.api.EditQuestionApi;
import com.india.innovates.pucho.api.FeedApi;
import com.india.innovates.pucho.api.LoginApi;
import com.india.innovates.pucho.api.MyQuestionsApi;
import com.india.innovates.pucho.api.PostSearchQueryApi;
import com.india.innovates.pucho.api.QuestionsApi;
import com.india.innovates.pucho.api.SignUpApi;
import com.india.innovates.pucho.dagger.UserScope;
import com.india.innovates.pucho.dagger.modules.NetworkModule;
import com.india.innovates.pucho.fcm.SendTokenToServer;

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

    void inject(AskQuestionApi askQuestionApi);
    void inject(ComposeAnswerApi composeAnswerApi);
    void inject(FeedApi feedApi);
    void inject(AnswerFetchApi answerFetchApi);
    void inject(PostSearchQueryApi postSearchQueryApi);
    void inject(MyQuestionsApi myQuestionsApi);
    void inject(EditQuestionApi editQuestionApi);
    void inject(EditAnswerApi editAnswerApi);
    void inject(SendTokenToServer sendTokenToServer);

}
