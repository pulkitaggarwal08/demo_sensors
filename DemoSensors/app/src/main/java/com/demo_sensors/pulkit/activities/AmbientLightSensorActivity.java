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
import com.demo_sensors.pulkit.utils.HardwareObject;

import java.util.ArrayList;

public class AmbientLightSensorActivity extends AppCompatActivity implements SensorEventListener {

    public static final float LIGHT_SUNLIGHT_MAX = 120000.0f;
    public static final float LIGHT_SUNLIGHT = 110000.0f;
    public static final float LIGHT_SHADE = 20000.0f;
    public static final float LIGHT_OVERCAST = 10000.0f;
    public static final float LIGHT_SUNRISE = 400.0f;
    public static final float LIGHT_CLOUDY = 100.0f;
    public static final float LIGHT_FULLMOON = 0.25f;
    public static final float LIGHT_NO_MOON = 0.001f;

    SensorManager senseManager;
    private Sensor sensor;
    private TextView tv_light_sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient_light_sensor);

        findIds();
        init();
    }

    private void findIds() {

        tv_light_sensor = (TextView) findViewById(R.id.tv_light_sensor);
    }

    private void init() {

        senseManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = senseManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {

            String name = sensor.getName();
            String vendor = String.valueOf(sensor.getVendor());
            String version = String.valueOf(sensor.getVersion());
            String maxRange = String.valueOf(sensor.getMaximumRange());
            String minRange = String.valueOf(sensor.getMinDelay());
            String resolution = String.valueOf(sensor.getResolution());
            String power = String.valueOf(sensor.getPower());
            String lightSensorReading = String.valueOf(sensorEvent.values[0]);

            tv_light_sensor.setText(
                    "Name:                               " + name + "\n\n" +
                    "Vendor:                             " + vendor + "\n\n" +
                    "Version:                            " + version + "\n\n" +
                    "Max Range:                      " + maxRange + "\n\n" +
                    "Min Delay:                        " + minRange + "\n\n" +
                    "Resolution:                       " + resolution + "\n\n" +
                    "Power:                              " + power + "\n\n" +
                    "Light Sensor Reading:    " + lightSensorReading + "\n\n");

            /*Information about sunrise etc*/
//            String mode = getLightMode(sensorEvent.values[0]);
//            tv_light_sensor.setText(mode + " "+sensorEvent.values[0]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }

    @Override
    protected void onPause() {
        super.onPause();

        senseManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        senseManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private String getLightMode(float value) {

        if (value >= LIGHT_SUNLIGHT_MAX) {
            return "LIGHT_SUNLIGHT_MAX";
        } else if (value > LIGHT_SUNLIGHT) {
            return "LIGHT_SUNLIGHT";
        } else if (value > LIGHT_SHADE) {
            return "LIGHT_SHADE";
        } else if (value > LIGHT_OVERCAST) {
            return "LIGHT_OVERCAST";
        } else if (value > LIGHT_SUNRISE) {
            return "LIGHT_SUNRISE";
        } else if (value > LIGHT_CLOUDY) {
            return "LIGHT_CLOUDY";
        } else if (value > LIGHT_FULLMOON) {
            return "LIGHT_FULLMOON";
        } else if (value > LIGHT_NO_MOON) {
            return "LIGHT_NO_MOON";
        } else {
            return "123";
        }
    }

}
