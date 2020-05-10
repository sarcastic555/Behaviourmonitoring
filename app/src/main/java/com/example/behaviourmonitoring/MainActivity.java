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
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private double ax = 4.0;
    private TextView mAxValue, mAyValue, mAzValue;
    private SensorManager sensorManager;
    private String fileName = "test.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mAxValue = findViewById(R.id.ax_value);
        mAyValue = findViewById(R.id.ay_value);
        mAzValue = findViewById(R.id.az_value);

        Log.i("MyLog", "onCreate called");
    }

    @Override
    protected void onResume(){
        Log.i("MyLog", "onResume called");
        super.onResume();
        //List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        //for ( Sensor s : sensors){
        //    sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        //}
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
        //sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);
        //sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause(){
        Log.i("MyLog", "onPause called");
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        double ax, ay, az;
        Log.i("MyLog", "onSensorChanged called");
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];
            mAxValue.setText(String.format("%.3f", ax));
            mAyValue.setText(String.format("%.3f", ay));
            mAzValue.setText(String.format("%.3f", az));
            saveFile(fileName, "onCreate\n");
            Log.i("MyLog", String.format("%.5f, %.5f, %.5f", ax, ay, az));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    public void saveFile(String file, String str) {
        try {
            Log.i("MyLog", "Save0");
            //FileOutputStream fileOutputStream = openFileOutput(file, Context.MODE_WORLD_READABLE);
            //FileOutputStream fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE);
            FileOutputStream fileOutputStream = openFileOutput(file, Context.MODE_APPEND);
            //FileOutputStream fileOutputStream = new FileOutputStream("/data/data/test.txt");
            // FileOutputStream fileOutputStream = openFileOutput("/data/data/test.txt", Context.MODE_PRIVATE);
            //MediaScannerConnection.scanFile(this, new String[]{getFilesDir()+"/"+file}, null, null);
            fileOutputStream.write(str.getBytes());
            //fileOutputStream.flush();
            fileOutputStream.close();
            Log.i("MyLog", "Save");
            //getFilesDir().mkdirs();
            Log.i("MyLog", getFilesDir().toString());
            Log.i("MyLog", getFilesDir().getPath());
            //Log.i("MyLog", getExternal.toString());
            //Log.i("MyLog", getExternalFilesDir().getPath());
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("MyLog", "Error");
        }
    }
}
