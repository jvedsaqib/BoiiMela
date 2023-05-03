package com.gssproductions.boiimela;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {


    private View parentView;
    private SwitchMaterial themeSwitch;
    private TextView themeTV, titleTV;

    private UserSettings settings;
    RelativeLayout email_verify_block;

    ImageView back_to_frag_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = (UserSettings) getApplication();

      //  initWidgets();
       // loadSharedPreferences();
        //initSwitchListener();


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

    /*private void initSwitchListener() {
    themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

            if(checked)
                settings.setCustomTheme(UserSettings.DARK_THEME);
            else
                settings.setCustomTheme(UserSettings.LIGHT_THEME);

            SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCESS,MODE_PRIVATE).edit();
            editor.putString(UserSettings.CUSTOM_THEME,settings.getCustomTheme());
            editor.apply();
            updateView();
        }

        private void updateView() {
            final int black = ContextCompat.getColor(this, R.color.black);
            final int white = ContextCompat.getColor(this, R.color.white);

            if(settings.getCustomTheme().equals(UserSettings.DARK_THEME))
            {
                titleTV.setTextColor(white);
                themeTV.setTextColor(white);
                themeTV.setText("Dark");
                parentView.setBackgroundColor(black);
                themeSwitch.setChecked(true);
            }
            else
            {
                titleTV.setTextColor(black);
                themeTV.setTextColor(black);
                themeTV.setText("Light");
                parentView.setBackgroundColor(white);
                themeSwitch.setChecked(false);
            }

        }
    });

    }

    private void loadSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCESS,MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME,UserSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
    }

    private void initWidgets() {
        themeTV = findViewById(R.id.themeTV);
        titleTV = findViewById(R.id.titleTV);
        themeSwitch = findViewById(R.id.themeSwitchTV);
        parentView = findViewById(R.id.parentView);*/

    }
//}