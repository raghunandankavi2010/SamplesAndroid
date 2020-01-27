package com.example.raghu.rxjava2invalidatecache;

/**
 * Created by raghu on 17/5/17.
 */

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.rxjava3.core.Flowable;


/**
 * Created by raghu on 14/5/17.
 */

public class FlowableCache<T> implements Publisher<T> {

    private final AtomicBoolean refresh = new AtomicBoolean(true);
    private final Flowable<T> source;
    private volatile Flowable<T> current;



    public FlowableCache(Flowable<T> source) {
        this.source = source;
        this.current = source;
    }

    public void reset() {
        refresh.set(true);
    }


    @Override
    public void subscribe(Subscriber<? super T> s) {
        current.subscribe(s);
    }
}
