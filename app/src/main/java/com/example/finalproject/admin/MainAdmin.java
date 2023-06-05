package com.example.finalproject.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalproject.R;
import com.example.finalproject.search.ticket.ChooseSeatActivity;
import com.example.finalproject.search.ticket.CustomerInformationActivity;

public class MainAdmin extends AppCompatActivity {
    private CardView cdvAddTicket, cdvAddRoute, cdvCheckTicket, cdvReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        mapping();

        cdvAddTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdmin.this, AddTicketActivity.class);
                startActivity(intent);
            }
        });

        cdvAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdmin.this, AddRouteActivity.class);
                startActivity(intent);
            }
        });

        cdvCheckTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdmin.this, TicketBookedActivity.class);
                startActivity(intent);
            }
        });

        cdvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdmin.this, ReportActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        cdvAddTicket = findViewById(R.id.addTicket);
        cdvAddRoute = findViewById(R.id.addRoute);
        cdvCheckTicket = findViewById(R.id.checkTicket);
        cdvReport = findViewById(R.id.report);
    }
}