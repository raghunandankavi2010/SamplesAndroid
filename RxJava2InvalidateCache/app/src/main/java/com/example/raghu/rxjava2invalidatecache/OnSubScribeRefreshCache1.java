package com.example.raghu.rxjava2invalidatecache;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

/**
 * Created by raghu on 14/5/17.
 */

public class OnSubScribeRefreshCache1<T> implements ObservableSource<T> {

    private final AtomicBoolean refresh = new AtomicBoolean(true);
    private final Observable<T> source;
    private volatile Observable<T> current;



    public OnSubScribeRefreshCache1(Observable<T> source) {
        this.source = source;
        this.current = source;
    }

    public void reset() {
        refresh.set(true);
    }


    @Override
    public void subscribe(@NonNull Observer<? super T> observer) {
        if (refresh.compareAndSet(true, false)) {
            current = source.cache();
            Log.d("Cache", "source");
        }
        current.subscribe(observer);
    }
}

