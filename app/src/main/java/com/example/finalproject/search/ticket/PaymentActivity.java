package com.example.finalproject.search.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;

public class PaymentActivity extends AppCompatActivity {
    private TextView txtRouteTicket, txtTrip, txtNameTicket, txtBusNumber, txtPrice, txtSeatChoose;
    private TextView txtFullName, txtPhone, txtEmail;
    private ImageView rtnBack;
    private Button btnConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mapping();




        Bundle bundle = getIntent().getBundleExtra("to payment");
        if(bundle == null){
            return;
        }

        Boolean isFromMyTicket = bundle.getBoolean("from my ticket", false);
        if (isFromMyTicket){
            Button btn = (Button) findViewById(R.id.buttonConfirmInfo);
            btn.setVisibility(View.VISIBLE);
        }

        Ticket ticket = (Ticket) bundle.get("CustomerTicket");

//        txtRouteTicket.setText(bundle.getString("ROUTE"));
        txtRouteTicket.setText(ticket.getNameRoute());
        txtTrip.setText(ticket.getDepartureTime() + ' ' + ticket.getDepartureDate());
        txtNameTicket.setText(ticket.getNameTicket());
        txtBusNumber.setText(ticket.getBusNumber());
        txtSeatChoose.setText(ticket.getNumberSeat());
        txtPrice.setText(ticket.getPriceTicket());

        // Thông tin khách hàng
        txtFullName.setText(ticket.getFullName());
        txtPhone.setText(ticket.getPhone());
        txtEmail.setText(ticket.getEmail());

        rtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void mapping(){
        txtRouteTicket = (TextView) findViewById(R.id.TicketRouteInf);
        txtTrip = (TextView) findViewById(R.id.TicketTripInf);
        txtNameTicket = findViewById(R.id.BusTypeInf);
        txtBusNumber = findViewById(R.id.BusNumberInf);
        txtSeatChoose =findViewById(R.id.NumberTicketInf);
        txtPrice = findViewById(R.id.TotalPriceInf);

        rtnBack = (ImageView) findViewById(R.id.backBefore);

        btnConfirm = (Button) findViewById(R.id.buttonConfirmInfo);

        // Thong tin khach hang
        txtFullName = (TextView) findViewById(R.id.FullNameInf);
        txtPhone = (TextView) findViewById(R.id.PhoneNumberInf);
        txtEmail = (TextView) findViewById(R.id.EmailInf);
    }
}