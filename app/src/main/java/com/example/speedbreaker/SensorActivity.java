package com.example.speedbreaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {


    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    private Sensor accelerometer,mGyro,mMagno;
    TextView xValue,yValue,zValue,xGyroValue,yGyroValue,zGyroValue,xMagnoValue,yMagnoValue,zMagnoValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        xValue = (TextView) findViewById(R.id.xValue);
        yValue =  (TextView)findViewById(R.id.yValue);
        zValue =  (TextView)findViewById(R.id.zValue);

        xGyroValue = (TextView) findViewById(R.id.xGyroValue);
        yGyroValue =  (TextView)findViewById(R.id.yGyroValue);
        zGyroValue =  (TextView)findViewById(R.id.zGyroValue);

        xMagnoValue = (TextView) findViewById(R.id.xMagnoValue);
        yMagnoValue =  (TextView)findViewById(R.id.yMagnoValue);
        zMagnoValue =  (TextView)findViewById(R.id.zMagnoValue);


        Log.d(TAG, "oncreate: Intializing sensor services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer!=null) {
            sensorManager.registerListener(SensorActivity.this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            Log.d(TAG, "oncreate: Registered accelerometer Listener");
        }else {
            xValue.setText("Accelerometer Not Supported");
            yValue.setText("Accelerometer Not Supported");
            zValue.setText("Accelerometer Not Supported");
        }



        mGyro=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(mGyro!=null) {
            sensorManager.registerListener(SensorActivity.this, mGyro, SensorManager.SENSOR_DELAY_UI);
            Log.d(TAG, "oncreate: Registered gyro Listener");
        }else {
            xGyroValue.setText("Gyroscope Not Supported");
            yGyroValue.setText("Gyroscope Not Supported");
            zGyroValue.setText("Gyroscope Not Supported");
        }


        mMagno=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(mMagno!=null) {
            sensorManager.registerListener(SensorActivity.this, mMagno, SensorManager.SENSOR_DELAY_UI);
            Log.d(TAG, "oncreate: Registered Magno Listener");
        }else {
            xMagnoValue.setText("Magno Not Supported");
            yMagnoValue.setText("Magno Not Supported");
            zMagnoValue.setText("Magno Not Supported");
        }

    }
//https://stackoverflow.com/questions/49011924/round-double-to-1-decimal-place-kotlin-from-0-044999-to-0-1
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d(TAG, "onSensorChanged: X: " + sensorEvent.values[0] + "Y: " + sensorEvent.values[1] + "Z: " + sensorEvent.values[2]);
            xValue.setText("Axvalue =" + String.format("%.2f", sensorEvent.values[0]));
            yValue.setText("Ayvalue =" + String.format("%.2f", sensorEvent.values[0]));
            zValue.setText("Azvalue =" + String.format("%.2f", sensorEvent.values[0]));


           if(sensorEvent.values[0]>= 1 ){
                    xValue.setBackgroundColor(Color.RED);
            }
           else if(sensorEvent.values[0]< 0){ xValue.setBackgroundColor(Color.GREEN);
            }
           else if(sensorEvent.values[1]>= 10 ){
               yValue.setBackgroundColor(Color.RED);
           }
           else if(sensorEvent.values[1]<9){ yValue.setBackgroundColor(Color.YELLOW);
           }
           else if(sensorEvent.values[2]>= 5){
               zValue.setBackgroundColor(Color.RED);
           }
           else if(sensorEvent.values[2]< 5) {
               zValue.setBackgroundColor(Color.CYAN);
           }


        }else if (sensor.getType()==Sensor.TYPE_GYROSCOPE){
            xGyroValue.setText("xGvalue =" + String.format("%.2f", sensorEvent.values[0]));
            yGyroValue.setText("yGvalue =" + String.format("%.2f", sensorEvent.values[1]));
            zGyroValue.setText("zGvalue =" + String.format("%.2f", sensorEvent.values[2]));

            if(sensorEvent.values[0]>= 1 ){
                xGyroValue.setBackgroundColor(Color.RED);
            }
            else if(sensorEvent.values[0]< 0){ xGyroValue.setBackgroundColor(Color.GREEN);
            }
            else if(sensorEvent.values[1]>= 10 ){
                yGyroValue.setBackgroundColor(Color.RED);
            }
            else if(sensorEvent.values[1]<9){ yGyroValue.setBackgroundColor(Color.YELLOW);
            }
            else if(sensorEvent.values[2]>= 5){
                zGyroValue.setBackgroundColor(Color.RED);
            }
            else if(sensorEvent.values[2]< 5) {
                zGyroValue.setBackgroundColor(Color.CYAN);
            }

        }else if (sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            xMagnoValue.setText("xMvalue =" + String.format("%.2f", sensorEvent.values[0]));
            yMagnoValue.setText("yMvalue =" + String.format("%.2f", sensorEvent.values[1]));
            zMagnoValue.setText("zMvalue =" + String.format("%.2f", sensorEvent.values[2]));


            if(sensorEvent.values[0]>= 1 ){
                xMagnoValue.setBackgroundColor(Color.RED);
            }
            else if(sensorEvent.values[0]< 0){ xMagnoValue.setBackgroundColor(Color.GREEN);
            }
            else if(sensorEvent.values[1]>= 10 ){
                yMagnoValue.setBackgroundColor(Color.RED);
            }
            else if(sensorEvent.values[1]<9){ yMagnoValue.setBackgroundColor(Color.YELLOW);
            }
            else if(sensorEvent.values[2]>= 5){
                zMagnoValue.setBackgroundColor(Color.RED);
            }
            else if(sensorEvent.values[2]< 5) {
                zMagnoValue.setBackgroundColor(Color.CYAN);
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}