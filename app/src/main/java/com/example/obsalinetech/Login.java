package com.example.obsalinetech;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

public class Login extends AppCompatActivity
{
    FirebaseFirestore db;
    EditText login;
    EditText Password;
    Button btn;
    SharedPreferences sp,sp1;
    private static final String SHARED_PREF_NAME="userlogin";
    private static final String SHARED_PREF_NAME1="iotdata";
    private static final String Key_UserName="name";
    private static final String Key_Password="Password";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intial();
        ButtonClick();
    }

    private void ButtonClick()
    {
     btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v)
         {
            db.collection("users").document(login.getText().toString()).
                    get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                           DocumentSnapshot document=task.getResult();
                            if(document.exists())
                            {
                                String password=document.getString("password");
                                int id=Integer.parseInt(document.getString("ivd"));
                                if(id==0&&password.equals(Password.getText().toString()))
                                {
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString(Key_UserName,login.getText().toString());
                                    editor.putString(Key_Password,Password.getText().toString());
                                    editor.apply();
                                    Intent i=new Intent(Login.this,MainActivity.class);
                                    startActivity(i);
                                }
                                else if(id!=0&&password.equals(Password.getText().toString()))
                                {
                                    SharedPreferences.Editor editor1=sp1.edit();
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString(Key_UserName,login.getText().toString());
                                    editor.putString(Key_Password,Password.getText().toString());
                                    editor.apply();
                                    editor1.putString("iotdata1",String.valueOf(id));
                                    editor1.apply();
                                    Intent i=new Intent(Login.this,IotData.class);
                                    i.putExtra("ID",id);
                                    startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"User not Matches",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"User Not Found",Toast.LENGTH_LONG).show();
                        }
                    });
         }
     });

    }

    private void intial() {
        sp1=getSharedPreferences(SHARED_PREF_NAME1,MODE_PRIVATE);
        sp=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String Name=sp.getString(Key_UserName,null);
        if(Name!=null)
        {
            Intent i=new Intent(Login.this,MainActivity.class);
            startActivity(i);
        }
        btn=findViewById(R.id.btnLogin);
        login=findViewById(R.id.LoginPhoneNo);
        Password=findViewById(R.id.LoginPassword);

        db=FirebaseFirestore.getInstance();
    }
}