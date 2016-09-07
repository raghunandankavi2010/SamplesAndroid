package com.india.innovates.pucho;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.india.innovates.pucho.events.EditAnswerFailureEvent;
import com.india.innovates.pucho.events.EditAnswerPostSuccessEvent;
import com.india.innovates.pucho.presenters.EditAnswerPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 18-04-2016.
 */
public class EditAnswerActivity extends AppCompatActivity {

    @Inject
    EditAnswerPresenter editAnswerPresenter;

    private Button post;
    private int answer_id;
    private int question_id;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PuchoApplication.component().inject(this);
        setContentView(R.layout.activity_post_question);


        Toolbar toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_up);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Answer");

        answer_id = getIntent().getIntExtra("answerid",-1);
        question_id = getIntent().getIntExtra("question_id",-1);
        post = (Button) findViewById(R.id.button);
        post.setText("POST EDITED ANSWER");

        editText = (EditText) this.findViewById(R.id.editText);
        editText.setText(getIntent().getStringExtra("content"));

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setEnabled(false);
                editAnswerPresenter.post_editedanswer(question_id,answer_id,editText.getText().toString());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(EditAnswerPostSuccessEvent editAnswerPostSuccessEvent)
    {

        Toast.makeText(getApplicationContext(),"Answer Edited Successfully",Toast.LENGTH_SHORT).show();
        finish();

    }

    @Subscribe
    public void onEvent(EditAnswerFailureEvent editAnswerFailureEvent)
    {
        post.setEnabled(true);
        Toast.makeText(getApplicationContext(),"Answer Edit Failed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                NavUtils.navigateUpFromSameTask(this);
                return true;


        }
        return true;
    }

}
