package com.example.smartparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class register extends AppCompatActivity {

    EditText name, email, password;
    Button mRegisterbtn;
    TextView mLoginPageBack;
    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    String Name, Email, Password;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.editName);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        mRegisterbtn = (Button) findViewById(R.id.buttonRegister);
        mLoginPageBack = (TextView) findViewById(R.id.buttonLogin);
        // for authentication using FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();


        mDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegister();
            }
        });

        mLoginPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,MainActivity.class));
            }
        });

    }
    /*    @Override
        public void onClick (View v){
            if (v == mRegisterbtn) {
                UserRegister();
            } else if (v == mLoginPageBack) {
                startActivity(new Intent(register.this, MainActivity.class));
            }
        }*/


        private void UserRegister () {
            Name = name.getText().toString().trim();
            Email = email.getText().toString().trim();
            Password = password.getText().toString().trim();

            if (TextUtils.isEmpty(Name)) {
                Toast.makeText(register.this, "Enter Name", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(Email)) {
                Toast.makeText(register.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(Password)) {
                Toast.makeText(register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            } else if (Password.length() < 6) {
                Toast.makeText(register.this, "Passwor must be greater then 6 digit", Toast.LENGTH_SHORT).show();
                return;
            }
            mDialog.setMessage("Creating User please wait...");
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
            mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendEmailVerification();
                        mDialog.dismiss();
                        OnAuth(task.getResult().getUser());
                        mAuth.signOut();
                    } else {
                        Toast.makeText(register.this, "error on creating user", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        //Email verification code using FirebaseUser object and using isSucccessful()function.
        private void sendEmailVerification () {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(register.this, "Check your Email for verification", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                        }
                    }
                });
            }
        }

        private void OnAuth (FirebaseUser user){
            createAnewUser(user.getUid());
        }

        private void createAnewUser (String uid){
            User user = BuildNewuser();
            mdatabase.child(uid).setValue(user);
        }


        private User BuildNewuser () {
            return new User(
                    getDisplayName(),
                    getUserEmail(),
                    new Date().getTime()
            );
        }

        public String getDisplayName () {
            return Name;
        }

        public String getUserEmail () {
            return Email;
        }

    }




