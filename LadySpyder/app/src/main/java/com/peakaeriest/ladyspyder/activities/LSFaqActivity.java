package com.peakaeriest.ladyspyder.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.adapters.APFaqListAdapter;
import com.peakaeriest.ladyspyder.models.LSFAQModel;
import com.peakaeriest.ladyspyder.rest.APIInterface;
import com.peakaeriest.ladyspyder.rest.ApiClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LSFaqActivity extends LSBaseActivity {
    public RecyclerView lvNews;
    public APFaqListAdapter mFaqAdapter;
    public Gson mGson;
    private LSFAQModel newsPojo;
    private WebView wv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsfaq);
        updateFragmentTitle("FAQ", true, false);
        wv1 = (WebView) findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());
        wv1.loadUrl("file:///android_asset/faq.html");
      /*  newsPojo = new LSFAQModel();
        lvNews = (RecyclerView) findViewById(R.id.list_latest_videos);
        lvNews.setLayoutManager(new LinearLayoutManager(LSFaqActivity.this));
        lvNews.setItemAnimator(new DefaultItemAnimator());

        getFAQ();
*/
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements
            RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private LSFaqActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context,
                                     final RecyclerView recyclerView,
                                     final LSFaqActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context,
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            View child = recyclerView.findChildViewUnder(
                                    e.getX(), e.getY());
                            if (child != null && clickListener != null) {
                                clickListener.onLongClick(child,
                                        recyclerView.getChildPosition(child));
                            }
                        }
                    });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null
                    && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(
                boolean disallowIntercept) {

        }
    }


    /**
     * FAQ API CALL
     */
    public void getFAQ() {

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");

        APIInterface appInterface = ApiClient.getClient().create(APIInterface.class);

        Call<LSFAQModel> call = appInterface.getfaq(header);

        call.enqueue(new Callback<LSFAQModel>() {
            @Override
            public void onResponse(Call<LSFAQModel> call, Response<LSFAQModel> response) {

                try {
                    mFaqAdapter = new APFaqListAdapter(LSFaqActivity.this, response.body().getFAQ());
                    lvNews.setAdapter(mFaqAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<LSFAQModel> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kd_action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
