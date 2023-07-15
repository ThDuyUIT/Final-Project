package com.example.finalproject.admin.managefunction.CHUYENXE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.search.ticket.Ticket;
import com.example.finalproject.search.ticket.TicketAdapter;
import com.example.finalproject.search.ticket.ticket_interface.IClickTicketListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageChuyenXeActivity extends AppCompatActivity {
    private Button btnAddNew;
    private ImageView btnBack;
    private RecyclerView recyclerViewCX;
    private ArrayList<Ticket> tickets;
    private TicketAdapter ticketAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_chuyen_xe);

        mapping();

        setListRoute();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ticketAdapter = new TicketAdapter(tickets, new IClickTicketListener() {
            @Override
            public void onClickTicketListener(Ticket ticket) {
                OnClickGotoChooseSeat(ticket);
            }
        }, ManageChuyenXeActivity.this);
        recyclerViewCX.setLayoutManager(layoutManager);
        recyclerViewCX.setAdapter(ticketAdapter);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageChuyenXeActivity.this, AddRouteActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
    }

    private void mapping(){
        btnAddNew = (Button) findViewById(R.id.buttonAddNewRoute);
        recyclerViewCX = (RecyclerView) findViewById(R.id.recycleviewRoute);

        btnBack = (ImageView) findViewById(R.id.backBefore);
    }

    private void setListRoute(){
        tickets = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referenceChuyenXe = database.getReference("CHUYENXE");

        referenceChuyenXe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Ticket ticket = dataSnapshot.getValue(Ticket.class);
                    ticket.setIdTransition(dataSnapshot.getKey().toString());
                    getInfoXe(database, ticket);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ManageChuyenXeActivity.this, "Get list ticket fail.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getInfoXe(FirebaseDatabase database, Ticket ticket){
        DatabaseReference referenceXe = database.getReference("XE");
        referenceXe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String keyXe = dataSnapshot.getKey();
                    if (ticket.getBusNumber().equals(keyXe)){
                        Ticket ticket1 = dataSnapshot.getValue(Ticket.class);
                        String fbimgxe = dataSnapshot.child("anhdaidienXe").getValue(String.class);
                        ticket.setNameTicket(ticket1.getNameTicket());
                        ticket.setSrcImg(fbimgxe);
                        //ticket.setResourceID(R.drawable.limousine21v1);
                        tickets.add(ticket);
                    }
                }
                ticketAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ManageChuyenXeActivity.this, "Get bus info fail.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void OnClickGotoChooseSeat(Ticket ticket) {
        Intent intent = new Intent(ManageChuyenXeActivity.this, AddRouteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("INFO ROUTE", ticket);
        intent.putExtra("from manage route", bundle);
        startActivity(intent);
    }
}