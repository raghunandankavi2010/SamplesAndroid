package com.indiainnovates.pucho;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.indiainnovates.pucho.events.ComposeAnswerErrorEvent;
import com.indiainnovates.pucho.events.PostAnswerResponseEvent;
import com.indiainnovates.pucho.presenters.ComposeAnswerPresenter;
import com.indiainnovates.pucho.screen_contracts.ComposeAnswerScreen;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;


/**
 * Created by Raghunandan on 16-06-2015.
 */
public class ComposeAnswer extends AppCompatActivity implements ComposeAnswerScreen {

    @Inject
    SharedPreferences sharedPreferences;
    int id,statusCode;
    private Toolbar toolbar;
    private EditText postAnswereditText;
    private Button post;
    private int question_id;

    private static final String TAG ="ComposeAnswer";

    @Inject
    ComposeAnswerPresenter composeAnswerPresenter;
    //Question id 4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_question);
        ((PuchoApplication) getApplication()).component().inject(this);
        EventBus.getDefault().register(this);
        question_id = getIntent().getIntExtra("question_id",-1);

        composeAnswerPresenter.setPresenterScreen(this);

        //sharedPreferences = getSharedPreferences("User_ID", MODE_PRIVATE);
        id = sharedPreferences.getInt("user_id", -1);

           /* Use toolbar as actionbar */
        toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Compose a Answer");


        this.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_up);

        postAnswereditText = (EditText) this.findViewById(R.id.editText);
        postAnswereditText.setHint("Compose Answer");
        post = (Button) this.findViewById(R.id.button);
        post.setText("POST A ANSWER");
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                composeAnswerPresenter.onPostButtonClicked();
               /* if(!TextUtils.isEmpty(editText.getText().toString()))
                {
                    // rest call to pot a question.
                    // for now i use asynctask.
                    // later retrofit coupled with rxjava
                    if(CheckNetwork.isInternetAvailable(ComposeAnswer.this)) {
                        if(!TextUtils.isEmpty(editText.getText().toString()))
                        new PostTask().execute(editText.getText().toString());

                    } else
                    {
                        Toast.makeText(getApplicationContext(),"Check your network connection",Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        composeAnswerPresenter.onDestroyActivity();
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

    @Override
    public void onPostAnswerClicked() {
        composeAnswerPresenter.setPostAnswerContent(postAnswereditText.getText().toString());
        composeAnswerPresenter.validatePostAnswerContent();
    }

    @Subscribe
    public void onEvent(String check) {

        if(check.equals("Empty")) {
            postAnswereditText.setError("Content cannot be Empty!.");
        }
        else if(check.equals("Success"))
        {
            if(id!=-1) {
                composeAnswerPresenter.setUserId(id);
                composeAnswerPresenter.setQuestionId(question_id);
                composeAnswerPresenter.setPostAnswerContent(postAnswereditText.getText().toString());
                composeAnswerPresenter.sendContentToServer();
            }
        }

    }

    @Subscribe
    public void onEvent(ComposeAnswerErrorEvent composeAnswerErrorEvent)
    {
        composeAnswerErrorEvent.getErrorEvent().printStackTrace();
    }

    @Subscribe
    public void onEvent(PostAnswerResponseEvent postAnswerResponseEvent)
    {
       int id =  postAnswerResponseEvent.getPostAnswerResponseList().get(0).getId();

        Log.i(TAG,"Id is "+id);
        Toast.makeText(getApplicationContext(),"You have asnwered the question successfully",Toast.LENGTH_SHORT).show();
    }

}


