package com.example.raghu.dagger2android.dagger.components;


import com.example.raghu.dagger2android.dagger.UserScope;
import com.example.raghu.dagger2android.dagger.modules.NetworkModule;

import dagger.Component;

/**
 * Created by Raghunandan on 16-12-2015.
 */
@UserScope
@Component(dependencies = ApplicationComponent.class,modules =NetworkModule.class)
public interface NetworkComponent  {



}
