package com.miniproject.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {


    EditText loginemail, loginpassword;
    Button login;
    TextView register;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginemail = findViewById(R.id.loginemail);
        loginpassword = findViewById(R.id.loginpassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        firebaseAuth = firebaseAuth.getInstance();
        firebaseFirestore = firebaseFirestore.getInstance();

//        if (firebaseAuth.getCurrentUser()!=null)
//        {
//            startActivity(new Intent(getApplicationContext(), LogLoading.class));
//            finish();
//        }

        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uemail = loginemail.getText().toString().trim();
                final String pword = loginpassword.getText().toString().trim();

                if (TextUtils.isEmpty(uemail)) {
                    loginemail.setError("Username is required");
                    return;
                } else if (TextUtils.isEmpty(pword)) {
                    loginpassword.setError("password is required");
                    return;
                } else {
                    firebaseAuth.signInWithEmailAndPassword(uemail,pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(Login.this, "Successfully logged in", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), LogLoading.class).putExtra("username",""+firebaseAuth.getCurrentUser().getDisplayName()));
                            }
                            else
                            {
                                Toast.makeText(Login.this, ""+ task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
            }
        });


    }
}