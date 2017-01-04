package innovates.com.pucho;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import innovates.com.pucho.adapters.AnswerAdapter;
import innovates.com.pucho.models.Answers;


/**
 * Created by Tamil on 4/28/2015.
 */
public class AnswersActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView question_detailed;
    private android.support.design.widget.FloatingActionButton fab;
    private int question_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        question_id = getIntent().getIntExtra("question_id",-1);
        fab = (android.support.design.widget.FloatingActionButton)this.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AnswersActivity.this,ComposeAnswer.class);
                intent.putExtra("question_id",question_id);
                startActivity(intent);
            }
        });


        /*Use toolbar as actionbar*/
        toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Answers");

        this.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_up);

        question_detailed = (TextView) this.findViewById(R.id.question);

        recyclerView = (RecyclerView) this.findViewById(R.id.ansRV);
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

   public void onEvent(String question){

     question_detailed.setText(question);

    }

    public void onEvent(ArrayList<Answers> listAnswers){

        recyclerView.setAdapter(new AnswerAdapter(listAnswers));
        Toast.makeText(getApplicationContext(),"Answer Activity",Toast.LENGTH_SHORT).show();
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
