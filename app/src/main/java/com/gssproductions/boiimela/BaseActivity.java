package com.gssproductions.boiimela;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.gssproductions.boiimela.databinding.ActivityMainBinding;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getSupportActionBar().hide();

        replaceFragment(new HomeFragment());

        BottomNavigationView bottom_nav =  findViewById(R.id.bottom_nav);

        bottom_nav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.nav_btn_home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.nav_btn_chats:
                    replaceFragment(new ChatFragment());
                    break;

                case R.id.nav_btn_my_ads:
                    replaceFragment(new MyAdsFragment());
                    break;

                case R.id.nav_btn_profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }

            return true;
        });

    }

    public void onBackPressed(){
        Toast.makeText(this, "Signed Out right now", Toast.LENGTH_SHORT);
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(BaseActivity.this, LoginActivity.class));
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();

    }
}