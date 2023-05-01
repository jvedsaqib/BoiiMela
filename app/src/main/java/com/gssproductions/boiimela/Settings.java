package com.gssproductions.boiimela;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

    RelativeLayout email_verify_block;

    ImageView back_to_frag_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //edit profile
        Button buttonUpdateProfile=findViewById(R.id.buttonEditProfile);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Settings.this,UpdateProfileActivity.class);
                startActivity(intent);
            }
        });

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

        back_to_frag_btn = findViewById(R.id.back_to_frag_btn);
        back_to_frag_btn.setOnClickListener(click ->{
            startActivity(new Intent(Settings.this, BaseActivity.class));
            finish();
        });

    }
}