package com.example.backgroundserviceneverend;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Intent mServiceIntent;
    private SensorService sensorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorService = new SensorService(this);

        mServiceIntent = new Intent(this, sensorService.getClass());
        startService(mServiceIntent);

    }

}
