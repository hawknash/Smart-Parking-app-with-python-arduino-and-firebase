package com.example.smartparking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class register extends AppCompatActivity {

    EditText name, email, password;
    Button mRegisterbtn;
    TextView mLoginPageBack;
    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    String Name, Email, Password;
    ProgressDialog mDialog;
    ImageButton pic;
    private Uri mImageUri,resultUri;
    private StorageReference mStorage;
    private static final int GALLERY_CODE=1;
    UploadTask uploadTask;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int CAMERA_REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.editName);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        mRegisterbtn = (Button) findViewById(R.id.buttonRegister);
        mLoginPageBack = (TextView) findViewById(R.id.buttonLogin);
        pic=(ImageButton)findViewById(R.id.imageButton);

        mAuth = FirebaseAuth.getInstance();
        mStorage= FirebaseStorage.getInstance().getReference();


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

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                //     galleryIntent.setType("image/*");
                //    startActivityForResult(galleryIntent,GALLERY_CODE);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }

            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {


            mImageUri = data.getData();
            pic.setImageURI(mImageUri);

            CropImage.activity(mImageUri)
                    .setAspectRatio(1,1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                pic.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        }


 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK){

            mImageUri=data.getData();
            pic.setImageURI(mImageUri);

        }

    } */

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
                        final StorageReference filepath=mStorage.child("MBlog_images").child(resultUri.getLastPathSegment());
                        uploadTask=filepath.putFile(resultUri);
                        sendEmailVerification();
                        mDialog.dismiss();
                        OnAuth(task.getResult().getUser());
                        mAuth.signOut();
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(register.this, "error on creating user/user already present", Toast.LENGTH_SHORT).show();
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




