package com.example.obsalinetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp1,sp;
    EditText productID;
    Button Submit,logout;
    DatabaseReference df;
    FirebaseFirestore db1;
    private final String SHARED_PREF_NAME="userlogin";
    private final String SHARED_PREF_NAME1="iotdata";
//    private static final String SHARED_PREF_NAME="userlogin";
//    private static final String SHARED_PREF_NAME1="iotdata";
    private static final String Key_UserName="name";
    private static final String Key_Password="Password";
    private final String systemName="ivdID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initialization();
        ButtonClick();
    }

    private void ButtonClick() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sp.edit();
                editor.clear();
                editor.apply();
                Intent i=new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                SharedPreferences.Editor editor=sp1.edit();
                editor.putString("iotdata1",productID.getText().toString());
                editor.apply();
                Intent i=new Intent(getApplicationContext(), IotData.class);
                i.putExtra("ID",Integer.parseInt(productID.getText().toString()));
                startActivity(i);


            }
        });
    }

    private void Initialization() {
        logout=findViewById(R.id.logout);
        sp=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        sp1=getSharedPreferences(SHARED_PREF_NAME1,MODE_PRIVATE);
        Submit=findViewById(R.id.Submit);
        productID=findViewById(R.id.ProductID);
        int id=Integer.parseInt(sp1.getString("iotdata1","0"));
        if(id!=0)
        {
            Intent i=new Intent(MainActivity.this,IotData.class);
            i.putExtra("ID",id);
            startActivity(i);
        }
    }
}