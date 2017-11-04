package com.ladyspyd.rest;

import com.ladyspyd.models.FeedBackRequest;
import com.ladyspyd.models.ForogotRequestModel;
import com.ladyspyd.models.GetMainCategoryResponse;
import com.ladyspyd.models.LSBannerModel;
import com.ladyspyd.models.LSFAQModel;
import com.ladyspyd.models.LSFeedBackResponseModel;
import com.ladyspyd.models.LSForgotpasswordResponseModel;
import com.ladyspyd.models.LoginRequestModel;
import com.ladyspyd.models.LoginResponseModel;
import com.ladyspyd.models.RegistrationRequest;
import com.ladyspyd.models.RegistrationResponseModel;
import com.ladyspyd.models.RequestAddContactsUs;
import com.ladyspyd.models.UpdatePasswordRequestModel;
import com.ladyspyd.models.UpdatePasswordResponseModel;
import com.ladyspyd.models.UpdateRequestModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by MyMac on 13/09/17.
 */

public interface APIInterface {

    /*Registration*/
    @POST("users/register")
    Call<RegistrationResponseModel> registerUser(@HeaderMap Map<String, String> header, @Body RegistrationRequest registrationRequest);

    /*Login*/
    @POST("users/login")
    Call<LoginResponseModel> loginUser(@HeaderMap Map<String, String> header, @Body LoginRequestModel loginRequest);

    /*----------------------------------------------------------------------------------------------------------------------------------------*/

    /*Update password*/
    @POST("users/updatepassword")
    Call<UpdatePasswordResponseModel> updatePassword(@HeaderMap Map<String, String> header, @Body UpdatePasswordRequestModel updatePasswordRequestModel);


    @GET("users/getcategories")
    Call<GetMainCategoryResponse> getMainCategories(@HeaderMap Map<String, String> header);

    //https://ladyapi-fc58c.firebaseio.com/content/-KwoAcoIepX6PpGimkNv.json
    @Headers({ "Content-Type: application/json"})
    @GET ("content/-Kwt1nWeh5yJrIUDIxZx.json")
    Call<GetMainCategoryResponse> getMainCategories2();

    //https://ladyapi-fc58c.firebaseio.com/content/-Kwt0jhTyCoA_tBrth_H.json
    @Headers({ "Content-Type: application/json"})
    @GET("content/-Kwt0jhTyCoA_tBrth_H.json")//content/-Kwt0jhTyCoA_tBrth_H.json
    Call<LSBannerModel> getBanners();


    @GET("users/getfaq")
    Call<LSFAQModel> getfaq(@HeaderMap Map<String, String> header);

    @POST("users/addfeedback")
    Call<LSFeedBackResponseModel> registerUser(@HeaderMap Map<String, String> header, @Body FeedBackRequest feedBackRequestRequest);

    @POST("users/addcontactus")
    Call<LSFeedBackResponseModel> addcontactus(@HeaderMap Map<String, String> header, @Body RequestAddContactsUs feedBackRequestRequest);

    @POST("users/forgotpassword")
    Call<LSForgotpasswordResponseModel> forgotpassword(@HeaderMap Map<String, String> header, @Body ForogotRequestModel feedBackRequestRequest);

    @POST("users/updatepassword")
    Call<LSForgotpasswordResponseModel> updatepassword(@HeaderMap Map<String, String> header, @Body UpdateRequestModel feedBackRequestRequest);

}
