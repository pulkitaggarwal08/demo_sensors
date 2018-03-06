package com.demo_sensors.pulkit.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demo_sensors.pulkit.R;

public class MusicAmbientLightSensorActivity extends AppCompatActivity implements SensorEventListener {

    private MediaPlayer mediaPlayer;
    private SensorManager senseManager;
    private Sensor sensor;

    private TextView tv_light_sensor;
    private Button start, pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_ambient_light_sensor);

        findIds();
        init();
    }

    private void findIds() {

        tv_light_sensor = (TextView) findViewById(R.id.tv_light_sensor);
        start = (Button) findViewById(R.id.button_start);
        pause = (Button) findViewById(R.id.button_pause);
    }

    private void init() {

        senseManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = senseManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.songs);
        } catch (Exception e) {
            Log.e("42", String.valueOf(e));
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float value = sensorEvent.values[0];

        if (value < (float) 200) {

            if (!mediaPlayer.isPlaying()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mediaPlayer.start();
                    }
                });
            }
        } else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }
        tv_light_sensor.setText("Light Sensor Reading:  " + sensorEvent.values[0]);
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

        senseManager.unregisterListener(this);
    }


}
