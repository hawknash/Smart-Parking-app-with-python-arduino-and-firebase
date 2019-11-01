package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class slotActivity extends AppCompatActivity {
    private TextView m,m1;
    private Button exit;
    long time;
     int message;
     DatabaseReference mDatabaseReference,r;
     FirebaseDatabase mDatabase,mD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_slot);
        m=(TextView)findViewById(R.id.textView4) ;
        m1=(TextView)findViewById(R.id.textView5) ;
        exit=(Button)findViewById(R.id.exit) ;
        Intent intent = getIntent();
         message = intent.getIntExtra("slot",0);
        m.setText("Welome to Smart Parking\n\nYou are in Slot "+message);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  'at' HH:mm:ss");
        final String currentDateandTime = sdf.format(new Date());
        m1.setText("In Time: "+currentDateandTime);
        time=System.currentTimeMillis()/1000;

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase= FirebaseDatabase.getInstance();
                mDatabaseReference=mDatabase.getReference("python-example-f6d0b");
                mD=FirebaseDatabase.getInstance();
                r=mD.getReference("final");

                if(message==1){
                    mDatabaseReference.child("s0").setValue("0");
                    r.child("s0").setValue(0);
                }
                if(message==2){
                    mDatabaseReference.child("s1").setValue("0");
                    r.child("s1").setValue(0);
                }
                if(message==3){
                    mDatabaseReference.child("s2").setValue("0");
                    r.child("s2").setValue(0);
                }
                if(message==4){
                    mDatabaseReference.child("s3").setValue("0");
                    r.child("s3").setValue(0);
                }
                if(message==5){
                    mDatabaseReference.child("s4").setValue("0");
                    r.child("s4").setValue(0);
                }
                if(message==6){
                    mDatabaseReference.child("s5").setValue("0");
                    r.child("s5").setValue(0);
                }
                Intent intent = new Intent(slotActivity.this, payment.class);
                intent.putExtra("in",currentDateandTime);
                intent.putExtra("milli",time);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mDatabase= FirebaseDatabase.getInstance();
        mDatabaseReference=mDatabase.getReference("python-example-f6d0b");
        mD=FirebaseDatabase.getInstance();
        r=mD.getReference("final");

        if(message==1){
            mDatabaseReference.child("s0").setValue("0");
            r.child("s0").setValue(0);
        }
        if(message==2){
            mDatabaseReference.child("s1").setValue("0");
            r.child("s1").setValue(0);
        }
        if(message==3){
            mDatabaseReference.child("s2").setValue("0");
            r.child("s2").setValue(0);
        }
        if(message==4){
            mDatabaseReference.child("s3").setValue("0");
            r.child("s3").setValue(0);
        }
        if(message==5){
            mDatabaseReference.child("s4").setValue("0");
            r.child("s4").setValue(0);
        }
        if(message==6){
            mDatabaseReference.child("s5").setValue("0");
            r.child("s5").setValue(0);
        }
        Intent intent = new Intent(slotActivity.this, DashboardActivity.class);

        // Sending Email to Dashboard Activity using intent.


        startActivity(intent);
        finish();

    }
}
