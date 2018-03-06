package com.demo_sensors.pulkit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo_sensors.pulkit.activities.BallAccelerometerSensorActivity;
import com.demo_sensors.pulkit.activities.BasicAccelerometerSensorActivity;
import com.demo_sensors.pulkit.activities.AmbientLightSensorActivity;
import com.demo_sensors.pulkit.activities.MusicAmbientLightSensorActivity;
import com.demo_sensors.pulkit.activities.ScreenOffProximityActivity;
import com.demo_sensors.pulkit.activities.CarMovingAccelerometerSensorActivity;
import com.demo_sensors.pulkit.activities.ShakeMobileAccelerometerSensorActivity;

public class MainActivity extends AppCompatActivity {

    private static final int ACTIVATE_PROXIMITY_SENSOR = 1;

    private Context context;
    private String TAG = "mainActivity";

    Button btn_proximity_sensor, btn_basic_accelerometer_sensor, btn_accelerometer_sensor, btn_car_moving, btn_ambient_light_sensor,
            btn_music_ambient_light_sensor, btn_shake_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        init();
    }

    private void findIds() {
        btn_proximity_sensor = (Button) findViewById(R.id.btn_proximity_sensor);
        btn_basic_accelerometer_sensor = (Button) findViewById(R.id.btn_basic_accelerometer_sensor);
        btn_accelerometer_sensor = (Button) findViewById(R.id.btn_accelerometer_sensor);
        btn_car_moving = (Button) findViewById(R.id.btn_car_moving);
        btn_ambient_light_sensor = (Button) findViewById(R.id.btn_ambient_light_sensor);
        btn_music_ambient_light_sensor = (Button) findViewById(R.id.btn_music_ambient_light_sensor);
        btn_shake_mobile = (Button) findViewById(R.id.btn_shake_mobile);
    }

    private void init() {

        context = MainActivity.this;

        /*Screen OFF Proximity Sensor*/
        screenOffProximitySensor();

        /*basic demo on Accelerometer Sensor*/
        basicAccelerometerSensor();
        
        /*demo on moving ball using Accelerometer Sensor*/
        movingBallaccelerometerSensor();

        /*demo on moving car Accelerometer Sensor*/
        movingCarAccelerometerSensor();

         /*demo on shake mobile using Accelerometer sensor*/
        shakeMobileAccelerometerSensor();

        /*basic demo on Ambient Light Sensor Information*/
        ambientLightSensor();

        /*basic demo on play music through Ambient Light Sensor*/
        playMusicAmbientLightSensor();

    }

    private void screenOffProximitySensor() {

        /*Donot give background services before activate the proximity sensor.*/

        /*Screen Off Proximity Sensor*/
        btn_proximity_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Without Service direct screen off*/
                Toast.makeText(MainActivity.this, "screen off button", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(context, ScreenOffProximityActivity.class);
                startActivity(startIntent);
            }
        });
    }

    private void basicAccelerometerSensor() {

        /*Accelerometer Sensor*/
        btn_basic_accelerometer_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Without Service direct Accelerometer Sensor*/
                Toast.makeText(MainActivity.this, "basic accelerometer button", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(context, BasicAccelerometerSensorActivity.class);
                startActivity(startIntent);
            }
        });
    }

    private void movingBallaccelerometerSensor() {

        /*Moving ball Accelerometer Sensor*/
        btn_accelerometer_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*moving ball using accelerometer through animation*/
                Toast.makeText(MainActivity.this, "Ball Accelerometer button", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(context, BallAccelerometerSensorActivity.class);
                startActivity(startIntent);
            }
        });
    }

    private void movingCarAccelerometerSensor() {

         /*Moving car Accelerometer Sensor*/
        btn_car_moving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*moving ball using accelerometer*/
                Toast.makeText(MainActivity.this, "Car Moving Accelerometer button", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(context, CarMovingAccelerometerSensorActivity.class);
                startActivity(startIntent);
            }
        });
    }

    private void shakeMobileAccelerometerSensor() {

         /*shake mobile Accelerometer Sensor*/
        btn_shake_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*moving ball using accelerometer*/
                Toast.makeText(MainActivity.this, "Shake Mobile Accelerometer button", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(context, ShakeMobileAccelerometerSensorActivity.class);
                startActivity(startIntent);
            }
        });
    }


    private void ambientLightSensor() {

        btn_ambient_light_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Without Service direct Ambient Light Sensor*/
                Toast.makeText(MainActivity.this, "Ambient Light Sensor button", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(context, AmbientLightSensorActivity.class);
                startActivity(startIntent);
            }
        });
    }

    private void playMusicAmbientLightSensor() {

        btn_music_ambient_light_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Without Service direct Ambient Light Sensor*/
                Toast.makeText(MainActivity.this, "Ambient Light Sensor button", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(context, MusicAmbientLightSensorActivity.class);
                startActivity(startIntent);
            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (ACTIVATE_PROXIMITY_SENSOR == requestCode) {
//            super.onActivityResult(requestCode, resultCode, data);
//
//        }
//    }

}