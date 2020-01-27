package com.anupcowkur.mvpsample.model;

import android.util.Log;

import com.anupcowkur.mvpsample.model.pojo.Post;


import java.util.List;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class PostsAPI {

    private interface PostService {
        @GET("posts")
        Observable<List<Post>> getPostsList();
    }

    private Observable<List<Post>> postsObservable = new Retrofit.Builder()
            .baseUrl("http://jsonplaceholder.typicode.com/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PostService.class).getPostsList();


    public Observable<List<Post>> getPostsObservable() {
        Log.d("PostsAPI", "GetObservable") ;
        return postsObservable;
    }

}
