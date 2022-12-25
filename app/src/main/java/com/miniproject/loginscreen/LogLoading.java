package com.miniproject.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

public class LogLoading extends AppCompatActivity {

    @Override
    //Darkmode
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_loading);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"Registration Succesfull",Toast.LENGTH_LONG);
                startActivity(new Intent(getApplicationContext(), MScreen.class).putExtra("username",""+getIntent().getStringExtra("username")));
                finish();
            }
        }, 5000);
    }
}