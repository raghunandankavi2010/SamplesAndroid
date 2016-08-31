package com.example.raghunandan.sensorsample;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Raghunandan on 31-08-2016.
 */

public class SensorService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {

        float distance = event.values[0];
        Log.d("MainActivity"," :"+distance);
        if (distance >= mSensor.getMaximumRange()) {
            Toast.makeText(this.getApplicationContext(),"Far from face",Toast.LENGTH_SHORT).show();
        } else if(distance <= 3){

            Toast.makeText(this.getApplicationContext(),"Very Near to face",Toast.LENGTH_SHORT).show();
        }
    }
}
