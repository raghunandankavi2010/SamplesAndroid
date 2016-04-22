package com.indiainnovates.pucho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.indiainnovates.pucho.adapters.MyQuestionsAdapter;
import com.indiainnovates.pucho.animations.FeedItemAnimator;
import com.indiainnovates.pucho.listeners.ShareButtonClickListener;
import com.indiainnovates.pucho.models.MyQuestionsFetched;
import com.indiainnovates.pucho.presenters.MyQuestionsPresenter;
import com.indiainnovates.pucho.screen_contracts.MyQuestionScreen;
import com.indiainnovates.pucho.widgets.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 18-03-2016.
 */
public class MyQuestions extends AppCompatActivity implements MyQuestionScreen, ShareButtonClickListener {

    private static final String STATE_FEED = "state_feed";
    private static final String REQUEST_PEDNING = "request_pending";
    private static final String ERROR = "error";

    private boolean mRequestPending;

    @Inject
    MyQuestionsPresenter myQuestionsPresenter;

    @Inject
    SharedPreferences sharedPreferences;

    private ProgressBar pb;

    private EmptyRecyclerView recylerView;
    private MyQuestionsAdapter questionsAdapter;

    private int userId;

    private TextView tv;
    private String language;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myquestions);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        language = prefs.getString("listkey","");

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle(getResources().getString(R.string.myquestions));
        toolbar.setNavigationIcon(R.drawable.ic_up);
        this.setSupportActionBar(toolbar);
        pb = (ProgressBar) this.findViewById(R.id.progressBar);
        recylerView = (EmptyRecyclerView) this.findViewById(R.id.recyclerview);
        tv = (TextView) this.findViewById(R.id.errorTextView);
        PuchoApplication.component().inject(this);
        myQuestionsPresenter.setContext(this);
        userId = sharedPreferences.getInt("user_id", -1);


        questionsAdapter = new MyQuestionsAdapter(this);

        recylerView.setAdapter(questionsAdapter);

        // 1 column in portrait mode and 2 columns in landscape mode
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);//etActivity().getResources().getInteger(R.integer.grid_columns));
        // mGridLayoutManager.setReverseLayout(true);
        // mLayoutManager.setStackFromEnd(true);
        recylerView.setLayoutManager(mLinearLayoutManager);
        recylerView.setHasFixedSize(true);
        recylerView.setItemAnimator(new FeedItemAnimator());


        if (savedInstanceState != null) {
             //if (savedInstanceState.containsKey(STATE_FEED)) {
                Log.i("LoadSame Data", "Rotation");
                List<MyQuestionsFetched> list = savedInstanceState.getParcelableArrayList(STATE_FEED);
                questionsAdapter.setData(list,language);
           // }

        } else  if(savedInstanceState==null){

             mRequestPending =true;

            if (userId != -1) {
                recylerView.setVisibility(View.GONE);
                myQuestionsPresenter.displayProgressBar();
                myQuestionsPresenter.fetchMyQuestions(userId);
            }
        }


    }

    @Override
    public void displayProgressBar() {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressBar() {
        pb.setVisibility(View.GONE);
    }

    @Override
    public void displayRecyclerView() {

    }

    @Override
    public void hideRecycelrView() {

    }


    @Override
    public void fetchedQuestions(List<MyQuestionsFetched> myQuestions) {

        if (myQuestions != null && myQuestions.size() > 0) {
            //.makeText(this.getApplicationContext(),"Fetched Feed",Toast.LENGTH_SHORT).show();
            myQuestionsPresenter.dismissProgressBar();
            recylerView.setVisibility(View.VISIBLE);
            questionsAdapter.setData(myQuestions,language);
        }
    }

    @Override
    public void onError(Throwable e) {

        e.printStackTrace();
        myQuestionsPresenter.dismissProgressBar();
        recylerView.setVisibility(View.GONE);
        tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void share(int position) {

    }

    @Override
    public void onCardClick(int position) {


        if (questionsAdapter != null) {
            Intent intent = new Intent(this, MyQuestionsDetailAnswerActivity.class);
            if(language.equals("Hindi") && questionsAdapter.getmList().get(position).getLanguageContents().size()>0)
            {
                intent.putExtra("question", questionsAdapter.getmList().get(position).getLanguageContents().get(0).getContent());
            }else {
                intent.putExtra("question", questionsAdapter.getmList().get(position).getTitle());
            }
            Log.i("Question id", "" + questionsAdapter.getmList().get(position).getId());
            intent.putExtra("questionid", questionsAdapter.getmList().get(position).getId());
            intent.putExtra("askedon", questionsAdapter.getmList().get(position).getAskedOn());
            intent.putExtra("audiofileurl", questionsAdapter.getmList().get(position).getAudioFileUrl());
            intent.putExtra("upvote", questionsAdapter.getmList().get(position).getUpvote());
            intent.putExtra("downvote", questionsAdapter.getmList().get(position).getDownvote());
            intent.putExtra("username", questionsAdapter.getmList().get(position).getUser().getFullName());
            if (questionsAdapter.getmList().get(position).isActive()) {
                intent.putExtra("active", "true");
            } else {
                intent.putExtra("active", "false");
            }
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        myQuestionsPresenter.onActivityDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        if (questionsAdapter.getmList().size() > 0) {
            outState.putParcelableArrayList(STATE_FEED, (ArrayList) questionsAdapter.getmList());
        }
        outState.putBoolean(REQUEST_PEDNING, mRequestPending);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
