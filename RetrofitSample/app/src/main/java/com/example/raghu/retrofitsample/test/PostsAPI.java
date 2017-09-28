package com.example.raghu.retrofitsample.test;

import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;


public class PostsAPI {



    OkHttpClient client;



    public PostsAPI()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.writeTimeout(10, TimeUnit.SECONDS); // connect timeout
        builder.readTimeout(10, TimeUnit.SECONDS);


        builder.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request originalRequest = chain.request(); //Current Request


                okhttp3.Response response = chain.proceed(originalRequest); //Get response of the request


                //I am logging the response body in debug mode. When I do this I consume the response (OKHttp only lets you do this once) so i have re-build a new one using the cached body
                String bodyString = response.body().string();
                //Log.i("Response Body", bodyString);

                //Log.i("NetworkModule", String.format("Sending request %s with headers %s ", originalRequest.url(), originalRequest.headers()));
                Log.i("Response", (String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), bodyString, response.headers())));
                response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();


                return response;
            }
        });
        client = builder.build();
    }

    public interface PostService {


        @GET("repos/Raghunandan/retrofit/contributors")
        Observable<Response<List<Contributor>>> getPosts();

    }



    private Observable<Response<List<Contributor>>> postsObservable ;

    public Observable<Response<List<Contributor>>> getPost()
    {
        postsObservable = getApi().getPosts();
        postsObservable.cache();
        return postsObservable;
    }



    PostService getApi() {

        //
       // https://api.github.com/repos/square/retrofit/contributors

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        return retrofit.create(PostService.class);

    }


}
