package com.example.behaviourmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaScannerConnection;
import android.os.Environment;
import android.widget.TextView;
import android.os.Bundle;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import java.util.Calendar;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private double ax = 4.0;
    private TextView mAxValue, mAyValue, mAzValue;
    private SensorManager sensorManager;
    private String fileName = "test.txt";
    private int daqPeriod = 100000; // us

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mAxValue = findViewById(R.id.ax_value);
        mAyValue = findViewById(R.id.ay_value);
        mAzValue = findViewById(R.id.az_value);
        // TYPE_GYROSCOPE and Sensor.TYPE_LINEAR_ACCELERATION cannot be used!
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accel, daqPeriod);

        Log.i("MyLog", "onCreate called");
    }

    @Override
    protected void onResume(){
        Log.i("MyLog", "onResume called");
        super.onResume();
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onPause(){
        Log.i("MyLog", "onPause called");
        super.onPause();
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        double ax, ay, az;
        long time;
        Log.i("MyLog", "onSensorChanged called");
        ax = event.values[0];
        ay = event.values[1];
        az = event.values[2];
        time = System.currentTimeMillis();
        mAxValue.setText(String.format("%.3f", ax));
        mAyValue.setText(String.format("%.3f", ay));
        mAzValue.setText(String.format("%.3f", az));
        Log.i("MyLog", String.format("%d,%.3f,%.3f,%.3f\n", time, ax, ay, az));
        saveFile(String.format("%d,%.3f,%.3f,%.3f\n", time, ax, ay, az));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    public void saveFile(String str) {
        try {
            Log.i("MyLog", "SaveFile called");
            Calendar cal = Calendar.getInstance();
            String file = String.format("%d%02d%02d.csv", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
            FileOutputStream fileOutputStream = openFileOutput(file, Context.MODE_APPEND);
            fileOutputStream.write(str.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MyLog", "IOException Error");
        }
    }
}
