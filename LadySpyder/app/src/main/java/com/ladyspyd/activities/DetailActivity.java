package com.ladyspyd.activities;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ladyspyd.R;


/**
 * Created by raghu on 17/10/17.
 */

public class DetailActivity extends AppCompatActivity {

    private WebView webView;
    private View mask;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mask = findViewById(R.id.mask);

        webView = findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setBackgroundColor(Color.parseColor("#ffffff"));

        //mask.setText(getIntent().getStringExtra("title"));

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                Log.i("Web", "" + view.getUrl());
                if (view.getUrl().contains("citruspage") || view.getUrl().contains("citruspay")) {
                    //mask.setVisibility(View.GONE);
                    // progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setProgress(progress);
                    if (progress == 100) {
                        mask.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);

                    } else {
                        mask.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);

                    }
                }
            }

        });

        WebViewClient yourWebClient = new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {

                if (request.contains("citruspage") || request.contains("citruspay")) {
                    mask.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
                view.loadUrl(request);

                return true;
            }

            // here you execute an action when the URL you want is about to load
            @Override
            public void onLoadResource(WebView view, String url) {

            }

            @Override
            public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {

                return super.shouldInterceptRequest(view, url);


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("citruspage") || url.contains("citruspay")) {
                    mask.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                } else if(url.contains("thankyou")){
                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('col-xs-60 col-sm-60')[0].style.display = 'none', i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");

                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('col-lg-16 col-md-16 col-xs-60 pull-left hidden-xs')[0].style.display = 'none', i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");

                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('row')[0].style.display = 'none', i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");
                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('page-thank__order_btn')[0].style.display = 'none', i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");
                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('content-footer')[0].style.display = 'none', i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");
                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('continue_shopping')[0].style.display = 'none', i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");

                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('page-thank__order_btn'),i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");


                } else if(url.contains("cart")){

                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('row')[0].style.display = 'none',i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");

                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('col-xs-60 col-sm-60')[0].style.display = 'none', i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");

                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('col-lg-16 col-md-16 col-xs-60 pull-left hidden-xs')[0].style.display = 'none', i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");

                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('content-footer')[0].style.display = 'none', i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");


                }else{
                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('col-xs-60 col-sm-60')[0].style.display = 'none',i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");

                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('col-lg-16 col-md-16 col-xs-60 pull-left hidden-xs')[0].style.display = 'none', i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");

                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('row')[0].style.display = 'none',i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");

                    view.loadUrl("javascript:var appBanners = document.getElementsByClassName('content-footer')[0].style.display = 'none',i;\n" +
                            "\n" +
                            "for (var i = 0; i < appBanners.length; i ++) {\n" +
                            "    appBanners[i].style.display = 'none';\n" +
                            "}\n");

                }

            }
        };

        webView.loadUrl(getIntent().getStringExtra("title_url"));

        webView.setWebViewClient(yourWebClient);

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
