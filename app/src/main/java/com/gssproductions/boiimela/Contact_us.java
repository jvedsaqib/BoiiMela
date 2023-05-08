package com.gssproductions.boiimela;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class Contact_us extends AppCompatActivity {

    Button btn_contactUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        btn_contactUs = findViewById(R.id.btn_contactUs);

        btn_contactUs.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/vZL8Na8gyCG2XVyy7"));
            startActivity(browserIntent);
        });


    }
}