package com.example.obsalinetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IotData extends AppCompatActivity {
TextView data;
int ID;
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
                if(Text.equals("20"))
                {
//                    Intent i=new Intent(this,)
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Initialization()
    {
        data=findViewById(R.id.lblData);
        databaseReference = FirebaseDatabase.getInstance("https://ivd-1-f15ba-default-rtdb.firebaseio.com/").getReference();

    }
}