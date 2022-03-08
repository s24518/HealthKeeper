package com.nikfen.healthkeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    public void goToStandart(View view) {
        Intent intent = new Intent(this, RegistDataActivity.class);
        startActivity(intent);
    }

    public void goToNotification(View view) {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);

            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);

    }
}

