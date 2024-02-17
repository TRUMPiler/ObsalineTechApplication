package com.example.obsalinetech;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.TrafficStats;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Transfer extends AppCompatActivity {
SharedPreferences sp,sp1;
FirebaseFirestore fb;
EditText TransferID;
Button TransferButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Intialization();
        ButtonClick();
    }

    private void ButtonClick()
    {
        TransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                fb.collection("users").document(sp.getString("name","Naishal")).update("ivd",String.valueOf(0));
                fb.collection("users").document(TransferID.getText().toString()).update("ivd",String.valueOf(sp1.getString("iotdata1","null"))).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Transfer Completed ",Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor=sp1.edit();
                        editor.clear();
                        editor.apply();
                        Intent i=new Intent(Transfer.this,MainActivity.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Transfer Failed",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    private void Intialization()
    {
       sp=getSharedPreferences("userlogin",MODE_PRIVATE);
       sp1=getSharedPreferences("iotdata",MODE_PRIVATE);
       fb=FirebaseFirestore.getInstance();
       TransferID=findViewById(R.id.TransferGG);
       TransferButton=findViewById(R.id.Transferbtn);
    }
}