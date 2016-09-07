package com.india.innovates.pucho;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.india.innovates.pucho.events.AskQuestionErrorEvent;
import com.india.innovates.pucho.events.PostQuestionResponeEvent;
import com.india.innovates.pucho.presenters.AskQuestionPresenter;
import com.india.innovates.pucho.screen_contracts.PostQuestionScreen;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;



/**
 * Created by sony on 4/25/2015.
 */
public class PostQuestion extends AppCompatActivity implements PostQuestionScreen {


    private Toolbar toolbar;
    private EditText questionEditText;
    private Button post;

    private int statusCode;
    @Inject
    SharedPreferences sharedPreferences;
    int id;


    private static final String TAG = "PostQuestion";

    @Inject
    AskQuestionPresenter askQuestionPresenter;

    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        super.onCreate(savedInstanceState);
       // overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(R.layout.post_question);

        final RelativeLayout rootLayout = (RelativeLayout) this.findViewById(R.id.rl);
/*
        if (savedInstanceState == null) {
            rootLayout.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity(rootLayout);
                        rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
            }
        }*/
        PuchoApplication.component().inject(this);
        EventBus.getDefault().register(this);

        askQuestionPresenter.setPresenterScreen(this);

        id = sharedPreferences.getInt("user_id", -1);

        /* Use toolbar as actionbar */
        toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle(getResources().getString(R.string.compose_question));


        this.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_up);

        questionEditText = (EditText) this.findViewById(R.id.editText);
        post = (Button) this.findViewById(R.id.button);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                post.setEnabled(false);
                askQuestionPresenter.postButtonClicked();

            }
        });
    }

/*    private void circularRevealActivity(RelativeLayout rootLayout) {

        int cx = rootLayout.getWidth() / 2;
        int cy = rootLayout.getHeight() / 2;

        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
            circularReveal.setDuration(1000);

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        }

    }*/

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        askQuestionPresenter.onDestroyActivity();
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


    @Subscribe
    public void onEvent(String check) {

        if (check.equals("Empty")) {
            questionEditText.setError("Content cannot be Empty!. Body must have 10 Characters.");
        } else if (check.equals("Success")) {
            if (id != -1) {
                askQuestionPresenter.setUserId(id);
                askQuestionPresenter.setQuestionContent(questionEditText.getText().toString());
                askQuestionPresenter.sendContentToServer();
            }
        }

    }


    @Override
    public void onPostButtonClicked() {
        askQuestionPresenter.setQuestionContent(questionEditText.getText().toString());

        askQuestionPresenter.validateContentEmpty();

    }

    @Subscribe
    public void onEvent(AskQuestionErrorEvent event) {

        event.getErrorEvent().printStackTrace();
    }

    @Subscribe
    public void onEvent(PostQuestionResponeEvent postQuestionResponeEvent) {
        post.setEnabled(true);
        int questionid = postQuestionResponeEvent.getQuestionContentModelList().get(0).getId();
        Log.i(TAG, "Question id " + questionid);

        Toast.makeText(getApplicationContext(), "Question Posted Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

}