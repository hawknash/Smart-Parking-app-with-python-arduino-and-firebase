package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class payment extends AppCompatActivity {

    private TextView intime;
    private TextView outtime,payment,pay;
    long time,total,tpay;
    private Button signout;
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

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                Intent intent = new Intent(payment.this, MainActivity.class);


                startActivity(intent);
                finish();
            }
        });





    }
}
