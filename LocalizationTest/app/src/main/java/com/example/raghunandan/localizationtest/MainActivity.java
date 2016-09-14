package com.example.raghunandan.localizationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button b1,b2;

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) this.findViewById(R.id.button1);
        b2 = (Button) this.findViewById(R.id.button2);

        tv = (TextView) this.findViewById(R.id.textView);
        LocaleHelper.setLocale(MainActivity.this, LocaleHelper.getLanguage(this));
        tv.setText(getResources().getString(R.string.hello));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocaleHelper.setLocale(MainActivity.this, "en");
                tv.setText(getResources().getString(R.string.hello));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocale(MainActivity.this, "hi");
                tv.setText(getResources().getString(R.string.hello));
            }
        });
    }
}
