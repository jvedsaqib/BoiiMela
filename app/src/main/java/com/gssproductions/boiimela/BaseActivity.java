package com.gssproductions.boiimela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.gssproductions.boiimela.databinding.ActivityMainBinding;

public class BaseActivity extends AppCompatActivity {

//    public DrawerLayout drawerLayout;
//    public ActionBarDrawerToggle actionBarDrawerToggle;
//
//    NavigationView navigationView;

    Long backPressedTime = Long.valueOf(0);
    BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getSupportActionBar().hide();

        replaceFragment(new HomeFragment());

        bottom_nav =  findViewById(R.id.bottom_nav);


//        drawerLayout = findViewById(R.id.drawer_layout);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
//        {
//
//            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                drawerLayout.closeDrawer(GravityCompat.START);
//                // Do whatever you want here
//            }
//
//            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                drawerLayout.bringToFront();
//                // Do whatever you want here
//            }
//        };
//
//
//        navigationView = findViewById(R.id.navmenu);
//        navigationView.setNavigationItemSelectedListener(this);


        bottom_nav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.nav_btn_home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.nav_btn_chats:
//                    drawerLayout.close();
//                    drawerLayout.closeDrawer(GravityCompat.START, false);
                    replaceFragment(new ChatFragment());
                    break;

                case R.id.nav_btn_my_ads:
//                    drawerLayout.close();
//                    drawerLayout.closeDrawer(GravityCompat.START, false);
                    replaceFragment(new MyAdsFragment());
                    break;

                case R.id.nav_btn_profile:
//                    drawerLayout.close();
//                    drawerLayout.closeDrawer(GravityCompat.START, false);
                    replaceFragment(new ProfileFragment());
                    break;
            }

            return true;
        });

//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();

    }

    public void onBackPressed(){

        if (backPressedTime + 500 > System.currentTimeMillis()) {
            FirebaseAuth.getInstance().signOut();
//            super.onBackPressed();
            finish();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout, new HomeFragment())
                    .addToBackStack(null).commit();
            bottom_nav.setSelectedItemId(R.id.nav_btn_home);
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_LONG).show();
        }
        backPressedTime = System.currentTimeMillis();

    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.sign_out:
////                Toast.makeText(this, "Signed Out right now", Toast.LENGTH_SHORT).show();
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(BaseActivity.this, LoginActivity.class));
//                break;
//            default:
////                Toast.makeText(this, "Default", Toast.LENGTH_SHORT).show();
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }
}