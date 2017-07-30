package assignment.com.raghu.androdiassignment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import assignment.com.raghu.androdiassignment.dagger.modules.ActivityModule;
import assignment.com.raghu.androdiassignment.dagger.modules.components.ActivityComponent;
import assignment.com.raghu.androdiassignment.dagger.modules.components.ApplicationComponent;
import assignment.com.raghu.androdiassignment.dagger.modules.components.DaggerActivityComponent;
import assignment.com.raghu.androdiassignment.presenters.AbstractPresenter;
import assignment.com.raghu.androdiassignment.presenters.BasePresenter;

/**
 * Created by raghu on 29/7/17.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    @Inject
    protected T presenter;
    protected ActivityModule activityModule;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationComponent appComponent = ((DemoApplication) getApplication()).getApplicationComponent();

        activityModule = new ActivityModule();
        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(appComponent)
                .activityModule(activityModule)
                .build();
        injectFrom(activityComponent);


        ((AbstractPresenter) presenter).onViewCreated(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected abstract void injectFrom(ActivityComponent activityComponent);


    @Override
    protected void onDestroy() {
        ((AbstractPresenter) presenter).onViewDestroyed();
        activityModule = null;
        super.onDestroy();
    }
}
