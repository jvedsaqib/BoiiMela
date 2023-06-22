package com.gssproductions.boiimela;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {


    private View parentView;
    private SwitchMaterial themeSwitch;
    private TextView themeTV, titleTV;

    private UserSettings settings;
    RelativeLayout security, RL_aboutUs,
            RL_contactUs, RL_editProfile,
            RL_deleteProfile;

    ImageView back_to_frag_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);

        settings = (UserSettings) getApplication();



        //edit profile
        RL_editProfile = findViewById(R.id.RL_editProfile);
        RL_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this,UpdateProfileActivity.class);
                startActivity(intent);
            }
        });

        //delete profile
        RL_deleteProfile = findViewById(R.id.RL_deleteProfile);
        RL_deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this,DeleteProfileActivity.class);
                startActivity(intent);
            }
        });

        //security
        security = findViewById(R.id.RL_security);
        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this, SecurityActivity.class);
                startActivity(intent);
            }
        });


        back_to_frag_btn = findViewById(R.id.back_to_frag_btn);
        back_to_frag_btn.setOnClickListener(click ->{
            startActivity(new Intent(SettingsActivity.this, BaseActivity.class));
            finish();
        });

        RL_aboutUs = findViewById(R.id.RL_aboutUs);
        RL_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
        RL_contactUs = findViewById(R.id.RL_contactUs);

//        RL_aboutUs.setOnClickListener(v ->{
//            Intent intent=new Intent(SettingsActivity.this, aboutUs.class);
//            startActivity(intent);
//        });

        RL_contactUs.setOnClickListener(v -> {
            Intent intent=new Intent(SettingsActivity.this, Contact_us.class);
            startActivity(intent);
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