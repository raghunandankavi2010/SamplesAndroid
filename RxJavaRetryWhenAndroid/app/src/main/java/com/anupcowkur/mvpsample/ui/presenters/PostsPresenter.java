package com.anupcowkur.mvpsample.ui.presenters;



import android.util.Log;

import com.anupcowkur.mvpsample.events.ErrorEvent;
import com.anupcowkur.mvpsample.events.NewPostsEvent;
import com.anupcowkur.mvpsample.model.PostsAPI;
import com.anupcowkur.mvpsample.model.pojo.Post;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class PostsPresenter {
    PostsAPI postsAPI;

    @Inject
    public PostsPresenter(PostsAPI postsAPI) {
        this.postsAPI = postsAPI;
    }

    public void loadPostsFromAPI() {

        postsAPI.getPostsObservable().retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> apply(Observable<? extends Throwable> errorNotification) {
                return errorNotification
                        .zipWith(Observable.range(1, 6), new BiFunction<Throwable, Integer, Integer>() {
                            @Override
                            public Integer apply(Throwable throwable, Integer attempts) {
                                throwable.printStackTrace();
                                if(throwable instanceof IOException)
                                return attempts;
                                else
                                    return 0;
                            }
                        })
                        .flatMap(new Function<Integer, Observable<?>>() {
                            @Override
                            public Observable<?> apply(Integer attempts) {
                                if(attempts <6) {
                                    Log.d("PostsPresenter", "Retrying" + attempts);

                                    return Observable.timer(attempts, TimeUnit.SECONDS);
                                }
                                else {
                                    Log.d("PostsPresenter","Retrying Done");
                                    return Observable.empty();
                                }
                            }
                        });
            }
        })
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Post>>() {
                    @Override
                    public void onNext(List<Post> newPosts) {
                        EventBus.getDefault().post(new NewPostsEvent(newPosts));
                    }



                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent());
                    }

                    @Override
                    public void onComplete() {

                        Log.d("PostsPresenter","Completed");
                    }

                });
    }

}
