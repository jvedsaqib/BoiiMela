package com.gssproductions.boiimela;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Contact_us extends AppCompatActivity {

    Button btn_contactUs;

    TextView cust_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        btn_contactUs = findViewById(R.id.btn_contactUs);
        cust_call = findViewById(R.id.cust_call);

        cust_call.setOnClickListener(v -> {

        });



        btn_contactUs.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/vZL8Na8gyCG2XVyy7"));
            startActivity(browserIntent);
        });


    }
}