package com.nikfen.healthkeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;



public class NotificationActivity extends AppCompatActivity {

    TimePicker timePicker;
    Switch onNotification;
    TextView textView;

    SharedPreferences sPref;

    long latency;

    final String SAVED_MINUTE = "saved_minute";
    final String SAVED_HOUR = "saved_hour";
    final String SAVED_LATENCY = "saved_latency";
    final String SAVED_NOTIFICATION_SETTINGS = "saved_notification_settings";
    final int MINUT_COEFICIENT = 60000;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification);
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);


        onNotification = (Switch) findViewById(R.id.switch_notification);
        timePicker = (TimePicker)  findViewById(R.id.datePicker1);
        textView = (TextView) findViewById(R.id.textView);

        timePicker.setIs24HourView(true);
        timePicker.setHour(sPref.getInt(SAVED_HOUR, 2));
        timePicker.setMinute(sPref.getInt(SAVED_MINUTE, 0));



        latency = sPref.getLong(SAVED_LATENCY, 3600000);
        onNotification.setChecked(sPref.getBoolean(SAVED_NOTIFICATION_SETTINGS, false));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = sPref.edit();
        latency = (timePicker.getMinute()* MINUT_COEFICIENT) + (timePicker.getHour()*60)*MINUT_COEFICIENT;
        editor.putInt(SAVED_HOUR, timePicker.getHour());
        editor.putInt(SAVED_MINUTE, timePicker.getMinute());
        editor.putBoolean(SAVED_NOTIFICATION_SETTINGS, onNotification.isChecked());
        editor.putLong(SAVED_LATENCY, latency);
        editor.apply();
        Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
