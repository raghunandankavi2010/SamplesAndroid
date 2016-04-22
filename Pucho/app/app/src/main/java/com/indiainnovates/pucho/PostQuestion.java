package com.indiainnovates.pucho;

import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.indiainnovates.pucho.events.AskQuestionErrorEvent;
import com.indiainnovates.pucho.events.ErrorEvent;
import com.indiainnovates.pucho.events.PostQuestionResponeEvent;
import com.indiainnovates.pucho.listeners.OnFragmentCallback;
import com.indiainnovates.pucho.presenters.AskQuestionPresenter;
import com.indiainnovates.pucho.screen_contracts.PostQuestionScreen;
import com.indiainnovates.pucho.utils.CheckNetwork;
import com.indiainnovates.pucho.utils.UrlStrings;
import com.indiainnovates.pucho.utils.Utility;

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
        setContentView(R.layout.post_question);
        PuchoApplication.component().inject(this);
        EventBus.getDefault().register(this);

        askQuestionPresenter.setPresenterScreen(this);

        id = sharedPreferences.getInt("user_id", -1);

        /* Use toolbar as actionbar */
        toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Ask a Question");


        this.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_up);

        questionEditText = (EditText) this.findViewById(R.id.editText);
        post = (Button) this.findViewById(R.id.button);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                askQuestionPresenter.postButtonClicked();

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
            questionEditText.setError("Content cannot be Empty!.");
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
        int questionid = postQuestionResponeEvent.getQuestionContentModelList().get(0).getId();
        Log.i(TAG, "Question id " + questionid);
        Toast.makeText(getApplicationContext(), "Question Posted Successfully", Toast.LENGTH_SHORT).show();
    }

}