package com.gssproductions.boiimela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutActivity extends AppCompatActivity {

    TextView total_books_count_tv, total_user_count_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        total_books_count_tv = findViewById(R.id.total_books_count_tv);
        total_user_count_tv = findViewById(R.id.total_user_count_tv);

        Handler bookHandler = new Handler();

        bookHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseDatabase.getInstance().getReference("bookData").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int count = 0;
//                        total_books_count_tv.setText(String.valueOf(snapshot.getChildrenCount()));
                        for(DataSnapshot i : snapshot.getChildren()){
                            count = (int) (count + i.getChildrenCount());
                        }
                        total_books_count_tv.setText(String.valueOf(count));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }, 2000);

        Handler userHandler = new Handler();

        userHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseDatabase.getInstance().getReference("Registered Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        total_user_count_tv.setText(String.valueOf(snapshot.getChildrenCount()));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }, 2000);


    }
}