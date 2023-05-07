package com.example.finalproject.search.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;

public class PaymentActivity extends AppCompatActivity {
    private TextView txtRoute, txtDate, txtRouteTicket, txtTrip, txtNameTicket, txtBusNumber, txtPrice, txtSeatChoose;
    private TextView txtFullName, txtPhone, txtEmail;
    private ImageView rtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mapping();

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        Ticket ticket = (Ticket) bundle.get("CustomerTicket");

        txtRoute.setText(bundle.getString("ROUTE"));
        txtDate.setText(bundle.getString("DATE"));
        txtRouteTicket.setText(bundle.getString("ROUTE"));
        txtTrip.setText(ticket.getDepartureTime() + ' ' + bundle.getString("DATE"));
        txtNameTicket.setText(ticket.getNameTicket());
        txtBusNumber.setText(ticket.getBusNumber());
        txtSeatChoose.setText(bundle.getString("SEAT"));
        txtPrice.setText(ticket.getPriceTicket());

        // Thông tin khách hàng
        txtFullName.setText(bundle.getString("NAME"));
        txtPhone.setText(bundle.getString("PHONE"));
        txtEmail.setText(bundle.getString("EMAIL"));
        rtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mapping(){
        txtRoute = (TextView) findViewById(R.id.textviewRoute);
        txtDate = (TextView) findViewById(R.id.textviewDepartureDate);
        txtRouteTicket = (TextView) findViewById(R.id.TicketRouteInf);
        txtTrip = (TextView) findViewById(R.id.TicketTripInf);
        txtNameTicket = findViewById(R.id.BusTypeInf);
        txtBusNumber = findViewById(R.id.BusNumberInf);
        txtSeatChoose =findViewById(R.id.NumberTicketInf);
        txtPrice = findViewById(R.id.TotalPriceInf);

        rtnBack = (ImageView) findViewById(R.id.backBefore);

        // Thong tin khach hang
        txtFullName = (TextView) findViewById(R.id.FullNameInf);
        txtPhone = (TextView) findViewById(R.id.PhoneNumberInf);
        txtEmail = (TextView) findViewById(R.id.EmailInf);
    }
}