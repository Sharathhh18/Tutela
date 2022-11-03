package com.miniproject.loginscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    EditText username,emailaddress,phonenumber,password;
    Button signupbutton;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userid;
    TextView loginIfHaveAccount;
//    ProgressBar regProgressBar;
    public static String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter);
        username = findViewById(R.id.username);
        emailaddress  = findViewById(R.id.emailaddress);
        phonenumber = findViewById(R.id.phonenumber);
        password = findViewById(R.id.password);
        signupbutton = findViewById(R.id.signupbutton);
        loginIfHaveAccount = findViewById(R.id.loginIfHaveAccount);

        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        regProgressBar = findViewById(R.id.regprogressBar);

        loginIfHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uname = username.getText().toString().trim();
                String pword = password.getText().toString().trim();
                final String pnumber = phonenumber.getText().toString().trim();
                final String email = emailaddress.getText().toString().trim();
                fauth = fauth.getInstance();
                fstore = fstore.getInstance();
                if(TextUtils.isEmpty(uname))
                {
                    username.setError("Username is required");
                    return;
                }

                if(TextUtils.isEmpty(pnumber))
                {
                    password.setError("Phone number is required");
                    return;
                }

                if(TextUtils.isEmpty(email))
                {
                    emailaddress.setError("Emailaddress is required");
                    return;
                }

                if(password.length() < 6)
                {
                    password.setError("Password must be > 6");
                    return;
                }
//                regProgressBar.setVisibility(View.VISIBLE);
                fauth.createUserWithEmailAndPassword(email,pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser fuser = fauth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Registration Succesfull",Toast.LENGTH_LONG);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                   Log.d("Tag", "email not sent");
                                }
                            });
                        }
                        Toast.makeText(getApplicationContext(),"User created",Toast.LENGTH_LONG);
                        userid=fauth.getCurrentUser().getUid();
//                        DocumentReference dd

                    }
                });
                Toast.makeText(getApplicationContext(),"Sucessfully Registered",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), RegLoading.class));
            }
        });


    }
}