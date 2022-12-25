package com.miniproject.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

public class MScreen extends AppCompatActivity {
    VideoView videoView;
    Button mSubmit;
    EditText link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_mscreen);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView = findViewById(R.id.mscreenvideo);
        mSubmit = findViewById(R.id.msubmit);
        link = findViewById(R.id.link);
        String path = "android.resource://com.miniproject.loginscreen/"+R.raw.mscreen;
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        Toast.makeText(this, "Welcome "+ getIntent().getStringExtra("username"), Toast.LENGTH_SHORT).show();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        //Loop ie  play again and again
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String linkname = link.getText().toString().trim();
                Toast.makeText(MScreen.this, linkname, Toast.LENGTH_LONG).show();
            }
        });

    }
}