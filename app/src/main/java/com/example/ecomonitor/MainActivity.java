package com.example.ecomonitor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private boolean isCharging = false;
    private int batteryLevel = 0;
    private TextView NivelBateria;
    private ProgressBar bateriaProgressBar;
    private Button cargarBateria;
    private Button descargarBateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        NivelBateria = findViewById(R.id.textView2);
        bateriaProgressBar = findViewById(R.id.progressBar);
        cargarBateria = findViewById(R.id.button);
        descargarBateria = findViewById(R.id.button2);

        cargarBateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarSolarIncial();
            }
        });

        descargarBateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarBateriaFinal();
            }
        });

    }
    private void cargarBateriaFinal() {
        isCharging = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isCharging && batteryLevel > 0){
                    batteryLevel -=5;

                    if(batteryLevel<0){
                        batteryLevel = 0;
                    }
                    actualizarNivelBateria();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    private void cargarSolarIncial() {
        isCharging= true;

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(isCharging && batteryLevel < 100){
                    batteryLevel +=5;

                    if(batteryLevel > 100){
                        batteryLevel = 100;
                    }
                    actualizarNivelBateria();

                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }
    private void actualizarNivelBateria() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NivelBateria.setText("Nivel de Bateria: " + batteryLevel + "%");
                bateriaProgressBar.setProgress(batteryLevel);
            }
        });
    }
}