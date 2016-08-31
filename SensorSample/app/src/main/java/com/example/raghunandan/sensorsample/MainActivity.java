package com.example.raghunandan.sensorsample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity /*implements SensorEventListener */{
    private SensorManager mSensorManager;
    private Sensor mSensor;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        startService(new Intent(this,SensorService.class));
        finish();
        /*setContentView(R.layout.sensor_screen);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Log.d("MainActivity"," :"+mSensor.getMaximumRange());
        iv = (ImageView) findViewById(R.id.imageView1);*/
    }

/*    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {

        float distance = event.values[0];
        Log.d("MainActivity"," :"+distance);
        if (distance >= 5) {
            iv.setBackgroundColor(Color.RED);
        } else {
            iv.setBackgroundColor(Color.BLUE);
        }
    }*/
}