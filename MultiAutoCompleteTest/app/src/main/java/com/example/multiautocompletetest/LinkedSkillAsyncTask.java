/*
package com.example.multiautocompletetest;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LinkedinSkillAsyncTask  extends AsyncTask<String, String, String> {

    private Activity context;
    public String data;
    public List<String> suggest;
    public ArrayAdapter<String> aAdapter;

    public LinkedinSkillAsyncTask(Activity cntxt) {
        context = cntxt;
    }


    @Override protected String doInBackground(String... key) {
        String newText = key[0];
        newText = newText.trim();
        newText = newText.replace(" ", "+");
        try {
            HttpClient hClient = new DefaultHttpClient();
            HttpGet hGet = new HttpGet("http://www.linkedin.com/ta/skill?query="+newText);

            ResponseHandler<String> rHandler = new BasicResponseHandler();
            data = hClient.execute(hGet, rHandler);
            suggest = new ArrayList<String>();
            JSONObject jobj = new JSONObject(data);
            JSONArray jArray = jobj.getJSONArray("resultList");
            for (int i = 0; i < jArray.length(); i++) {
                String SuggestKey = jArray.getJSONObject(i).getString("displayName");
                suggest.add(SuggestKey);
            }
        } catch (Exception e) {
            Log.w("Error", e.getMessage());
        }

        context.runOnUiThread(new Runnable() {
            public void run() {
                MultiAutoCompleteTextView inputEditText = (MultiAutoCompleteTextView) context.findViewById(R.id.multiAutoCompleteTextView1);
                aAdapter = new ArrayAdapter<String>( context, android.R.layout.simple_dropdown_item_1line, suggest);
                inputEditText.setAdapter(aAdapter);
                aAdapter.notifyDataSetChanged();
            }
        });

        return null;
    }

}*/
