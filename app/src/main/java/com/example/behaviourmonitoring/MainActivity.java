package com.example.behaviourmonitoring;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private double ax = 4.0;
    private TextView mAxValue, mAyValue, mAzValue;
    private SensorManager sensorManager;

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

            Log.i("MyLog", String.format("%.5f, %.5f, %.5f", ax, ay, az));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }
}
