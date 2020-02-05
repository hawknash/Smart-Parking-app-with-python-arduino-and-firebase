package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class payment extends AppCompatActivity {

    private TextView intime;
    private TextView outtime,payment,pay;
    long time,total,tpay;
    private Button signout;
    TextToSpeech t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment);
        intime=(TextView) findViewById(R.id.textView7) ;
        outtime=(TextView)findViewById(R.id.textView8);
        payment=(TextView)findViewById(R.id.textView9);
        pay=(TextView)findViewById(R.id.textView10);
        signout=(Button)findViewById(R.id.signout);

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

        Intent intent = getIntent();
        String min= intent.getStringExtra("in");

        long mili = intent.getLongExtra("milli",0);

        intime.setText("Your intime was: "+min);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  'at' HH:mm:ss");
        final String currentDateandTime = sdf.format(new Date());
        outtime.setText("Your Outtime is: "+currentDateandTime);

        time=System.currentTimeMillis()/1000;
        total=time-mili;
        payment.setText("total time in seconds is:"+total);

        tpay=total/(60*60);
        if(tpay==0){
            tpay=30;
        }
        pay.setText("Your Total Amount Is Rs."+tpay);
        t1.setPitch(0.3f);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toSpeak="thank you for using smart parking";
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Thread.sleep(1500);
                            FirebaseAuth fAuth = FirebaseAuth.getInstance();
                            fAuth.signOut();
                            Intent intent = new Intent(payment.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        catch (InterruptedException e)
                        {                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });
         }
}
