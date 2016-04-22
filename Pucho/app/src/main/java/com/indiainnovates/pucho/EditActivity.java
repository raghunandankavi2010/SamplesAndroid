package com.indiainnovates.pucho;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.indiainnovates.pucho.events.EditQuestionErrorEvent;
import com.indiainnovates.pucho.events.EditQuestionEvent;
import com.indiainnovates.pucho.models.Answers;
import com.indiainnovates.pucho.presenters.EditQuestionPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 12-04-2016.
 */
public class EditActivity extends AppCompatActivity {

    private int question_id, answerid,user_id;
    private String content;
    private Button edit;

    private static final String TAG  = EditActivity.class.getSimpleName();
    @Inject
    EditQuestionPresenter editQuestionPresenter;

    private EditText editText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PuchoApplication.component().inject(this);
        setContentView(R.layout.activity_post_question);
        editText = (EditText) this.findViewById(R.id.editText);

        //editQuestionPresenter.setContext(this);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_up);
        edit = (Button) this.findViewById(R.id.button);
        edit.setText("Post Edited Question");
        this.setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Edit Question");
        getSupportActionBar().setHomeButtonEnabled(true);

        question_id = getIntent().getIntExtra("question_id", -1);
        content = getIntent().getStringExtra("content");
        editText.setText(content);
        user_id = getIntent().getIntExtra("user_id",-1);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editQuestionPresenter.postEditQuestion(question_id,editText.getText().toString(),user_id);
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
    public void onEvent(EditQuestionEvent editQuestionEvent)
    {
        Toast.makeText(getApplicationContext(),"Question Edit "+editQuestionEvent.getResponse(), Toast.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(this);
    }


    @Subscribe
    public void onEvent(EditQuestionErrorEvent editQuestionErrorEvent)
    {
        Toast.makeText(getApplicationContext(),"Question Edit "+editQuestionErrorEvent.getT(), Toast.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editQuestionPresenter.onDestroy();
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
