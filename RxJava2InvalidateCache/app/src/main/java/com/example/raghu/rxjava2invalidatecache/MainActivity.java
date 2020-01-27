package com.example.raghu.rxjava2invalidatecache;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) this.findViewById(R.id.textView);
        //test();
        //testFlowable();
        //testObservable();
        testMaybe();
    }

    public void test() {
        Single<Integer> o = Single.just(1)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        textView.append("Completed \n");
                        Log.i(TAG, "completed");
                    }

                });
        SingleCache<Integer> cacher =
                new SingleCache<Integer>(o);
        Single<Integer> o2 = Single.unsafeCreate(cacher);
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
        cacher.reset();
        textView.append("Cache Reset" +"\n");
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
    }

    public void testFlowable() {
        Flowable<Integer> o = Flowable.just(1);

        FlowableCache<Integer> cacher =
                new FlowableCache<Integer>(o);
        Flowable<Integer> o2 = Flowable.unsafeCreate(cacher);
        o2.subscribe(subscriber);
        o2.subscribe(subscriber);
        cacher.reset();
        textView.append("Cache Reset" +"\n");
        o2.subscribe(subscriber);
    }

    private Subscriber<Integer> subscriber = new Subscriber<Integer>()
    {

        @Override
        public void onSubscribe(Subscription s) {
            s.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(Integer integer) {
            textView.append(""+integer+" \n");
            Log.i(TAG, "" + integer);
        }

        @Override
        public void onError(Throwable t) {
               t.printStackTrace();
        }

        @Override
        public void onComplete() {
            textView.append("Completed \n");
            Log.i(TAG, "completed");
        }
    };

    public void testObservable() {
        Observable<Integer> o = Observable.just(1)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        textView.append("Completed \n");
                        Log.i(TAG, "completed");
                    }

                });
        ObservableCache<Integer> cacher =
                new ObservableCache<Integer>(o);
        Observable<Integer> o2 = Observable.unsafeCreate(cacher);
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
        cacher.reset();
        textView.append("Cache Reset" +"\n");
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
    }

    public void testMaybe() {
        Maybe<Integer> o = Maybe.just(1)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        textView.append("Completed \n");
                        Log.i(TAG, "completed");
                    }

                });
        MaybeCache<Integer> cacher =
                new MaybeCache<Integer>(o);
        Maybe<Integer> o2 = Maybe.unsafeCreate(cacher);
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
        cacher.reset();
        textView.append("Cache Reset" +"\n");
        o2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                textView.append(""+integer+" \n");
                Log.i(TAG, "" + integer);
            }
        });
    }
}

