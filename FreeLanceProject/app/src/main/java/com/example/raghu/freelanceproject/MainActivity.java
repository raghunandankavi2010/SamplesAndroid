package com.example.raghu.freelanceproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.raghu.freelanceproject.R.styleable.CrystalRangeSeekbar;

public class MainActivity extends AppCompatActivity implements OnSeekbarChangeListener,OnSeekbarFinalValueListener{

    private String[] text_draw = {"4hrs", "8hrs", "12hrs", "16hrs", "20hrs", "24hrs"};
    private int numberOfCircles =6;
    private int minValue = 4;
    private int maxValue = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CrystalSeekbar rangeSeekbar = (CrystalSeekbar) this.findViewById(R.id.rangeSeekbar1);
        // text_draw length must be same as number of cricles
        rangeSeekbar.setText_draw(text_draw);
        rangeSeekbar.setNumberOfCircle(numberOfCircles);

        rangeSeekbar.setMinValue(minValue);
        rangeSeekbar.setMaxValue(maxValue);
        rangeSeekbar.setTextColor(Color.BLACK);
        rangeSeekbar.setLeftThumbColor(Color.GRAY); // cricle indicator color

        // listeners
        rangeSeekbar.setOnSeekbarChangeListener(this);
        rangeSeekbar.setOnSeekbarFinalValueListener(this);

    }

    @Override
    public void valueChanged(Number value) {
        // do what you want with values
    }

    @Override
    public void finalValue(Number value) {
        // do what you want with values
    }
}
