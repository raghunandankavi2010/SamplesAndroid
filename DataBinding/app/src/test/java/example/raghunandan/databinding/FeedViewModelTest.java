package example.raghunandan.databinding;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import example.raghunandan.databinding.apis.DataManager;
import example.raghunandan.databinding.apis.FeedApi;
import example.raghunandan.databinding.models.FeedResponse;
import example.raghunandan.databinding.viewmodel.FeedViewModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;
import static org.junit.Assert.*;


import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;



/**
 * Created by Raghunandan on 28-09-2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Observable.class, AndroidSchedulers.class})
@PowerMockIgnore("javax.net.ssl.*")
public class FeedViewModelTest {


    FeedViewModel feedViewModel;
    DataManager dataManager;
    FeedViewModel.DataListener dataListener;
    FeedApi feedApi;
    FeedResponse feedResponse;


    @Before
    public void setUp() {

        dataListener = mock(FeedViewModel.DataListener.class);
        Context mMockContext = mock(Context.class);
        dataManager =mock(DataManager.class);
        feedApi = mock(FeedApi.class);
        feedViewModel = spy(new FeedViewModel(mMockContext, dataListener,dataManager));
        feedResponse = mock(FeedResponse.class);

    }

    @Test
    public void testShouldScheduleLoadFromAPIOnBackgroundThread() {



        Observable<FeedResponse> observable = (Observable<FeedResponse>) mock(Observable.class);

        when(dataManager.fetchFeed()).thenReturn(observable);
        when(observable.subscribeOn(Schedulers.io())).thenReturn(observable);
        when(observable.observeOn(AndroidSchedulers.mainThread())).thenReturn(observable);

        //call test method
        feedViewModel.fetchFeed();
        verify(feedViewModel).fetchFeed();

        verify(observable).subscribeOn(Schedulers.io());
        verify(observable).observeOn(AndroidSchedulers.mainThread());
        verify(observable).subscribeWith(Matchers.<DisposableObserver<FeedResponse>>any());

    }

}
