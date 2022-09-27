package com.example.speedbreaker;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {


    TextView tv1,tv2,tv3;
    private SensorManager mSensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,sensor,mSensorManager.SENSOR_DELAY_UI);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tv1.setText("X ="+event.values[0]);
        tv2.setText("Y ="+event.values[1]);
        tv3.setText("Z ="+event.values[2]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}