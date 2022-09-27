package com.example.speedbreaker;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DatabaseActivity extends AppCompatActivity {

    EditText id, latlag, location, accuracy, speed, gyroscope, accelerometer, timestamp, magnetometer;
    Button button;
    Intent intent;
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());

    }


    private void initViews() {
        id = findViewById(R.id.edit_id);
        latlag = findViewById(R.id.edit_latlng);
        location = findViewById(R.id.edit_bearing);
        accuracy = findViewById(R.id.edit_accuracy);
        speed = findViewById(R.id.edit_speed);
        gyroscope = findViewById(R.id.edit_gyroscope);
        accelerometer = findViewById(R.id.edit_accelerometer);
        timestamp = findViewById(R.id.edit_timestamp);
        magnetometer = findViewById(R.id.edit_magnetometer);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }


    public void onSaveButtonClicked() {
        final Provider provider = new Provider(
                latlag.getText().toString(),
                location.getText().toString(),
                accuracy.getText().toString(),
                speed.getText().toString(),
                gyroscope.getText().toString(),
                accelerometer.getText().toString(),
                timestamp.getText().toString(),
                magnetometer.getText().toString());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.providerDao().insertProvider(provider);
            }
        });

    }

}


