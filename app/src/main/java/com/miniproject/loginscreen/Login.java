package com.miniproject.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Login extends AppCompatActivity {


    EditText loginusername,loginpassword;
    Button login;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginusername = findViewById(R.id.loginusername);
        loginpassword = findViewById(R.id.loginpassword);
        login=findViewById(R.id.login);
        register = findViewById(R.id.register);

        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uname = loginusername.getText().toString().trim();
                final String pword = loginpassword.getText().toString().trim();

                if(TextUtils.isEmpty(uname))
                {
                loginusername.setError("Username is required");
                }
                if(TextUtils.isEmpty(pword))
                {
                loginpassword.setError("password is required");
                }
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),LogLoading.class));
                    }
                });
            }
        });


    }
}