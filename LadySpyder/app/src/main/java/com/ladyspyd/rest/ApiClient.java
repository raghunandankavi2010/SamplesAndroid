package com.ladyspyd.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://peakaeriest.com/ladyspyderapi/v1/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;
    private static Retrofit retrofit2 = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
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
            })
                    .connectTimeout(30, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.MINUTES)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient3() {
        if (retrofit1 == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
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
            })
                    .connectTimeout(30, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.MINUTES)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit1 = new Retrofit.Builder()
                    .baseUrl("https://ladyapi-fc58c.firebaseio.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit1;
    }

    public static Retrofit getClient2()
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

        retrofit2 = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://ladyapi-fc58c.firebaseio.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit2;
    }

}