package com.example.raghunandan.localizationtest;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) this.findViewById(R.id.button1);
        Button b2 = (Button) this.findViewById(R.id.button2);

        tv = (TextView) this.findViewById(R.id.textView);

        tv.setText(getResources().getString(R.string.hello));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocaleHelper.setLocale(MainActivity.this.getApplicationContext(),"en");
                finish();
                startActivity(getIntent());
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocale(MainActivity.this.getApplicationContext(),"hi");
                finish();
                startActivity(getIntent());
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {

        Locale newLocale = new Locale( LocaleHelper.getPersistedData(newBase,"en"));
        // .. create or get your new Locale object here.

        Context context = ContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }


}
