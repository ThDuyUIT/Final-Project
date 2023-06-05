package com.example.finalproject.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalproject.R;

public class TicketBookedActivity extends AppCompatActivity {
    private ImageView rtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booked);

        mapping();

        rtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void mapping() {
        rtnBack = (ImageView) findViewById(R.id.backBefore);
    }
}