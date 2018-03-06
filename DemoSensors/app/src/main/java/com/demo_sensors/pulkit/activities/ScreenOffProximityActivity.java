package com.demo_sensors.pulkit.activities;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo_sensors.pulkit.R;
import com.demo_sensors.pulkit.receiver.SensorAdminReceiver;
import com.demo_sensors.pulkit.services.ProximitySensorService;
import com.demo_sensors.pulkit.sharedpreferences.SharedPreference;

import static java.lang.Thread.sleep;

public class ScreenOffProximityActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager proximitySensorManager;
    private Sensor proximitySensor;

    private static final int SENSOR_SENSITIVITY = 5;
    private static final int ACTIVATE_PROXIMITY_SENSOR = 1;

    private SharedPreferences preferences;

    ComponentName deviceAdminSample;
    DevicePolicyManager devicePolicyManager;

    boolean admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        preferences = getSharedPreferences(SharedPreference.SHARED_PREF_NAME, MODE_PRIVATE);

        findIds();
        init();
    }

    private void findIds() {
    }

    private void init() {

        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        deviceAdminSample = new ComponentName(this, SensorAdminReceiver.class);

        admin = devicePolicyManager.isAdminActive(deviceAdminSample);

        if (!admin) {
            Intent adminIntent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            adminIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminSample);
            startActivityForResult(adminIntent, ACTIVATE_PROXIMITY_SENSOR);
        }

        proximitySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = proximitySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proximitySensorManager.registerListener(this, this.proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

//        this.enableButton = (Button) findViewById(R.id.enable);
//        if (this.preferences.getBoolean(SharedPreference.ENABLED_KEY, false)) {
//            this.enableButton.setText(getResources().getString(R.string.button_disable));
//            startService(new Intent(getApplicationContext(), ProximitySensorService.class));
//        } else {
//            this.enableButton.setText(getResources().getString(R.string.button_enable));
//            stopService(new Intent(getApplicationContext(), ProximitySensorService.class));
//        }

//        this.enableButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (preferences.getBoolean(SharedPreference.ENABLED_KEY, false)) {
//                    enableButton.setText(getResources().getString(R.string.button_enable));
//                    preferences.edit().putBoolean(SharedPreference.ENABLED_KEY, false).apply();
//                    stopService(new Intent(getApplicationContext(), ProximitySensorService.class));
//                } else {
//                    enableButton.setText(getResources().getString(R.string.button_disable));
//                    preferences.edit().putBoolean(SharedPreference.ENABLED_KEY, true).apply();
//                    startService(new Intent(getApplicationContext(), ProximitySensorService.class));
//                }
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (admin) {
            startService(new Intent(getApplicationContext(), ProximitySensorService.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        proximitySensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        proximitySensorManager.unregisterListener(this);

        stopService(new Intent(getApplicationContext(), ProximitySensorService.class));
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {

        final long start = System.currentTimeMillis();

        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values[0] >= -SENSOR_SENSITIVITY && sensorEvent.values[0] <= SENSOR_SENSITIVITY) {
                //near

                preferences = getSharedPreferences(SharedPreference.SHARED_PREF_NAME, MODE_PRIVATE);
                final int timeout = Integer.parseInt(preferences.getString(SharedPreference.TIMEOUT_KEY, "300"));
                final float calibration = Float.parseFloat(preferences.getString(SharedPreference.CALIBRATION_KEY, "123.4"));

                final Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (sensorEvent.values[0] == calibration && System.currentTimeMillis() - start < timeout) {
                            try {
                                Thread.sleep(10L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (System.currentTimeMillis() - start >= timeout) {
                            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                            devicePolicyManager.lockNow();
                        }
                    }
                });

                try {
                    sleep(900);
                    t.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                //far
                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (ACTIVATE_PROXIMITY_SENSOR == requestCode) {
            super.onActivityResult(requestCode, resultCode, data);
            finish();
        }
    }

}
