package com.example.finalproject.admin.managefunction.VE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.search.ticket.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManageVeActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBookedTicket;
    private BookedTicketAdapter bookedTicketAdapter;
    private ArrayList<Ticket> tickets;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ImageView btnBack;
    private EditText edtPhoneNums;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ve);

        mapping();

        getBookedTickets();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        bookedTicketAdapter = new BookedTicketAdapter(tickets, ManageVeActivity.this);
        recyclerViewBookedTicket.setLayoutManager(layoutManager);
        recyclerViewBookedTicket.setAdapter(bookedTicketAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBookedTicketsWithPhone();
            }
        });
    }

    private void mapping(){
        recyclerViewBookedTicket = (RecyclerView) findViewById(R.id.recycleviewBookedTicket);
        tickets = new ArrayList<>();
        btnBack = (ImageView) findViewById(R.id.backBefore);
        edtPhoneNums = (EditText) findViewById(R.id.editPhoneNumbers);
        btnSearch = (Button) findViewById(R.id.buttonSearchTickets);
    }

    private void getBookedTickets() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        DatabaseReference referenceVe = database.getReference("VE");
        referenceVe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Ticket> fbVe = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String keyVe = dataSnapshot.getKey();
                            Ticket ticket = dataSnapshot.getValue(Ticket.class);
                            ticket.setIdTicket(keyVe);
                            fbVe.add(0, ticket);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tickets.clear();
                                tickets.addAll(fbVe);
                                bookedTicketAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ManageVeActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getBookedTicketsWithPhone() {
        String inputPhoneNums = edtPhoneNums.getText().toString();
        if (inputPhoneNums.isEmpty()){
            getBookedTickets();
            return;
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        DatabaseReference referenceVe = database.getReference("VE");
        referenceVe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Ticket> fbVe = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String keyVe = dataSnapshot.getKey();
                            Ticket ticket = dataSnapshot.getValue(Ticket.class);
                            ticket.setIdTicket(keyVe);
                            fbVe.add(0, ticket);
                        }

                        ArrayList<Ticket> filterVe = new ArrayList<>();
                        DatabaseReference referenceKH = database.getReference("KHACHHANG");
                        referenceKH.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    String keyKH = dataSnapshot.getKey();
                                    String fbPhoneNums = dataSnapshot.child("sdt").getValue(String.class);
                                    for (Ticket element : fbVe){
                                        if (element.getIdAccount().equals(keyKH)){
                                            if (fbPhoneNums.equals(inputPhoneNums)){
                                                filterVe.add(element);
                                            }
                                        }

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tickets.clear();
                                                tickets.addAll(filterVe);
                                                bookedTicketAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(ManageVeActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ManageVeActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}