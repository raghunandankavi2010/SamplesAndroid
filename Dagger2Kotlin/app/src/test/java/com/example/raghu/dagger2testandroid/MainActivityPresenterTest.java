package com.example.raghu.dagger2testandroid;

import com.example.raghu.dagger2testandroid.data.MainModel;
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter;
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MainActivityPresenterTest {

    @Mock
    private MainPresenterContract.View mainView;

    //@Mock
    private MainModel mainModel;

    private MainActivityPresenter mainActivityPresenter;

    @Before
    public void setUp(){
        mainModel = Mockito.mock(MainModel.class);
        MockitoAnnotations.initMocks(this);
        mainActivityPresenter = new MainActivityPresenter(mainView,mainModel);
    }



}
