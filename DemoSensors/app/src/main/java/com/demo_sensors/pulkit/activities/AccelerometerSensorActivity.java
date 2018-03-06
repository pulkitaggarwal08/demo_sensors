package com.demo_sensors.pulkit.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.demo_sensors.pulkit.R;

public class AccelerometerSensorActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager senseManager;
    private Sensor sensor;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_sensor);

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

    private int hitCount = 0;
    private double hitSum = 0;
    private double hitResult = 0;

    private final int SAMPLE_SIZE = 50; // change this sample size as you want, higher is more precise but slow measure.
    private final double THRESHOLD = 0.2; // change this threshold as you want, higher is more spike movement

    private float[] mGravity;
    private double mAccel;
    private double mAccelCurrent;
    private double mAccelLast;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = sensorEvent.values.clone();
            // Shake detection
            double x = mGravity[0];
            double y = mGravity[1];
            double z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = Math.sqrt(x * x + y * y + z * z);
            double delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            if (hitCount <= SAMPLE_SIZE) {
                hitCount++;
                hitSum += Math.abs(mAccel);
            } else {
                hitResult = hitSum / SAMPLE_SIZE;

                Log.d("TAG", String.valueOf(hitResult));

                if (hitResult > THRESHOLD) {

                    Log.d("TAG", "Walking");
                    textView.setText("Walking");

                } else {
                    Log.d("TAG", "Stop Walking");
                    textView.setText("Stop Walking");
                }
                hitCount = 0;
                hitSum = 0;
                hitResult = 0;
            }
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
