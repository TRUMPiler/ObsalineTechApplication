package com.example.obsalinetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class IotData extends AppCompatActivity {
TextView data;
int ID;
    FirebaseDatabase fd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_data);
        Intent i=getIntent();
        ID= i.getIntExtra("ID",0);
        Initialization();
    }

    private void Initialization()
    {

    }
}