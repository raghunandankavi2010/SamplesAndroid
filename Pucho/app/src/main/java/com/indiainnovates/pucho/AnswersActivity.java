package com.indiainnovates.pucho;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.indiainnovates.pucho.adapters.AnswerAdapter;
import com.indiainnovates.pucho.events.AnswerErrorEvent;
import com.indiainnovates.pucho.events.AnswersEvent;
import com.indiainnovates.pucho.models.Answers;

import com.indiainnovates.pucho.models.FollowModel;
import com.indiainnovates.pucho.presenters.AnswersPresenter;
import com.indiainnovates.pucho.provider.FavoriteContract;
import com.indiainnovates.pucho.screen_contracts.AnswerScreen;
import com.indiainnovates.pucho.utils.Utility;
import com.indiainnovates.pucho.widgets.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by Tamil on 4/28/2015.
 */
public class AnswersActivity extends AppCompatActivity implements AnswerScreen, AnswerAdapter.OnEditButtonClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView question_detailed;
    private FloatingActionButton fab;
    private int question_id, user_id;
    private boolean mLoadMore, mRequestPending, mError;

    @Inject
    AnswersPresenter answersPresenter;
    private AnswerAdapter answerAdapter;

    private ProgressBar progressBar;
    private int val;

    @Inject
    SharedPreferences sharedPreferences;

    private MenuItem follow,like;

    private static final String[] PROJECTION = new String[]
            {
                    FavoriteContract.Favorite.QUESTION_ID,
                    FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME,
                    FavoriteContract.Favorite.QUESTION_ACTIVE,
                    FavoriteContract.Favorite.QUESTION_TITLE,
                    FavoriteContract.Favorite.QUESTION_CONTENT,
                    FavoriteContract.Favorite.QUESTION_UPVOTE,
                    FavoriteContract.Favorite.QUESTION_DOWNVOTE,
                    FavoriteContract.Favorite.QUESTION_AUDIO_FILE_URL,
                    FavoriteContract.Favorite.QUESTION_ASKEDON,
                    FavoriteContract.Favorite.QUESTION_SAVED,
            };

    private Uri uri = FavoriteContract.Favorite.CONTENT_URI;

    private ContentResolver contentResolver;

    private static final String STATE_ANSWER = "state_feed";
    private static final String REQUEST_PEDNING = "request_pending";
    private static final String ERROR = "error";
    private ImageButton favButton, delete,edit;


    private int upvote, downvote;
    private String username, askedon, audiofileurl, content, active,language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        ((PuchoApplication) getApplication()).component().inject(this);
        EventBus.getDefault().register(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        language = prefs.getString("listkey","");
        answersPresenter.setAnswerScreen(this);


        delete = (ImageButton) this.findViewById(R.id.delete);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        favButton = (ImageButton) this.findViewById(R.id.star_btn);
        edit = (ImageButton) this.findViewById(R.id.edit_btn);
        TextView questioncontent = (TextView) this.findViewById(R.id.content);
        TextView date = (TextView) this.findViewById(R.id.answeredOn);
        TextView name = (TextView) this.findViewById(R.id.name);
        content = getIntent().getStringExtra("question");
        questioncontent.setText(content, TextView.BufferType.SPANNABLE);
        Utility.fixTextView(questioncontent);


        question_id = getIntent().getIntExtra("questionid", -1);
        user_id = getIntent().getIntExtra("userid", -1);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnswersActivity.this,EditActivity.class);
                intent.putExtra("question_id",question_id);
                intent.putExtra("content",content);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });

        answersPresenter.followQuestion(user_id, question_id);

        askedon = getIntent().getStringExtra("askedon");
        date.setText(Utility.calcualte_timeDifference(askedon));
        audiofileurl = getIntent().getStringExtra("audiofileurl");
        upvote = getIntent().getIntExtra("upvote", -1);
        downvote = getIntent().getIntExtra("downvote", -1);
        username = getIntent().getStringExtra("username");
        name.setText(username);
        active = getIntent().getStringExtra("active");


        fab = (android.support.design.widget.FloatingActionButton) this.findViewById(R.id.fab);

        scaleFab(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AnswersActivity.this, ComposeAnswer.class);
                intent.putExtra("question_id", question_id);
                startActivity(intent);
            }
        });


        /*Use toolbar as actionbar*/
        toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        //toolbar.setTitle("Answers");

        this.setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Answers");
        toolbar.setNavigationIcon(R.drawable.ic_up);


        recyclerView = (RecyclerView) this.findViewById(R.id.ansRV);
        answerAdapter = new AnswerAdapter(this);
        recyclerView.setAdapter(answerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);

        ImageButton shbutton = (ImageButton) findViewById(R.id.share_btn);

        shbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answersPresenter != null)
                    answersPresenter.setShareButtonClicked();
            }
        });

        question_id = getIntent().getIntExtra("questionid", -1);


        if (savedInstanceState != null) {
            boolean bool = savedInstanceState.getBoolean(REQUEST_PEDNING, false);

            if (bool) {
                // Log.i("LoadMore","Rotation");
                //mLoadMore = savedInstanceState.getBoolean("loadMore", mLoadMore);
                answersPresenter.setQuestionId(question_id);
                answersPresenter.setQuestionId(savedInstanceState.getInt("question_id"));
                answersPresenter.fetchAnswer();


            } else if (savedInstanceState.containsKey(STATE_ANSWER)) {
                Log.i("LoadSame date", "Rotation");
                List<Answers> list = savedInstanceState.getParcelableArrayList(STATE_ANSWER);
                answerAdapter.setData(list,language);
            } else {
                Log.i("Nothing", "Tryagain");
                mRequestPending = true;
                answersPresenter.setQuestionId(savedInstanceState.getInt("question_id"));
                answersPresenter.hideRecyclerView();
                answersPresenter.displayProgressBar();
                //answersPresenter.hideErrorText();
                answersPresenter.fetchAnswer();
            }
        } else {
            Log.i("First Time", "fetch");
            mRequestPending = true;
            answersPresenter.setQuestionId(question_id);
            answersPresenter.hideRecyclerView();
            answersPresenter.displayProgressBar();
            //answersPresenter.hideErrorText();
            answersPresenter.fetchAnswer();
        }

        contentResolver = getContentResolver();
        Cursor cursor = contentResolver.
                query(uri, PROJECTION, "question_id=?", new String[]{String.valueOf(question_id)}, null, null);
        if (cursor.moveToFirst()) {
            val = cursor.getInt(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_SAVED));
        }
        cursor.close();

        if (val == 0) {
            favButton.setImageDrawable(getImage(R.drawable.ic_favorite_border));
            //fab.setSelected(false);
        } else if (val == 1) {

            favButton.setImageDrawable(getImage(R.drawable.ic_favorite_full));
        }

        favButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (val == 0) {
                    favButton.setImageDrawable(getImage(R.drawable.ic_favorite_full));
                    val = 1;
                } else if (val == 1) {
                    favButton.setImageDrawable(getImage(R.drawable.ic_favorite_border));
                    val = 0;
                }
                setFavoredQuestion(val);
            }
        });


        /* delete functionality */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                answersPresenter.deleteQuestion(question_id);
            }
        });

        /* follow question functionality */
        /*Button follow = (Button) findViewById(R.id.follow);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id!=-1 && question_id!=-1)
                answersPresenter.followQuestion(user_id,question_id);
            }
        });*/
    }

    public void setFavoredQuestion(int val) {


        if (val == 1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(FavoriteContract.Favorite.QUESTION_ID, question_id);
            contentValues.put(FavoriteContract.Favorite.QUESTION_CONTENT, content);
            contentValues.put(FavoriteContract.Favorite.QUESTION_ACTIVE, active);
            contentValues.put(FavoriteContract.Favorite.QUESTION_ASKEDON, askedon);
            contentValues.put(FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME, username);
            contentValues.put(FavoriteContract.Favorite.QUESTION_UPVOTE, upvote);
            contentValues.put(FavoriteContract.Favorite.QUESTION_DOWNVOTE, downvote);
            contentValues.put(FavoriteContract.Favorite.QUESTION_AUDIO_FILE_URL, audiofileurl);
            contentValues.put(FavoriteContract.Favorite.QUESTION_SAVED, 1);

            //contentResolver.insert(uri, contentValues);

            AsyncQueryHandler handler = new AsyncQueryHandler(contentResolver) {
            };
            handler.startInsert(-1, null, uri, contentValues);
            Toast.makeText(getApplicationContext(), "Feed Saved", Toast.LENGTH_SHORT).show();

        } else if (val == 0) {
            contentResolver.delete(
                    uri,
                    "question_id=?",
                    new String[]{String.valueOf(question_id)});
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);


    }


    @Subscribe
    public void onEvent(AnswerErrorEvent e) {
        mRequestPending = false;
        e.getErrorEvent().printStackTrace();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        answersPresenter.onDestroyActivity();
    }

    @Subscribe
    public void onEvent(AnswersEvent answersEvent) {

        mRequestPending = false;
        if (answersEvent.getAnswers().size() > 0) {
            answersPresenter.hideProgressBar();
            answersPresenter.displayRecyclerView();
            answerAdapter.setData(answersEvent.getAnswers(),language);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("On Resume", "Resumed");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (answerAdapter.getAnswerList() != null && answerAdapter.getAnswerList().size() > 0) {
            outState.putParcelableArrayList(STATE_ANSWER, (ArrayList) answerAdapter.getAnswerList());
        }

        outState.putInt("question_id", question_id);
        outState.putBoolean(REQUEST_PEDNING, mRequestPending);
        outState.putBoolean(ERROR, mError);
        //outState.putInt("count", pageCount);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
             /*   Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);*/
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_follow:
                   // Toast.makeText(getApplicationContext(),"User id "+user_id+"question_id"+question_id,Toast.LENGTH_SHORT).show();
                    answersPresenter.followQuestion(user_id, question_id);

        return true;
    }

    return super.onOptionsItemSelected(item);

}

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onDisaplyRecylerView() {

        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgresBarDisplay() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void onShareButtonClicked() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, getString(R.string.send_intent_title)));
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onFetchedAnswer(List<Answers> answers) {

    }

    @Override
    public void onDelete_Success() {
        finish();
    }

    @Override
    public void onDelete_Failure() {
        // do nothing for now
    }

    @Override
    public void onErrorFollowResponse(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onSuccess(FollowModel likeModel) {

        if(likeModel.isSuccess() && follow!=null)
        {

            follow.setEnabled(false);
            //follow.getIcon().setAlpha(130);
            Toast.makeText(this,"You have successfully followed a question",Toast.LENGTH_SHORT).show();
        }
    }

    public void scaleFab(FloatingActionButton fab) {
        Animation scale_Up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.scale_up);
        fab.startAnimation(scale_Up);

    }

    public Drawable getImage(int id) {
        return ContextCompat.getDrawable(this, id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answers, menu);
        follow = menu.getItem(0);
        like =  menu.getItem(1);
        return true;
    }

    @Override
    public void onEditButtonClick(int pos) {
        Answers answers = answerAdapter.getAnswerList().get(pos);

        Intent intent = new Intent(this,EditAnswerActivity.class);
        intent.putExtra("question_id",question_id);
        if(language.equals("Hindi") && answers.getLanguageContents().size()>0) {
            intent.putExtra("content", answers.getLanguageContents().get(0).getContent());
        }else
        {
            intent.putExtra("content", answers.getContent());
        }
        intent.putExtra("answerid",answers.getId());
        startActivity(intent);
    }
}
