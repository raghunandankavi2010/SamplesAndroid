package com.raghu.printhtml;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        doWebViewPrint();
    }


    private void doWebViewPrint() {
        // Create a WebView object specifically for printing
        mWebView = new WebView(this);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "page finished loading " + url);
                createWebPrintJob(view);
                //mWebView = null;
            }
        });

        mWebView.getSettings().setLoadsImagesAutomatically(true);

        // Generate an HTML document on the fly:
      /*  String htmlDocument = "<html><body><h1>Test Content</h1><p>Testing, " +
                "testing, testing...</p></body></html>";
        mWebView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);*/
      mWebView.loadUrl("file:///android_asset/print.html");

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager

    }

    private void createWebPrintJob(WebView mWebView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager)
                getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter;
        // Get a print adapter instance
        if(Build.VERSION.SDK_INT >=21) {
            printAdapter = mWebView.createPrintDocumentAdapter("Invoice");
        }else {
            //noinspection deprecation
            printAdapter = mWebView.createPrintDocumentAdapter();
        }

        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize( PrintAttributes.MediaSize.ISO_C7);


        // Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());


    }
}