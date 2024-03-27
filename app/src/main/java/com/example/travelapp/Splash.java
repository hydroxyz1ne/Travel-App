package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class Splash extends AppCompatActivity {

    private final int zamorozka=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent per = new Intent(Splash.this, MainScreen.class);
                Splash.this.startActivity(per);
                Splash.this.finish();
            }

        },zamorozka);


    }
}
