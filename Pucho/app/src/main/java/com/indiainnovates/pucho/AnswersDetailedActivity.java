package com.indiainnovates.pucho;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.indiainnovates.pucho.models.Answers;

/**
 * Created by Tamil on 4/28/2015.
 */
public class AnswersDetailedActivity extends AppCompatActivity {
    Answers ans;
    private Toolbar toolbar;
    private TextView dateTV;
    private TextView nameTV;
    private TextView ansTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers_detailed);

        /*Use toolbar as actionbar*/
        toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Answer in Detail");

        this.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_up);

        dateTV = (TextView) findViewById(R.id.dateTv1);
        nameTV = (TextView) findViewById(R.id.nameTV);
        ansTV = (TextView) findViewById(R.id.ansTV);
    }

    @Override
    protected void onStart() {
        super.onStart();
       // EventBus.getDefault().registerSticky(this);
    }


 /*   public void onEvent(Answers answer){

        Toast.makeText(getApplicationContext(), "Answers Detailed Activity", Toast.LENGTH_SHORT).show();
        dateTV.setText(answer.getAnsweredOn());
        nameTV.setText(answer.getUserName());
        ansTV.setText(answer.getContent());
    }*/

    /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answers_detailed, menu);
        return true;
    }
*/
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
    protected void onPause() {
        super.onPause();
        //EventBus.getDefault().unregister(this);
    }
}