package com.example.obsalinetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class IotData extends AppCompatActivity {
TextView data;
FirebaseFirestore fb;
Button Submit,Forget,Transfer;
int ID;
double Store;
   DatabaseReference databaseReference;
    SharedPreferences sp;
    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_data);
        Intent i=getIntent();
        ID= i.getIntExtra("ID",0);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU)
        {
            if(ContextCompat.checkSelfPermission(IotData.this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(IotData.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }
        Initialization();
        databaseReference.child("/Firebase22").child("/ivd-"+ID+"/weight").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String Text=snapshot.getValue().toString();
                data.setText(""+Text);
//                Toast.makeText(getApplicationContext(), ""+(Double.parseDouble(Text))/Store*100, Toast.LENGTH_LONG).show();
                Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Store=Double.parseDouble(Text);
                    }

                });
                Transfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(IotData.this,Transfer.class);
                        startActivity(i);
                    }
                });
                Forget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        SharedPreferences.Editor editor =sp.edit();
                        editor.clear();
                        editor.commit();
                        Intent i=new Intent(IotData.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                if((Double.parseDouble(Text))/Store*100<=13&&(Double.parseDouble(Text))/Store*100>=10)
                {
                    Toast.makeText(getApplicationContext(), "10% reached", Toast.LENGTH_LONG).show();
                    Notification("bottle at Critical level","IV has reached at 10%");
                }
                if((Double.parseDouble(Text))/Store*100<=23&&(Double.parseDouble(Text))/Store*100>=20)
                {
                    Toast.makeText(getApplicationContext(), "20% reached", Toast.LENGTH_LONG).show();
                    Notification("bottle at Critical level","IV has reached at `20%");
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
        Transfer=findViewById(R.id.Transfer);
        fb=FirebaseFirestore.getInstance();
        sp=getSharedPreferences("iotdata",MODE_PRIVATE);
        sp1=getSharedPreferences("userlogin",MODE_PRIVATE);
        Store=0.0;
        Submit=findViewById(R.id.Submit);
        Forget=findViewById(R.id.Forget);
        data=findViewById(R.id.lblData);
        databaseReference = FirebaseDatabase.getInstance("https://ivd-1-f15ba-default-rtdb.firebaseio.com/").getReference();
        fb.collection("users").document(sp1.getString("name","Naishal")).update("ivd",String.valueOf(ID));
        Activate();
    }

    private void Activate() {
        databaseReference.child("/Firebase22").child("/ivd-"+ID+"/activate").setValue(true);
    }

    public void Notification(String Title,String Text)
    {
        String Channelid="CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),Channelid);
        builder.setSmallIcon(R.drawable.notificationbell);
        builder.setContentTitle(Title);
        builder.setContentText(Text);
        builder.setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent i=new Intent(getApplicationContext(),NotificationPacket.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("data",Text);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,i,PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pi);
        NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc=nm.getNotificationChannel(Channelid);
            if(nc==null)
            {
                int importance=NotificationManager.IMPORTANCE_HIGH;
                nc=new NotificationChannel(Channelid,"GG",importance);
                nc.setLightColor(Color.RED);
                nc.enableVibration(true);
                nm.createNotificationChannel(nc);
            }
        }
    nm.notify(0,builder.build());
    }
}