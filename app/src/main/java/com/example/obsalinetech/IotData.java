package com.example.obsalinetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class IotData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_data);
        Intent i=getIntent();
        int ID= i.getIntExtra("ID",0);
        Toast.makeText(this, ""+ID, Toast.LENGTH_LONG).show();

    }
}