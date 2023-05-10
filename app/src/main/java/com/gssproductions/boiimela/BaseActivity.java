package com.gssproductions.boiimela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_base);
//        getSupportActionBar().hide();

        replaceFragment(new WelcomeFragment());

        bottom_nav =  findViewById(R.id.bottom_nav);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        FirebaseDatabase.getInstance().getReference()
                                .child("tokens")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("token")
                                .setValue(token);

                    }
                });


        bottom_nav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.nav_btn_home:
                    replaceFragment(new WelcomeFragment());
                    break;

                case R.id.nav_btn_search:
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
            bottom_nav.setSelectedItemId(R.id.nav_btn_search);
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