package com.example.speedbreaker;

import androidx.room.Ignore;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "provider")
public class Provider {
    @PrimaryKey(autoGenerate = true)
    int id;
    String latlag, location, accuracy,speed,gyroscope,accelerometer,timestamp,magnetometer;

    @Ignore
    public Provider( String latlag, String location, String accuracy, String speed, String gyroscope, String accelerometer, String timestamp, String magnetometer) {
        this.latlag = latlag;
        this.location = location;
        this.accuracy = accuracy;
        this.speed = speed;
        this.gyroscope = gyroscope;
        this.accelerometer = accelerometer;
        this.timestamp = timestamp;
        this.magnetometer = magnetometer;
    }

    public Provider(int id, String latlag, String location, String accuracy, String speed, String gyroscope, String accelerometer, String timestamp, String magnetometer) {
        this.id = id;
        this.latlag = latlag;
        this.location = location;
        this.accuracy = accuracy;
        this.speed = speed;
        this.gyroscope = gyroscope;
        this.accelerometer = accelerometer;
        this.timestamp = timestamp;
        this.magnetometer = magnetometer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatlag() {
        return latlag;
    }

    public void setLatlag(String latlag) {
        this.latlag = latlag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getGyroscope() {
        return gyroscope;
    }

    public void setGyroscope(String gyroscope) {
        this.gyroscope = gyroscope;
    }

    public String getAccelerometer() {
        return accelerometer;
    }

    public void setAccelerometer(String accelerometer) {
        this.accelerometer = accelerometer;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMagnetometer() {
        return magnetometer;
    }

    public void setMagnetometer(String magnetometer) {
        this.magnetometer = magnetometer;
    }
}