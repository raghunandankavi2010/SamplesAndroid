package com.example.raghu.retrofitsample;

/**
 * Created by raghu on 23/4/17.
 */

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient
{
    public static final String URL="http://api.themoviedb.org/3/";

    public static final String URL2 = "https://us.openfoodfacts.org/";

    public static Retrofit getClient()
    {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {

                Request originalRequest = chain.request(); //Current Request


             /*   originalRequest.newBuilder().addHeader()
                Request newRequest = originalRequest.newBuilder()
                        .addHeader(HeadersContract.HEADER_AUTHONRIZATION, O_AUTH_AUTHENTICATION)
                        .addHeader(HeadersContract.HEADER_X_CLIENT_ID, CLIENT_ID)
                        .build();
                originalRequest.header()*/
                Response response = chain.proceed(originalRequest); //Get response of the request

                //I am logging the response body in debug mode. When I do this I consume the response (OKHttp only lets you do this once) so i have re-build a new one using the cached body
                String bodyString = response.body().string();

                Log.i("NetworkModule", String.format("Sending request %s with headers %s ", originalRequest.url(), originalRequest.headers()));
                Log.i("", (String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), bodyString, response.headers())));

                response = response.newBuilder().body(
                        ResponseBody.create(response.body().contentType(), bodyString))
                        .build();

                return response;
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(URL2)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit;
    }

}