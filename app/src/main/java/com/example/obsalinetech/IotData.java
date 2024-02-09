package com.example.obsalinetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IotData extends AppCompatActivity {
TextView data;
Button Submit;
int ID;
double Store;
   DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_data);
        Intent i=getIntent();
        ID= i.getIntExtra("ID",0);
        Initialization();
        databaseReference.child("/Firebase22").child("/ivd-"+ID+"/weight").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String Text=snapshot.getValue().toString();
                data.setText(""+Text);
                Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Store=Double.parseDouble(Text);
                    }
                });
                if((Double.parseDouble(Text))/Store*100==90)
                {
                    Toast.makeText(getApplicationContext(), "90% reached", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(getApplicationContext(),"Data fetching Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Initialization()
    {
        Store=0.0;
        Submit=findViewById(R.id.Submit);
        data=findViewById(R.id.lblData);
        databaseReference = FirebaseDatabase.getInstance("https://ivd-1-f15ba-default-rtdb.firebaseio.com/").getReference();

    }
}