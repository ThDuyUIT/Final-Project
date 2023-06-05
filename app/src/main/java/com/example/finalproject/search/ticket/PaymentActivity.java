package com.example.finalproject.search.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.search.SearchFragment;

public class PaymentActivity extends AppCompatActivity {
    private TextView txtRoute, txtDate, txtRouteTicket, txtTrip, txtNameTicket, txtBusNumber, txtPrice, txtSeatChoose;
    private TextView txtFullName, txtPhone, txtEmail;
    private ImageView rtnBack;
    private Button btnComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mapping();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
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

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Đã đặt vé thành công", Toast.LENGTH_SHORT).show();
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
        btnComplete = findViewById(R.id.buttonNComplete);

        rtnBack = (ImageView) findViewById(R.id.backBefore);

        // Thong tin khach hang
        txtFullName = (TextView) findViewById(R.id.FullNameInf);
        txtPhone = (TextView) findViewById(R.id.PhoneNumberInf);
        txtEmail = (TextView) findViewById(R.id.EmailInf);
    }
}