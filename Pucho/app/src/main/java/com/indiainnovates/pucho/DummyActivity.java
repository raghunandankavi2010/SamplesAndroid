package com.indiainnovates.pucho;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.indiainnovates.pucho.adapters.AnswerAdapter;
import com.indiainnovates.pucho.presenters.AnswersPresenter;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 08-02-2016.
 */
public class DummyActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView question_detailed;
    private FloatingActionButton fab;
    private int question_id;
    private boolean mLoadMore,mRequestPending,mError;

    @Inject
    AnswersPresenter answersPresenter;
    private AnswerAdapter answerAdapter;

    private ProgressBar progressBar;

    @Inject
    SharedPreferences sharedPreferences;

    private static final String STATE_ANSWER = "state_feed";
    private static final String REQUEST_PEDNING = "request_pending";
    private static final String ERROR = "error";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_answers_detailed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.i("Dummy destroyed","Yes");
    }
}
