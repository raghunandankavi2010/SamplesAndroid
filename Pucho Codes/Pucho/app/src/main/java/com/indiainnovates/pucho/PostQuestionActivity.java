package com.indiainnovates.pucho;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sony on 4/25/2015.
 */
public class PostQuestionActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private EditText editText;
    private Button post;

   // private HttpClient httpClient;
    private int statusCode;
   // private HttpResponse httpResponse;
   // private HttpPost httpPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_question);

        /* Use toolbar as actionbar */
        toolbar = (Toolbar) this.findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Ask a Question");


        this.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_up);

        editText = (EditText) this.findViewById(R.id.editText);
        post = (Button) this.findViewById(R.id.button);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editText.getText().toString()))
                {
                    // rest call to pot a question.
                    // for now i use asynctask.
                    // later retrofit coupled with rxjava
                   // new PostTask().execute(editText.getText().toString());
                }
            }
        });
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
  /*  class PostTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                httpClient = new DefaultHttpClient();
                httpPost = new HttpPost(UrlStrings.BASE_URL+UrlStrings.LOGIN_SVC_PATH);
//
//                httpPost.setHeader("Content-type","application/json");
//                // Log.d("in LoginDAO",UrlStrings.BASE_URL+UrlStrings.LOGIN_SVC_PATH);
//                httpPost.setEntity(new StringEntity(jsonObject.toString()));
                httpResponse = httpClient.execute(httpPost);
                statusCode = httpResponse.getStatusLine().getStatusCode();
                //if statusCode ==200 , then Success success. else Success Failure
                if(statusCode==200){

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            // background call
            return null;
        }
    }*/
}