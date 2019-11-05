package com.example.smartparking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import static java.util.logging.Logger.global;

public class DashboardActivity extends AppCompatActivity {
    int[] value = new int[6];
     int sl0=0,sl1=0,sl2=0,sl3=0,sl4=0,sl5=0;
    private Button s0;
    private Button s1;
    private Button s2;
    private Button s3;
    private Button s4;
    private Button s5;
    private Button location;
    TextToSpeech t1;


    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        s0=(Button)findViewById(R.id.button1);
        s1=(Button)findViewById(R.id.button2);
        s2=(Button)findViewById(R.id.button3);
        s3=(Button)findViewById(R.id.button4);
        s4=(Button)findViewById(R.id.button5);
        s5=(Button)findViewById(R.id.button6);
        location=(Button)findViewById(R.id.button8);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = t1.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        t1.setPitch(0.3f);


        final DatabaseReference mDatabaseReference,r;
         final FirebaseDatabase mDatabase,mD;

         FirebaseUser mUser;
         FirebaseAuth mAuth;

        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mDatabase.getReference("python-example-f6d0b");
        mD=FirebaseDatabase.getInstance();
        r=mD.getReference("final");
        Log.d("naman", "User name: "  + ", email " + mDatabaseReference);
        mDatabaseReference.keepSynced(true);


    mDatabaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot a : dataSnapshot.getChildren()) {
                Log.d("naman", "User name: " + a.getValue() + a.getKey());
                if (((String)a.getValue()).equals("255") && ((String)a.getKey()).equals("s0")){
                    sl0=1;
                    s0.setBackgroundColor(Color.RED);
                }
                if (((String)a.getValue()).equals("0") && ((String)a.getKey()).equals("s0")){
                    sl0=0;
                    s0.setBackgroundColor(Color.BLUE);
                }
                if (((String)a.getValue()).equals("0") && ((String)a.getKey()).equals("s1")){
                    sl1=0;
                    s1.setBackgroundColor(Color.BLUE);
                }
                if (((String)a.getValue()).equals("255") && ((String)a.getKey()).equals("s1")){
                    sl1=1;
                    s1.setBackgroundColor(Color.RED);
                }
                if (((String)a.getValue()).equals("0") && ((String)a.getKey()).equals("s2")){
                    sl2=0;
                    s2.setBackgroundColor(Color.BLUE);
                }
                if (((String)a.getValue()).equals("255") && ((String)a.getKey()).equals("s2")){
                    sl2=1;
                    s2.setBackgroundColor(Color.RED);
                }
                if (((String)a.getValue()).equals("0") && ((String)a.getKey()).equals("s3")){
                    sl3=0;
                    s3.setBackgroundColor(Color.BLUE);
                }
                if (((String)a.getValue()).equals("255") && ((String)a.getKey()).equals("s3")){
                    sl3=1;
                    s3.setBackgroundColor(Color.RED);
                }
                if (((String)a.getValue()).equals("0") && ((String)a.getKey()).equals("s4")){
                    sl4=0;
                    s4.setBackgroundColor(Color.BLUE);
                }
                if (((String)a.getValue()).equals("255") && ((String)a.getKey()).equals("s4")){
                    sl4=1;
                    s4.setBackgroundColor(Color.RED);
                }
                if (((String)a.getValue()).equals("0") && ((String)a.getKey()).equals("s5")){
                    sl5=0;
                    s5.setBackgroundColor(Color.BLUE);
                }
                if (((String)a.getValue()).equals("255") && ((String)a.getKey()).equals("s5")){
                    sl5=1;
                    s5.setBackgroundColor(Color.RED);
                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    s0.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if(sl0==1){
                Toast.makeText(DashboardActivity.this, "Sorry! The slot is filled.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                String toSpeak="Welcome to smart Parking              You Are in slot 1";
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Thread.sleep(2000);
                            mDatabaseReference.child("s0").setValue("255");
                            r.child("s0").setValue(1);
                            Intent intent = new Intent(DashboardActivity.this, slotActivity.class);
                            intent.putExtra("slot",1);

                            startActivity(intent);
                            finish();
                        }
                        catch (InterruptedException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();


            }

        }
    });

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sl1==1){
                    Toast.makeText(DashboardActivity.this, "Sorry! The slot is filled.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String toSpeak="Welcome to smart Parking You Are in slot 2";
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Thread.sleep(2000);
                                mDatabaseReference.child("s1").setValue("255");
                                r.child("s1").setValue(1);
                                Intent intent = new Intent(DashboardActivity.this, slotActivity.class);
                                intent.putExtra("slot",2);

                                startActivity(intent);
                                finish();
                            }
                            catch (InterruptedException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();



                }

            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);


                startActivity(intent);
                finish();

            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl2==1){
                    Toast.makeText(DashboardActivity.this, "Sorry! The slot is filled.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String toSpeak="Welcome to smart Parking You Are in slot 3";
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Thread.sleep(2000);
                                mDatabaseReference.child("s2").setValue("255");
                                r.child("s2").setValue(1);
                                Intent intent = new Intent(DashboardActivity.this, slotActivity.class);
                                intent.putExtra("slot",3);

                                startActivity(intent);
                                finish();
                            }
                            catch (InterruptedException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();


                }

            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl3==1){
                    Toast.makeText(DashboardActivity.this, "Sorry! The slot is filled.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String toSpeak="Welcome to smart Parking You Are in slot 4";
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Thread.sleep(2000);
                                mDatabaseReference.child("s3").setValue("255");
                                r.child("s3").setValue(1);
                                Intent intent = new Intent(DashboardActivity.this, slotActivity.class);
                                intent.putExtra("slot",4);

                                startActivity(intent);
                                finish();
                            }
                            catch (InterruptedException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }

            }
        });


        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(sl4==1){
                    Toast.makeText(DashboardActivity.this, "Sorry! The slot is filled.", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    String toSpeak="Welcome to smart Parking You Are in slot 5";
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Thread.sleep(2000);
                                mDatabaseReference.child("s4").setValue("255");
                                r.child("s4").setValue(1);
                                Intent intent = new Intent(DashboardActivity.this, slotActivity.class);
                                intent.putExtra("slot",5);

                                startActivity(intent);
                                finish();

                            }
                            catch (InterruptedException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();




                }

            }
        });

        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sl5==1){
                    Toast.makeText(DashboardActivity.this, "Sorry! The slot is filled.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String toSpeak="Welcome to smart Parking You Are in slot 6";
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Thread.sleep(2000);
                                mDatabaseReference.child("s5").setValue("255");
                                r.child("s5").setValue(1);
                                Intent intent = new Intent(DashboardActivity.this, slotActivity.class);
                                intent.putExtra("slot",6);

                                startActivity(intent);
                                finish();
                            }
                            catch (InterruptedException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();


                }
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
    }

}
