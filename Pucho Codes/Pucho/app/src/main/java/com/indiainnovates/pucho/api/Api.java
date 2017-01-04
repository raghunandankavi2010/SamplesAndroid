package com.indiainnovates.pucho.api;

import com.indiainnovates.pucho.models.Answers;
import com.indiainnovates.pucho.models.FeedResponse;
import com.indiainnovates.pucho.models.LoginPost;
import com.indiainnovates.pucho.models.LoginResponse;
import com.indiainnovates.pucho.models.LoginViaServerPost;
import com.indiainnovates.pucho.models.MyQuestionsFetched;
import com.indiainnovates.pucho.models.PostAnswerContentModel;
import com.indiainnovates.pucho.models.PostAnswerResponse;
import com.indiainnovates.pucho.models.PostGcmToken;
import com.indiainnovates.pucho.models.PostQuestionContent;
import com.indiainnovates.pucho.models.QuestionContentModel;
import com.indiainnovates.pucho.models.SearchQueryResponse;
import com.indiainnovates.pucho.models.SendTokenResponse;
import com.indiainnovates.pucho.models.SignUpPost;
import com.indiainnovates.pucho.models.SignupResponse;
import com.indiainnovates.pucho.models.SuccessSignUpResponse;
import com.indiainnovates.pucho.utils.UrlStrings;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Raghunandan on 03-01-2016.
 */
public interface Api {

    //@Headers({"Content-Type: application/json","charset=utf-8"})
    @Headers("Content-Type: application/json")
    @GET("/questions")
    Observable<LoginResponse> getQuestions(@Query("page") int page, @Query("per_page") int per_page);

    @Headers("Content-Type: application/json")
    @POST(UrlStrings.GOOGLE_OAUTH)
    Observable<LoginResponse> post_userdetails(@Body LoginPost loginPost);

    @Headers("Content-Type: application/json")
    @POST(UrlStrings.SIGN_UP)
    Observable<List<SuccessSignUpResponse>> post_signUpdetails_server(@Body SignUpPost signuppost);

    @Headers("Content-Type: application/json")
    @POST(UrlStrings.LOGIN_POST)
    Observable<SignupResponse> post_loginDetails(@Body LoginViaServerPost loginPost);

    @Headers("Content-Type: application/json")
    @POST(UrlStrings.POST_GCM_TOKEN)
    Observable<SendTokenResponse> post_GCMToken(@Body PostGcmToken postGcmToken);

    //@Headers("Content-Type: application/json")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(UrlStrings.ASK_QUESTION)
    Observable<List<QuestionContentModel>> postQuestion_server(@Body PostQuestionContent postQuestionContent);


    @Headers("Content-Type: application/json")
    @POST("/pucho/questions/{questionid}/answers")
    Observable<List<PostAnswerResponse>> postAnswer_server(@Path("questionid") int guestionId, @Body PostAnswerContentModel postAnswerContentModel);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET(UrlStrings.FETCH_FEED)
    Observable<FeedResponse> fetch_Feed(@Query("page") int page, @Query("per_page") int perpage);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("/pucho/questions/{questionid}/answers")
    Observable<List<Answers>> fetch_Answers(@Path("questionid") int guestionId);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(UrlStrings.BASE_URL + UrlStrings.SEARCH)
    Observable<SearchQueryResponse> postSearchQUery(@Query("query") String queryparams, @Query("from") int from);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET(UrlStrings.BASE_URL + UrlStrings.FETCH_USER_POSTED_QUESTIONS)
    Observable<List<MyQuestionsFetched>> fetch_question_userId(@Query("user_id") String queryparams);

    @DELETE("/pucho/questions/{questionid}")
    Call<ResponseBody> delete_question(@Path("questionid") int guestionId);
}
