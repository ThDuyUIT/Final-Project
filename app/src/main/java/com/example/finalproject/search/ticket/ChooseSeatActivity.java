package com.example.finalproject.search.ticket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.search.CustomerTicket;

import java.util.ArrayList;

public class ChooseSeatActivity extends AppCompatActivity {
    private TextView txtRoute, txtDate, txtNameTicket, txtBusNumber, txtTime, txtPrice, txtSeatChoose;
    private ImageView rtnBack, imgBus;
    private ImageView[] seats = new ImageView[40];
    private Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
        mapping();

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        Ticket ticket = (Ticket) bundle.get("busInformation");

        txtRoute.setText(bundle.getString("ROUTE"));
        txtDate.setText(bundle.getString("DATE"));
        imgBus.setImageResource(ticket.getResourceID());
        txtNameTicket.setText(ticket.getNameTicket());
        txtBusNumber.setText(ticket.getBusNumber());
        txtTime.setText(ticket.getDepartureTime());
        txtPrice.setText(ticket.getPriceTicket());
        rtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // BIến hình ảnh ghế đã được chọn
        Drawable booked = getResources().getDrawable(R.drawable.booked_img);
        Drawable yourseat = getResources().getDrawable(R.drawable.your_seat_img);
        ArrayList<String> seatChoosed = new ArrayList<>();
        // Đăng ký OnClickListener cho từng View ghế
        for (int i = 0; i < seats.length; i++) {
            final int seatNumber = i;
            seats[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Biến hình ảnh ghế hiện tại
                    Drawable current = seats[seatNumber].getDrawable();
                    // Kiểm tra xem ghế đã được chọn hay chưa
                    if (current.getConstantState().equals(booked.getConstantState()) || current.getConstantState().equals(yourseat.getConstantState())) {
                        Toast.makeText(getApplicationContext(), "Ghế đã được đặt", Toast.LENGTH_SHORT).show();
                    }
                    else if (!seatChoosed.contains(seatNumber) && seatChoosed.size() < 6) {
                        int remainder = seatNumber % 4;
                        // Đổi ảnh ghế khi được click
                        ImageView seat = (ImageView) v;
                        seat.setImageDrawable(null);
                        seat.setImageResource(R.drawable.your_seat_img);
                        // Lưu trạng thái ghế vào một mảng
                        String output;
                        if (remainder == 0) {
                            output = (seatNumber / 4) + 1 + "A";
                        } else if (remainder == 1) {
                            output = (seatNumber / 4) + 1 + "B";
                        } else if (remainder == 2) {
                            output = (seatNumber / 4) + 1 + "A upstairs";
                        } else {
                            output = (seatNumber / 4) + 1 + "B upstairs";
                        }
                        seatChoosed.add(output);
                        txtSeatChoose.setText(seatChoosed.toString().replace("[", "").replace("]", ""));
                    }
                    else if (seatChoosed.size() >= 6) {
                        Toast.makeText(getApplicationContext(), "Bạn đã chọn đủ số ghế tối đa, 6 ghế", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // Tiep tuc sang trang dien thong tin ve
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String route = txtRoute.getText().toString();
                String date = txtDate.getText().toString();
                String seat = txtSeatChoose.getText().toString();

                Intent intent = new Intent(ChooseSeatActivity.this, CustomerInformationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ROUTE", route);
                bundle.putString("DATE", date);
                bundle.putString("SEAT", seat);
                bundle.putSerializable("TicketMoreInformation", ticket);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    private void mapping(){
        txtRoute = (TextView) findViewById(R.id.textviewRoute);
        txtDate = (TextView) findViewById(R.id.textviewDepartureDate);
        rtnBack = (ImageView) findViewById(R.id.backBefore);
        btnContinue = findViewById(R.id.buttonNextCusinfo);
        txtSeatChoose = findViewById(R.id.seatChoose);

        // Vé xe
        imgBus = findViewById(R.id.imageTicket);
        txtNameTicket = findViewById(R.id.nameTicket);
        txtBusNumber = findViewById(R.id.busNumber);
        txtTime = findViewById(R.id.departureTime);
        txtPrice = findViewById(R.id.priceTicket);

        // Lưu trữ tất cả các View ghế trong một mảng
        seats[0] = findViewById(R.id.seat_a1);
        seats[1] = findViewById(R.id.seat_b1);
        seats[2] = findViewById(R.id.seat_a1_upstair);
        seats[3] = findViewById(R.id.seat_b1_upstair);
        seats[4] = findViewById(R.id.seat_a2);
        seats[5] = findViewById(R.id.seat_b2);
        seats[6] = findViewById(R.id.seat_a2_upstair);
        seats[7] = findViewById(R.id.seat_b2_upstair);
        seats[8] = findViewById(R.id.seat_a3);
        seats[9] = findViewById(R.id.seat_b3);
        seats[10] = findViewById(R.id.seat_a3_upstair);
        seats[11] = findViewById(R.id.seat_b3_upstair);
        seats[12] = findViewById(R.id.seat_a4);
        seats[13] = findViewById(R.id.seat_b4);
        seats[14] = findViewById(R.id.seat_a4_upstair);
        seats[15] = findViewById(R.id.seat_b4_upstair);
        seats[16] = findViewById(R.id.seat_a5);
        seats[17] = findViewById(R.id.seat_b5);
        seats[18] = findViewById(R.id.seat_a5_upstair);
        seats[19] = findViewById(R.id.seat_b5_upstair);
        seats[20] = findViewById(R.id.seat_a6);
        seats[21] = findViewById(R.id.seat_b6);
        seats[22] = findViewById(R.id.seat_a6_upstair);
        seats[23] = findViewById(R.id.seat_b6_upstair);
        seats[24] = findViewById(R.id.seat_a7);
        seats[25] = findViewById(R.id.seat_b7);
        seats[26] = findViewById(R.id.seat_a7_upstair);
        seats[27] = findViewById(R.id.seat_b7_upstair);
        seats[28] = findViewById(R.id.seat_a8);
        seats[29] = findViewById(R.id.seat_b8);
        seats[30] = findViewById(R.id.seat_a8_upstair);
        seats[31] = findViewById(R.id.seat_b8_upstair);
        seats[32] = findViewById(R.id.seat_a9);
        seats[33] = findViewById(R.id.seat_b9);
        seats[34] = findViewById(R.id.seat_a9_upstair);
        seats[35] = findViewById(R.id.seat_b9_upstair);
        seats[36] = findViewById(R.id.seat_a10);
        seats[37] = findViewById(R.id.seat_b10);
        seats[38] = findViewById(R.id.seat_a10_upstair);
        seats[39] = findViewById(R.id.seat_b10_upstair);
    }
}

