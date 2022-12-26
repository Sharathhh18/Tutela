package com.miniproject.loginscreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.os.Vibrator;

public class MScreen extends AppCompatActivity {
    VideoView videoView;
    Button mSubmit;
    EditText link;
    TextView message;
    private final String APIURL="https://urlpredictor.onrender.com/predict";

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
        message = findViewById(R.id.message);

        String path = "android.resource://com.miniproject.loginscreen/"+R.raw.mscreen;
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        message.setVisibility(View.GONE);


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

                StringRequest stringRequest = new StringRequest(Request.Method.POST, APIURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        String path = "android.resource://com.miniproject.loginscreen/"+R.raw.green;
                        try {
                            jsonObject = new JSONObject(response);
                            String res=jsonObject.getString("result");
                            Log.d("myTag", "res value: "+res);
                            if (res.equals("good")){

                                String rpath = "android.resource://com.miniproject.loginscreen/"+R.raw.green;
                                Uri uri = Uri.parse(rpath);
                                videoView.setVideoURI(uri);

                                message.setVisibility(View.VISIBLE);
                                message.setTextColor(Color.GREEN);
                                message.setText("Valid Link");


                                Toast.makeText(MScreen.this, "Valid Link", Toast.LENGTH_LONG).show();
                                // Don't Vibrate
                                // change image to green
                            }
                            else
                            {
                                String gpath = "android.resource://com.miniproject.loginscreen/"+R.raw.red;
                                Uri uri = Uri.parse(gpath);
                                videoView.setVideoURI(uri);

//                                 Vibrate Mobile
                                Vibrator v = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                    v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                }
                                v.vibrate(4000);
//                                // set color to red
                                message.setVisibility(View.VISIBLE);
                                message.setTextColor(Color.RED);
                                message.setText("Spam Link");


                                // change image to red
                                // label.setTextColor(new Color(RED));
                                Toast.makeText(MScreen.this, "Invalid Link", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MScreen.this, "Please Check Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        //Must Check
                        params.put("url",link.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(MScreen.this);
                requestQueue.add(stringRequest);
                Toast.makeText(MScreen.this, linkname, Toast.LENGTH_LONG).show();
            }
        });

    }
}