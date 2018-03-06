package com.demo_sensors.pulkit.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.demo_sensors.pulkit.R;
import com.demo_sensors.pulkit.animation.AnimatedView;

import java.util.Random;

public class BallAccelerometerSensorActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager senseManager;
    private Sensor sensor;
    private TextView textView;

    private AnimatedView mAnimatedView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*remove MenuBar, TollBar etc*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAnimatedView = new AnimatedView(this);
        setContentView(mAnimatedView);
//        setContentView(R.layout.activity_accelerometer_sensor);

        findIds();
        init();
    }

    private void findIds() {

        textView = (TextView) findViewById(R.id.txt);
    }

    private void init() {

        senseManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = senseManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAnimatedView.onSensorEvent(sensorEvent);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        senseManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregister Sensor listener
        senseManager.unregisterListener(this);
    }

}
