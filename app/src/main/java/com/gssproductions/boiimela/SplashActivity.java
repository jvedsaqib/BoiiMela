package com.gssproductions.boiimela;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    TextView wel,learning;

    private static int Splash_timeout=4000;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // getSupportActionBar().hide();
        hideStatusBar();

        wel=findViewById(R.id.textview1);
        learning=findViewById(R.id.textview2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser() == null){
                    Intent splashintent=new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(splashintent);
                }
                else{
                    Intent splashintent=new Intent(SplashActivity.this,BaseActivity.class);
                    startActivity(splashintent);
                }
                finish();
            }
        },Splash_timeout);
        Animation animation2= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.animation2);
        wel.startAnimation(animation2);

        Animation animation1= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.animation1);
        learning.startAnimation(animation1);
    }

    // This snippet hides the system bars.
    public void hideStatusBar() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    );
        }
    }

}