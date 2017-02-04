package com.anupcowkur.mvpsample.ui.presenters;



import android.util.Log;
import android.util.Pair;

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
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class PostsPresenter {

    PostsAPI postsAPI;

    private static final int UNCHECKED_ERROR_TYPE_CODE = -100;

    @Inject
    public PostsPresenter(PostsAPI postsAPI) {
        this.postsAPI = postsAPI;
    }

    public void loadPostsFromAPI() {



 /*       postsAPI.getPostsObservable().retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
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
*/

        // Another way with Pair
        postsAPI.getPostsObservable().retryWhen(exponentialBackoffForExceptions(1,4,TimeUnit.SECONDS,IOException.class))
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



    @SafeVarargs
    public static Function<Observable<? extends Throwable>, Observable<?>>
    exponentialBackoffForExceptions(final long initialDelay, final int numRetries,
                                    final TimeUnit unit, final Class<? extends Throwable>... errorTypes) {

      if (initialDelay <= 0) {
            throw new IllegalArgumentException("initialDelay must be greater than 0");
        }

        if (numRetries <= 0) {
            throw new IllegalArgumentException("numRetries must be greater than 0");
        }

        return new Function<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> apply(Observable<? extends Throwable> errors) {
                return errors
                        .zipWith(Observable.range(1, numRetries + 1), new BiFunction<Throwable, Integer, Pair<Throwable, Integer>>() {
                            @Override
                            public Pair<Throwable, Integer> apply(Throwable error, Integer integer) throws Exception {

                                error.printStackTrace();
                                if (integer == numRetries + 1) {
                                    return new Pair<>(error, UNCHECKED_ERROR_TYPE_CODE);
                                }

                                if (errorTypes != null) {
                                    for (Class<? extends Throwable> clazz : errorTypes) {
                                        if (clazz.isInstance(error)) {
                                            // Mark as error type found
                                            return new Pair<>(error, integer);
                                        }
                                    }
                                }

                                return new Pair<>(error, UNCHECKED_ERROR_TYPE_CODE);
                            }
                        })
                        .flatMap(new io.reactivex.functions.Function<Pair<Throwable, Integer>, ObservableSource<? extends Long>>() {
                            @Override
                            public ObservableSource<? extends Long> apply(Pair<Throwable, Integer> errorRetryCountTuple) throws Exception {
                                int retryAttempt = errorRetryCountTuple.second;

                                Log.d("Retry Attempts"," "+retryAttempt);
                                // If not a known error type, pass the error through.
                                if (retryAttempt == UNCHECKED_ERROR_TYPE_CODE) {
                                    return Observable.error(errorRetryCountTuple.first);
                                }

                                //long delay = (long) Math.pow(initialDelay, retryAttempt);

                                // Else, exponential backoff for the passed in error types.
                                return Observable.timer(retryAttempt, unit);
                            }
                        });
            }
        };
    }

}
