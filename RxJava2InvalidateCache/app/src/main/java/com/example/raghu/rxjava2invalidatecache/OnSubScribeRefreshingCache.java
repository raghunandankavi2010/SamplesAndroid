package com.example.raghu.rxjava2invalidatecache;


import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;

/**
 * Created by raghu on 13/5/17.
 */

public class OnSubScribeRefreshingCache<T> implements SingleSource<T> {

    private final AtomicBoolean refresh = new AtomicBoolean(true);
    private final Single<T> source;
    private volatile Single<T> current;



    public OnSubScribeRefreshingCache(Single<T> source) {
        this.source = source;
        this.current = source;
    }

    public void reset() {
        refresh.set(true);
    }



    @Override
    public void subscribe(@NonNull SingleObserver<? super T> observer) {
        if (refresh.compareAndSet(true, false)) {
            current = source.cache();
            Log.d("Cache", "source");
        }
            current.subscribe(observer);
    }

}

