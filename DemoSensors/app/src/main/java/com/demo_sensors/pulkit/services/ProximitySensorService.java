package com.demo_sensors.pulkit.services;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.demo_sensors.pulkit.MainActivity;
import com.demo_sensors.pulkit.sharedpreferences.SharedPreference;
import com.demo_sensors.pulkit.utils.ProximityApp;

import static java.lang.Thread.sleep;

public class ProximitySensorService extends Service implements SensorEventListener {

    private Sensor proximitySensor;
    private SensorManager sensorManager;
    private SharedPreferences preferences;

    private String TAG = "Sensor Services";

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG,"onBind");
//        Toast.makeText(getApplicationContext(), "onBind", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onCreate() {
        Log.v(TAG,"onCreate");
//        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.v(TAG,"onAccuracyChanged");
//        Toast.makeText(getApplicationContext(), "onAccuracyChanged", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        Log.v(TAG,"onSensorChanged");
//        Toast.makeText(getApplicationContext(), "onSensorChanged", Toast.LENGTH_SHORT).show();
        
        final long start = System.currentTimeMillis();

        preferences = getSharedPreferences(SharedPreference.SHARED_PREF_NAME, MODE_PRIVATE);
        final int timeout = Integer.parseInt(preferences.getString(SharedPreference.TIMEOUT_KEY, "300"));
        final float calibration = Float.parseFloat(preferences.getString(SharedPreference.CALIBRATION_KEY, "123.4"));

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (event.values[0] == calibration && System.currentTimeMillis() - start < timeout) {
                    try {
                        sleep(10L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (System.currentTimeMillis() - start >= timeout) {
                    DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
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
    }

    @Override
    public void onDestroy() {
        Log.v(TAG,"onDestroy");
//        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
        sensorManager.unregisterListener(this);

        stopService(new Intent(getApplicationContext(), ProximitySensorService.class));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG,"onStartCommand");
//        Toast.makeText(getApplicationContext(), "onStartCommand", Toast.LENGTH_SHORT).show();
        
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, this.proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

//        this.preferences = ProximityApp.getAppContext().getSharedPreferences(Strings.SHARED_PREF_NAME, MODE_PRIVATE);
//        boolean persistent = this.preferences.getBoolean(Strings.PERSISTENT_KEY, true);

//        if (persistent) {
//            Intent temp = new Intent(ProximitySensorService.this, MainActivity.class);
//            PendingIntent pIntent = PendingIntent.getActivity(ProximitySensorService.this, 0, temp, 0);
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                    .setSmallIcon(R.drawable.ic_notification)
//                    .setContentTitle("Proximity Lock")
//                    .setContentText("Touch proximity sensor to lock")
//                    .setAutoCancel(true)
//                    .setOngoing(true)
//                    .setContentIntent(pIntent);
//            Notification barNotif = builder.build();
//            this.startForeground(1, barNotif);
//        }
        return Service.START_STICKY;
    }
}
