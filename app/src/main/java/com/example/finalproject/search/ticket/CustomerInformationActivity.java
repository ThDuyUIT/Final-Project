package com.example.finalproject.search.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;

public class CustomerInformationActivity extends AppCompatActivity {
    private TextView txtRoute, txtDate, txtNameTicket, txtBusNumber, txtTime, txtPrice;
    private ImageView rtnBack, imgBus;
    private Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        mapping();

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        Ticket ticket = (Ticket) bundle.get("TicketMoreInformation");

        txtRoute.setText(bundle.getString("ROUTE"));
        txtDate.setText(bundle.getString("DATE"));
        imgBus.setImageResource(ticket.getResourceID());
        txtNameTicket.setText(ticket.getNameTicket());
        txtBusNumber.setText(ticket.getBusNumber());
        txtPrice.setText(ticket.getDepartureTime());
        txtPrice.setText(ticket.getPriceTicket());
        rtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Tiep tuc sang trang dien thong tin ve
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(CustomerInformationActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping(){
        txtRoute = (TextView) findViewById(R.id.textviewRoute);
        txtDate = (TextView) findViewById(R.id.textviewDepartureDate);
        rtnBack = (ImageView) findViewById(R.id.backBefore);
        btnContinue = findViewById(R.id.buttonNextPayment);

        // Vé xe
        imgBus = findViewById(R.id.imageTicket);
        txtNameTicket = findViewById(R.id.nameTicket);
        txtBusNumber = findViewById(R.id.busNumber);
        txtTime = findViewById(R.id.departureTime);
        txtPrice = findViewById(R.id.priceTicket);
    }
}