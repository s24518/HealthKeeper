package com.nikfen.healthkeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FirstOpenActivity extends AppCompatActivity {

    Button button;
    SharedPreferences sPref;

    final String SAVED_NAME = "saved_name";
    final String SAVED_REST = "saved_rest";
    final String SAVED_STANDART = "saved_standart";
    final String SAVED_LEVEL = "saved_level";
    final String SAVED_LAST_TIME_LOGIN = "saved_last_time_login";
    final String SAVED_LAST_TIME_DRUNK = "saved_last_time_DRUNK";
    int standart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_open_activity);

        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        String savedName = sPref.getString(SAVED_NAME, "");



        standart = sPref.getInt(SAVED_STANDART, 0);

        Date cuttentDate = new Date(System.currentTimeMillis());

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        String formatedCurrentDate = format.format(cuttentDate);


        String last_time_login = sPref.getString(SAVED_LAST_TIME_LOGIN, "");

        if(last_time_login == ""){
            last_time_login = format.format(cuttentDate);
            editor.putInt(SAVED_STANDART, standart);
            editor.apply();
        }

        if (!last_time_login.equals(formatedCurrentDate)) {
            editor.putFloat(SAVED_REST, standart);
            editor.putFloat(SAVED_LEVEL, 0);
            editor.putString(SAVED_LAST_TIME_DRUNK, "");
            editor.apply();
        }





        if (savedName != null && standart != 0) {
            Intent intent = new Intent(FirstOpenActivity.this, MainActivity.class);
            startActivity(intent);
        }


    }

    public void pressButtonStart(View view) {
        Intent intent = new Intent(FirstOpenActivity.this, RegistDataActivity.class);
        startActivity(intent);
    }


}