package com.example.raghu.dagger2testandroid;

import com.example.raghu.dagger2testandroid.data.MainModel;
import com.example.raghu.dagger2testandroid.models.Example;
import com.example.raghu.dagger2testandroid.models.User;
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter;
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class MainActivityPresenterTest {

    @Mock
    private MainPresenterContract.View mainView;

    @Mock
    private MainModel mainModel;

    private MainActivityPresenter mainActivityPresenter;
    private Scheduler testScheduler;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        mainActivityPresenter = new MainActivityPresenter(mainView,mainModel,testScheduler,testScheduler);
    }

    @Test
    public void loadItems_WhenDataIsAvailable_ShouldUpdateViews() {
        Example example = new Example();
        User user = new User();
        user.setAge("31");
        user.setName("Raghunandan Kavi");
        example.setUser(user);
        doReturn(Single.just(example)).when(mainModel).loadData();
       /* mainActivityPresenter.doSomething();
        testScheduler.now(TimeUnit.MILLISECONDS);
        verify(mainView).showData(example.getUser());*/
    }




}
