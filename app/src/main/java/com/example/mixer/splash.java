package com.example.mixer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();


        //runs  the run function after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //go to main activity
                Intent i=new Intent(splash.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}