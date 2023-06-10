package com.example.finalproject.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;

public class HelpCenter_Activity extends AppCompatActivity {
    private Button call, mail;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        call =findViewById(R.id.btn_call_helpcenter);
        mail =findViewById(R.id.btn_mail_helpcenter);

        back = findViewById(R.id.back_helpcenter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpCenter_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "1900 1069"; // Replace with the phone number you want to call

                // Create an intent with the ACTION_DIAL action and the phone number
                Intent callintent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(callintent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","support@uit.edu.vn", null));
                startActivity(emailIntent);
            }
        });


    }


}