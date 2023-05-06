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

public class ChooseSeatActivity extends AppCompatActivity {
    private TextView txtRoute, txtDate;
    private ImageView rtnBack;
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

        Ticket ticket = (Ticket) bundle.get("My ticket");
        rtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // BIến hình ảnh ghế đã được chọn
        Drawable booked = getResources().getDrawable(R.drawable.booked_img);
        // Đăng ký OnClickListener cho từng View ghế
        for (int i = 0; i < seats.length; i++) {
            final int seatNumber = i;
            seats[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Biến hình ảnh ghế hiện tại
                    Drawable current = seats[seatNumber].getDrawable();
                    Log.d("MyApp", current.toString());
                    // Kiểm tra xem ghế đã được chọn hay chưa
                    if (current != null && current.getConstantState().equals(booked.getConstantState())) {
                        Toast.makeText(getApplicationContext(), "Ghế đã được đặt", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // Đổi ảnh ghế khi được click
                        ImageView seat = (ImageView) v;
                        seat.setImageDrawable(null);
                        seat.setImageResource(R.drawable.your_seat_img);
                        // Lưu trạng thái ghế vào một mảng
                    }
                }
            });
        }

        // Tiep tuc sang trang dien thong tin ve
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ChooseSeatActivity.this, CustomerInformationActivity.class);
                startActivity(intent);
            }
        });
    }
    private void mapping(){
        txtRoute = (TextView) findViewById(R.id.textviewRoute);
        txtDate = (TextView) findViewById(R.id.textviewDepartureDate);
        rtnBack = (ImageView) findViewById(R.id.backBefore);
        btnContinue = findViewById(R.id.buttonNextCusinfo);

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