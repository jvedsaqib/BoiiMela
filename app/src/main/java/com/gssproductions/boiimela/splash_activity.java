package com.gssproductions.boiimela;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class splash_activity extends AppCompatActivity {

    TextView wel,learning;

    private static int Splash_timeout=5000;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        wel=findViewById(R.id.textview1);
        learning=findViewById(R.id.textview2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashintent=new Intent(splash_activity.this,MainActivity.class);
                startActivity(splashintent);
                finish();
            }
        },Splash_timeout);
        Animation animation2= AnimationUtils.loadAnimation(splash_activity.this,R.anim.animation2);
        wel.startAnimation(animation2);

        Animation animation1= AnimationUtils.loadAnimation(splash_activity.this,R.anim.animation1);
        learning.startAnimation(animation1);
    }
}