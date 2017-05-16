package com.example.raghu.rxjava2invalidatecache;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.annotations.NonNull;

/**
 * Created by raghu on 17/5/17.
 */

public class MaybeCache<T> implements MaybeSource {

    private final AtomicBoolean refresh = new AtomicBoolean(true);
    private final Maybe source;
    private volatile Maybe<T> current;



    public MaybeCache(Maybe<T> source) {
        this.source = source;
        this.current = source;
    }

    public void reset() {
        refresh.set(true);
    }

    @Override
    public void subscribe(@NonNull MaybeObserver observer) {

        if (refresh.compareAndSet(true, false)) {
            current = source.cache();
            Log.d("Cache", "source");
        }
        current.subscribe(observer);
    }
}
