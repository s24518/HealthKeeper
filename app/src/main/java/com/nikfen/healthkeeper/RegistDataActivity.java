package com.nikfen.healthkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;


public class RegistDataActivity extends AppCompatActivity {

    final String SAVED_NAME = "saved_name";
    final String SAVED_STANDART = "saved_standart";

    SharedPreferences sPref;
    Spinner spinner;
    TextView textRes;
    EditText editName;
    EditText editWeight;
    int counter = 0;
    int weight;
    int standrt;
    private String[] genders = {"Male", "Female"};
    String name, weightString;
    LinearLayout buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_data);

        textRes = (TextView) findViewById(R.id.extract);
        buttonSave = (LinearLayout) findViewById(R.id.buttonSave);

        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genders);
        gendersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spGenders = (Spinner) findViewById(R.id.switch_sex);
        spGenders.setAdapter(gendersAdapter);

        spGenders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        counter = 35;
                        break;
                    case 1:
                        counter = 31;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        editName = (EditText) findViewById(R.id.editText3);
        editWeight = (EditText) findViewById(R.id.imputweigh);

    }

    public void pressButtonCount(View view) {
        if (editWeight.getText().toString().length() != 0) {
            name = editName.getText().toString();
            weight = Integer.parseInt(editWeight.getText().toString());
            if (weight < 0) {
                weight *= -1;
            }
            standrt = weight * counter;

            textRes.setText("You have to drink " + standrt + "ml");
            buttonSave.setVisibility(View.VISIBLE);
        }
        else Toast.makeText(this, "Input data!", Toast.LENGTH_SHORT).show();
    }
    public void pressButtonSave(View view) {
        saveData();
    }

    private void saveData() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(SAVED_NAME, name);
        editor.putInt(SAVED_STANDART, standrt);
        editor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegistDataActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

