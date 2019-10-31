package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class payment extends AppCompatActivity {

    private TextView intime;
    private TextView outtime,payment,pay;
    long time,total,tpay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        intime=(TextView) findViewById(R.id.textView7) ;
        outtime=(TextView)findViewById(R.id.textView8);
        payment=(TextView)findViewById(R.id.textView9);
        pay=(TextView)findViewById(R.id.textView10);

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







    }
}
