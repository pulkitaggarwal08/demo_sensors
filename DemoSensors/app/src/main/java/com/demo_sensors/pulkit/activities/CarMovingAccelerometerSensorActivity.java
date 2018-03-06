package com.demo_sensors.pulkit.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.demo_sensors.pulkit.R;

public class CarMovingAccelerometerSensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    public long lastUpdate;

    private ImageView image_view;
    public static int x = 150;
    public static int y = 650;

    boolean isColorBlue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_moving);

        findIds();
        init();
    }

    private void findIds() {

        image_view = (ImageView) findViewById(R.id.image_view);
    }

    private void init() {

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();

        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isColorBlue) {

                    image_view.setImageResource(R.drawable.car1);
                    isColorBlue = false;
                } else {

                    image_view.setImageResource(R.drawable.car2);
                    isColorBlue = true;
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x = x - (int) event.values[0];
            y = y + (int) event.values[1];

            image_view.setY(y);
            image_view.setX(x);

        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}



