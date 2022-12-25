package com.miniproject.loginscreen;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    //    ProgressBar regProgressBar;
    public static String TAG = "TAG";
    EditText username, emailaddress, phonenumber, password;
    Button signupbutton;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userid;
    TextView loginIfHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter);
        username = findViewById(R.id.username);
        emailaddress = findViewById(R.id.emailaddress);
        phonenumber = findViewById(R.id.phonenumber);
        password = findViewById(R.id.password);
        signupbutton = findViewById(R.id.signupbutton);
        loginIfHaveAccount = findViewById(R.id.loginIfHaveAccount);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        regProgressBar = findViewById(R.id.regprogressBar);



        loginIfHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uname = username.getText().toString().trim();
                String pword = password.getText().toString().trim();
                final String pnumber = phonenumber.getText().toString().trim();
                final String email = emailaddress.getText().toString().trim();

                if (TextUtils.isEmpty(uname)) {
                    username.setError("Username is required");
                    return;
                }

                if (TextUtils.isEmpty(pnumber)) {
                    password.setError("Phone number is required");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    emailaddress.setError("Emailaddress is required");
                    return;
                }

                if (password.length() < 6) {
                    password.setError("Password must be > 6");
                    return;
                }
//                regProgressBar.setVisibility(View.VISIBLE);
                fauth.createUserWithEmailAndPassword(email, pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fuser = fauth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseUser user = fauth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(uname).build();
                                    user.updateProfile(profileUpdates);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Onfailure:Email Not Sent" + e.getMessage());
                                }
                            });
                            Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_LONG).show();
                            userid = fauth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("user").document(userid);
                            Map<String, Object> user = new HashMap<>();
                            user.put("username", uname);
                            user.put("emailaddress", email);
                            user.put("phonenumber", pnumber);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "OnSuccess:user profile created for" + userid);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), RegLoading.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Not Sucessfully Registered"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
            }
        });


    }
}