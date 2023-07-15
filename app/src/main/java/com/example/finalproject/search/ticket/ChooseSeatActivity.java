package com.example.finalproject.search.ticket;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.authentication.Account;
import com.example.finalproject.authentication.LoginActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChooseSeatActivity extends AppCompatActivity {
    private TextView txtRoute, txtDate, txtNameTicket, txtBusNumber, txtTime, txtPrice, txtSeatChoose;
    private ImageView rtnBack, imgBus;
    private ImageView[] seats = new ImageView[40];
    private Button btnContinue;
    private Ticket ticket;
    private FirebaseUser user;
    private Account account;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
        mapping();

        Bundle bundle = getIntent().getBundleExtra("to choose seat");
        if(bundle == null){
            return;
        }

        ticket = (Ticket) bundle.get("busInformation");
        account = (Account) bundle.get("ACCOUNT");
        txtRoute.setText(ticket.getNameRoute());
        txtDate.setText(ticket.getDepartureDate());
        Uri uri = Uri.parse(ticket.getSrcImg());
        Picasso.get().load(uri).into(imgBus);
        //imgBus.setImageResource(ticket.getResourceID());
        txtNameTicket.setText(ticket.getNameTicket());
        txtBusNumber.setText(ticket.getBusNumber());
        txtTime.setText(ticket.getDepartureTime());
        txtPrice.setText(ticket.getPriceTicket());

        checkDataSeat();

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
                    if (current.getConstantState().equals(booked.getConstantState())) {
                        Toast.makeText(getApplicationContext(), "Ghế đã được đặt", Toast.LENGTH_SHORT).show();
                    }
                    else if (current.getConstantState().equals(yourseat.getConstantState())) {
                        int remainder = seatNumber % 4;
                        ImageView seat = (ImageView) v;
                        seat.setImageDrawable(null);
                        seat.setImageResource(R.drawable.available_img);
                        String output;
                        if (remainder == 0) {
                            output = (seatNumber / 4) + 1 + "A";
                        } else if (remainder == 1) {
                            output = (seatNumber / 4) + 1 + "B";
                        } else if (remainder == 2) {
                            output = (seatNumber / 4) + 1 + "A upstair";
                        } else {
                            output = (seatNumber / 4) + 1 + "B upstair";
                        }
                        seatChoosed.remove(output);
                        txtSeatChoose.setText(seatChoosed.toString().replace("[", "").replace("]", ""));
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
                            output = (seatNumber / 4) + 1 + "A upstair";
                        } else {
                            output = (seatNumber / 4) + 1 + "B upstair";
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
//                String route = txtRoute.getText().toString();
//                String date = txtDate.getText().toString();
                String seat = txtSeatChoose.getText().toString();
                if(seat.isEmpty()){
                    Toast.makeText(ChooseSeatActivity.this, "Please choose a seat", Toast.LENGTH_LONG).show();
                    return;
                }

                //user = FirebaseAuth.getInstance().getCurrentUser();
                if (account == null){
                    Log.d("USER", "it work");
                    Intent intent = new Intent(ChooseSeatActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(ChooseSeatActivity.this, "Please login your account", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Intent intent = new Intent(ChooseSeatActivity.this, CustomerInformationActivity.class);
                Intent intent = new Intent(ChooseSeatActivity.this, PaymentActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putString("ROUTE", route);
//                bundle.putString("DATE", date);
//                bundle.putString("SEAT", seat);
                ticket.setNumberSeat(seat);
                String price = ticket.getPriceTicket().replaceAll("[^0-9]", "");
                int amount = Integer.parseInt(price);
                amount = amount * seatChoosed.size();
                ticket.setPriceTicket(amount+"VND");
                bundle.putSerializable("CustomerTicket", ticket);
                bundle.putSerializable("ACCOUNT", account);
                //intent.putExtra("to customer information",bundle);
                intent.putExtra("to payment", bundle);
                startActivity(intent);
            }
        });

    }
    private void mapping(){
        txtRoute = (TextView) findViewById(R.id.textviewRoute);
        txtDate = (TextView) findViewById(R.id.textviewDepartureDate);
        rtnBack = (ImageView) findViewById(R.id.backBefore);
        btnContinue = findViewById(R.id.buttonNextCusinfo);
        txtSeatChoose =findViewById(R.id.seatChoose);

        // Vé xe
        imgBus = findViewById(R.id.imageTicket);
        txtNameTicket = findViewById(R.id.nameTicket);
        txtBusNumber = findViewById(R.id.busNumber);
        txtTime = findViewById(R.id.departureTime);
        txtPrice = findViewById(R.id.priceTicket);

        // Lưu trữ tất cả các View ghế trong một mảng
        seats[0] = findViewById(R.id.seat_1a);
        seats[1] = findViewById(R.id.seat_1b);
        seats[2] = findViewById(R.id.seat_1a_upstair);
        seats[3] = findViewById(R.id.seat_1b_upstair);
        seats[4] = findViewById(R.id.seat_2a);
        seats[5] = findViewById(R.id.seat_2b);
        seats[6] = findViewById(R.id.seat_2a_upstair);
        seats[7] = findViewById(R.id.seat_2b_upstair);
        seats[8] = findViewById(R.id.seat_3a);
        seats[9] = findViewById(R.id.seat_3b);
        seats[10] = findViewById(R.id.seat_3a_upstair);
        seats[11] = findViewById(R.id.seat_3b_upstair);
        seats[12] = findViewById(R.id.seat_4a);
        seats[13] = findViewById(R.id.seat_4b);
        seats[14] = findViewById(R.id.seat_4a_upstair);
        seats[15] = findViewById(R.id.seat_4b_upstair);
        seats[16] = findViewById(R.id.seat_5a);
        seats[17] = findViewById(R.id.seat_5b);
        seats[18] = findViewById(R.id.seat_5a_upstair);
        seats[19] = findViewById(R.id.seat_5b_upstair);
        seats[20] = findViewById(R.id.seat_6a);
        seats[21] = findViewById(R.id.seat_6b);
        seats[22] = findViewById(R.id.seat_6a_upstair);
        seats[23] = findViewById(R.id.seat_6b_upstair);
        seats[24] = findViewById(R.id.seat_7a);
        seats[25] = findViewById(R.id.seat_7b);
        seats[26] = findViewById(R.id.seat_7a_upstair);
        seats[27] = findViewById(R.id.seat_7b_upstair);
        seats[28] = findViewById(R.id.seat_8a);
        seats[29] = findViewById(R.id.seat_8b);
        seats[30] = findViewById(R.id.seat_8a_upstair);
        seats[31] = findViewById(R.id.seat_8b_upstair);
        seats[32] = findViewById(R.id.seat_9a);
        seats[33] = findViewById(R.id.seat_9b);
        seats[34] = findViewById(R.id.seat_9a_upstair);
        seats[35] = findViewById(R.id.seat_9b_upstair);
        seats[36] = findViewById(R.id.seat_10a);
        seats[37] = findViewById(R.id.seat_10b);
        seats[38] = findViewById(R.id.seat_10a_upstair);
        seats[39] = findViewById(R.id.seat_10b_upstair);
    }

    private void checkDataSeat(){
        ArrayList<String> idVe = new ArrayList<>();
        DatabaseReference referenceVE = database.getReference("VE");
        referenceVE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String idTransition = dataSnapshot.child("idTransition").getValue(String.class);
                    String statusTicket = dataSnapshot.child("statusTicket").getValue(String.class);
                    if (idTransition.equals(ticket.getIdTransition()) && !statusTicket.equals("2")){
                        String keyVe = dataSnapshot.getKey();
                        Log.d("keyVe", keyVe);
                        idVe.add(keyVe);
                    }
                }

                ArrayList<String> numberSeats = new ArrayList<>();
                DatabaseReference referenceCT = database.getReference("CTVE");
                referenceCT.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot1) {
                        for (DataSnapshot dataSnapshot : snapshot1.getChildren()){
                            String idTicket = dataSnapshot.child("ticketId").getValue(String.class);
                            for (String element:idVe){
                                if(idTicket.equals(element)){
                                    String numberSeat = dataSnapshot.child("numberSeat").getValue(String.class);
                                    numberSeats.add(numberSeat);
                                }
                            }
                        }

                        for (int i = 0; i < seats.length; i++){
                            String seatId = getResources().getResourceEntryName(seats[i].getId());
                            seatId = seatId.substring(seatId.indexOf("_") + 1);
                            Log.d("seatId", seatId);
                            for (String element : numberSeats){
                                element = element.toLowerCase();
                                if (element.contains(" ")) {
                                    element = element.replace(" ", "_");
                                }
//                                String seatLetter = element.substring(1);
//                                String seatNumber = element.substring(0, 1);
//                                String bookedSeat = seatLetter + seatNumber;
                                Log.d("bookedSeat", element);
                                if (element.equals(seatId)){
                                    seats[i].setImageResource(R.drawable.booked_img);
                                    Log.d("BREAK", "break");
                                     break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(ChooseSeatActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ChooseSeatActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
