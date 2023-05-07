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
    private TextView txtRoute, txtDate, txtNameTicket, txtBusNumber, txtTime, txtPrice, txtSeatChoose;
    private TextView txtFullName, txtPhone, txtEmail, txtNoteOther;
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
        txtTime.setText(ticket.getDepartureTime());
        txtPrice.setText(ticket.getPriceTicket());
        txtSeatChoose.setText(bundle.getString("SEAT"));
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
                String route = txtRoute.getText().toString();
                String date = txtDate.getText().toString();
                String seat = txtSeatChoose.getText().toString();

                String fullName = txtFullName.getText().toString();
                String phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();

                Intent intent = new Intent(CustomerInformationActivity.this, PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ROUTE", route);
                bundle.putString("DATE", date);
                bundle.putString("SEAT", seat);
                bundle.putString("NAME", fullName);
                bundle.putString("PHONE", phone);
                bundle.putString("EMAIL", email);
                bundle.putSerializable("CustomerTicket", ticket);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void mapping(){
        txtRoute = (TextView) findViewById(R.id.textviewRoute);
        txtDate = (TextView) findViewById(R.id.textviewDepartureDate);
        rtnBack = (ImageView) findViewById(R.id.backBefore);
        btnContinue = findViewById(R.id.buttonNextPayment);
        txtSeatChoose =findViewById(R.id.seatChoose);

        // Thong tin khach hang
        txtFullName = (TextView) findViewById(R.id.editTextName);
        txtPhone = (TextView) findViewById(R.id.editTextPhone);
        txtEmail = (TextView) findViewById(R.id.editTextEmail);
        txtNoteOther = (TextView) findViewById(R.id.editTextOtherRequest);

        // Vé xe
        imgBus = findViewById(R.id.imageTicket);
        txtNameTicket = findViewById(R.id.nameTicket);
        txtBusNumber = findViewById(R.id.busNumber);
        txtTime = findViewById(R.id.departureTime);
        txtPrice = findViewById(R.id.priceTicket);
    }
}