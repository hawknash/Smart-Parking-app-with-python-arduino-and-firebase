package com.example.smartparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class slotActivity extends AppCompatActivity {
    private TextView m,m1;
    private Button exit;
    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);
        m=(TextView)findViewById(R.id.textView4) ;
        m1=(TextView)findViewById(R.id.textView5) ;
        exit=(Button)findViewById(R.id.exit) ;
        Intent intent = getIntent();
        String message = intent.getStringExtra("slot");
        m.setText("Welome to Smart Parking\n\nYou are in "+message);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  'at' HH:mm:ss");
        final String currentDateandTime = sdf.format(new Date());
        m1.setText("In Time: "+currentDateandTime);
        time=System.currentTimeMillis()/1000;

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(slotActivity.this, payment.class);
                intent.putExtra("in",currentDateandTime);
                intent.putExtra("milli",time);
                startActivity(intent);

            }
        });

    }
}
