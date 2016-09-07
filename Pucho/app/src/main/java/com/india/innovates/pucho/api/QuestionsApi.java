package com.india.innovates.pucho.api;

import com.india.innovates.pucho.PuchoApplication;
//import com.india.innovates.pucho.dagger.DependencyInjector;


import javax.inject.Inject;


import retrofit2.Retrofit;

/**
 * Created by Raghunandan on 28-12-2015.
 */
public class QuestionsApi {

    //@Inject
    //OkHttpClient okHttpClient;

    @Inject
    Retrofit retrofit;

    public QuestionsApi()
    {
        PuchoApplication.netcomponent().inject(this);
        //DependencyInjector.getNetworkComponent().inject(this);
    }

}
