package com.gssproductions.boiimela;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

    RelativeLayout email_verify_block;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        email_verify_block = findViewById(R.id.email_verify_block);

        email_verify_block.setOnClickListener(v -> {
            if(!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                Toast.makeText(this, "Please verify your email and restart the app", Toast.LENGTH_LONG).show();
            }
            else{
                email_verify_block.setBackgroundColor(Color.rgb(0, 255, 0));
            }
        });

    }
}