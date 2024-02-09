package com.example.obsalinetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    EditText productID;
    Button Submit;
    DatabaseReference df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initialization();
        ButtonClick();
    }

    private void ButtonClick() {
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), IotData.class);
                i.putExtra("ID",Integer.parseInt(productID.getText().toString()));
                startActivity(i);
            }
        });
    }

    private void Initialization() {
        Submit=findViewById(R.id.Submit);
        productID=findViewById(R.id.ProductID);
    }
}