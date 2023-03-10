package com.example.pertemuan4_sensors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Layout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager mSensorManager;

    //Sensors
    private Sensor mSensorAmbientTemp;
    private Sensor mSensorProximity;
    private Sensor mSensorPressure;
    private Sensor mSensorLight;
    private Sensor mSensorHumidity;

    //Sensor Textviews
    private TextView mTextSensorAmbientTemp;
    private TextView mTextSensorProximity;
    private TextView mTextSensorPressure;
    private TextView mTextSensorLight;
    private TextView mTextSensorHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        //menyimpan data sensor di string builder
        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList){
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        mTextSensorAmbientTemp  = findViewById(R.id.label_ambient_temp);
        mTextSensorProximity    = findViewById(R.id.label_proximity);
        mTextSensorPressure     = findViewById(R.id.label_pressure);
        mTextSensorLight        = findViewById(R.id.label_light);
        mTextSensorHumidity     = findViewById(R.id.label_humidity);

        mSensorAmbientTemp = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);;
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        String sensor_error = "No sensor";
        if (mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null){
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorAmbientTemp == null){
            mTextSensorAmbientTemp.setText(sensor_error);
        }
        if (mSensorPressure == null){
            mTextSensorPressure.setText(sensor_error);
        }
        if (mSensorHumidity == null){
            mTextSensorHumidity.setText(sensor_error);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorProximity != null) {

            mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL));
        if (mSensorManager.registerListener(this, mSensorAmbientTemp, SensorManager.SENSOR_DELAY_NORMAL));
        if (mSensorManager.registerListener(this, mSensorPressure, SensorManager.SENSOR_DELAY_NORMAL));
        if (mSensorManager.registerListener(this, mSensorHumidity, SensorManager.SENSOR_DELAY_NORMAL));

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];
        ConstraintLayout layoutUtama = findViewById(R.id.layoutUtama);
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(String.format("Light Sensor : %1$.2f",currentValue));

                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(String.format("Proximity sensor : %1$.2f",currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorAmbientTemp.setText(String.format("Ambient_temp sensor : %1$.2f",currentValue));
                if (currentValue >= 70 && currentValue <= 100){
                    mTextSensorAmbientTemp.setBackgroundColor(Color.RED);
                } else{
                    mTextSensorAmbientTemp.setBackgroundColor(Color.WHITE);
                }
                break;
            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(String.format("Pressure sensor : %1$.2f",currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(String.format("Humidity sensor : %1$.2f",currentValue));
                break;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}