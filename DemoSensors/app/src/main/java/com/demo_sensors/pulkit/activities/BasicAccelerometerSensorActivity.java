package com.demo_sensors.pulkit.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.demo_sensors.pulkit.R;

public class BasicAccelerometerSensorActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager senseManager;
    private Sensor sensor;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_accelerometer_sensor);

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

        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        if (Math.abs(x) > Math.abs(y)) {
            if (x < 0) {
                textView.setText("You tilt the device right");
            }
            if (x > 0) {
                textView.setText("You tilt the device left");
            }
        } else {
            if (y < 0) {
                textView.setText("You tilt the device up");
            }
            if (y > 0) {
                textView.setText("You tilt the device down");
            }
        }
        if (x > (-2) && x < (2) && y > (-2) && y < (2)) {
            textView.setText("Not tilt device");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        senseManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregister Sensor listener
        senseManager.unregisterListener(this);
    }

}
