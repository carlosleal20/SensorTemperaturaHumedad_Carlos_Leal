package com.example.sensortemperaturahumedad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView temperaturaTextView;//vistas de textos en las que mostrara los valores
    private TextView humedadTextView;
    private SensorManager sensorManager;//instancia que se utitliza para acceder a los sensores de temperatura y humedad

    private Sensor temperaturaSensor;//objetos tipo sensor que representa los sensores de temperatura y humedad
    private Sensor humedadSensor;
    private Boolean temperaturaDisponible;//indican los sensores  de temperatura y humedad estan disponibles
    private Boolean humedadDisponible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperaturaTextView=findViewById(R.id.Temp);
        humedadTextView=findViewById(R.id.Humed);

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);

        temperaturaDisponible=false;
        humedadDisponible=false;

        if (SensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) !=null){
            temperaturaSensor=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            temperaturaDisponible=true;
        }else {
            temperaturaTextView.setText("El sensor de temperatura no esta disponible");
        }

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        if (sensorEvent.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperaturaTextView.setText(sensorEvent.values[0] + "°C");
        } else if (SensorEvent.sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY) {
            humedadTextView.setText(sensorEvent.values[0] + "%");
            
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }
    @Override
    protected void onResume(){
        super.onResume();
        if(temperaturaDisponible){
            sensorManager.registerListener(this,temperaturaSensor,SensorManager.SENSOR_DELAY_NORMAL);

        }
        if(humedadDisponible){
            sensorManager.registerListener(this, humedadSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}