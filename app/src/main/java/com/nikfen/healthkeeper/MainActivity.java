package com.nikfen.healthkeeper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final String SAVED_NAME = "saved_name";
    final String SAVED_STANDART = "saved_standart";
    final String SAVED_REST = "saved_rest";
    final String SAVED_LEVEL = "saved_level";
    final String SAVED_LAST_TIME_LOGIN = "saved_last_time_login";
    final String SAVED_NOTIFICATION_SETTINGS = "saved_notification_settings";
    final String SAVED_LATENCY = "saved_latency";
    final String SAVED_LAST_TIME_DRUNK = "saved_last_time_DRUNK";


    SharedPreferences sPref;
    EditText quantity;
    TextView also_needed, time, last_time;
    Button btn_save;
    String savedName;
    View bottle;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    String last_time_drunk_string;

    float last_quantity_drunk, rest_standart, level;
    int standart;
    long latency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantity = (EditText) findViewById(R.id.much_drunk);
        btn_save = (Button) findViewById(R.id.btn_save);
        also_needed = (TextView) findViewById(R.id.also_needed);
        bottle = (View) findViewById(R.id.bottle);
        time = (TextView) findViewById(R.id.time);
        last_time = (TextView) findViewById(R.id.last_time);


        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);

        last_time_drunk_string = sPref.getString(SAVED_LAST_TIME_DRUNK, "");
        if (last_time_drunk_string == "") {
            last_time.setVisibility(View.INVISIBLE);
        }
        time.setText(last_time_drunk_string);
        savedName = sPref.getString(SAVED_NAME, "");
        standart = sPref.getInt(SAVED_STANDART, 0);
        rest_standart = sPref.getFloat(SAVED_REST, standart);
        level = sPref.getFloat(SAVED_LEVEL, level);
        latency = sPref.getLong(SAVED_LATENCY, 3600000);


        bottle.getBackground().setLevel((int) level);

        also_needed.setText((int) rest_standart + " ml");
        if (rest_standart <= 0) {
            rest_standart = 0;
            also_needed.setText("You drank all the water you need");
            also_needed.setTextSize(20);
        }


    }


    public void now_drink(View view) {
        quantity.setVisibility(View.VISIBLE);
        btn_save.setVisibility(View.VISIBLE);
    }

    public void save_drunk(View view) {
        if (quantity.getText().toString().length() != 0) {
            last_quantity_drunk = Integer.parseInt(quantity.getText().toString());
            if (last_quantity_drunk < 0) last_quantity_drunk *= -1;
            if (rest_standart == standart) {
                rest_standart = standart - last_quantity_drunk;

            } else {
                rest_standart = rest_standart - last_quantity_drunk;
            }
            level = (10000 - ((rest_standart / standart) * 10000));

            bottle.getBackground().setLevel((int) level);


            also_needed.setText(String.valueOf((int) rest_standart) + " ml");

            if (rest_standart <= 0) {
                rest_standart = 0;
                also_needed.setText("You drank all the water you need!");
                also_needed.setTextSize(20);
            }

            sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sPref.edit();
            editor.putFloat(SAVED_REST, (float) rest_standart);
            editor.putFloat(SAVED_LEVEL, (float) level);


            Date last_time_drunk = new Date(System.currentTimeMillis());
            DateFormat format = new SimpleDateFormat("hh:mm");
            time.setText(format.format(last_time_drunk));
            editor.putString(SAVED_LAST_TIME_DRUNK, format.format(last_time_drunk));
            last_time.setVisibility(View.VISIBLE);
            editor.apply();

            quantity.setVisibility(View.INVISIBLE);
            btn_save.setVisibility(View.INVISIBLE);

        } else Toast.makeText(this, "Input data!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

    }

    public void notification(View view) {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        startActivity(intent);


    }

    public void onPause() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        Date cuttentDateForDestroy = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        editor.putString(SAVED_LAST_TIME_LOGIN, format.format(cuttentDateForDestroy));
        editor.apply();
        if (sPref.getBoolean(SAVED_NOTIFICATION_SETTINGS, true) == true && sPref.getFloat(SAVED_REST, 0) > 0) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent notification_intent = new Intent(getApplicationContext(), AlarmReciver.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, notification_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, cuttentDateForDestroy.getTime() + latency, pendingIntent);
        }
        super.onPause();
    }

    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
