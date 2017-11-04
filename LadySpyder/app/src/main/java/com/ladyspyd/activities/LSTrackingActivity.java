package com.ladyspyd.activities;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ladyspyd.R;


public class LSTrackingActivity extends AppCompatActivity {

    private WebView webView;
    private View mask;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsshipping);
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mask = findViewById(R.id.mask);

        webView = findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false);


        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                progressBar.setProgress(progress);
                if (progress == 100) {
                    mask.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                } else {
                    mask.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);

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

                view.loadUrl("javascript:var appBanners = document.getElementsByClassName('col-xs-60 col-sm-60')[0].style.display = 'none', i;\n" +
                        "\n" +
                        "for (var i = 0; i < appBanners.length; i ++) {\n" +
                        "    appBanners[i].style.display = 'none';\n" +
                        "}\n");
                view.loadUrl("javascript:var button_hide = document.getElementsByClassName('page-content_btn btn btn-default btn-danger')[0].style.display = 'none'");
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
                view.loadUrl("javascript:var appBanners = document.getElementsByClassName('content-footer')[0].style.display = 'none', i;\n" +
                        "\n" +
                        "for (var i = 0; i < appBanners.length; i ++) {\n" +
                        "    appBanners[i].style.display = 'none';\n" +
                        "}\n");






            }
        };

        webView.loadUrl("https://ladyspyder.com/track-your-order/");

        webView.setWebViewClient(yourWebClient);
        //setContentView(webView );
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
