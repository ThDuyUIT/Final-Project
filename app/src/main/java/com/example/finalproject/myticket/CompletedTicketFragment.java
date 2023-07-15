package com.example.finalproject.myticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.authentication.Account;
import com.example.finalproject.search.ticket.PaymentActivity;
import com.example.finalproject.search.ticket.Ticket;
import com.example.finalproject.search.ticket.TicketAdapter;
import com.example.finalproject.search.ticket.ticket_interface.IClickTicketListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompletedTicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompletedTicketFragment extends Fragment {

    private ArrayList<Ticket> tickets;
    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;
    private LinearLayout linearLayout;
    private String idAccount;
    private Account account;

    public CompletedTicketFragment(String idAccount, Account account) {
        this.idAccount = idAccount;
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.completed_ticket_fragment, container, false);

        mapping(view);

        if(ticketAdapter.getItemCount()>0){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(ticketAdapter);

            recyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }else{
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        return view;
    }

    private void mapping(View view){
        setListTicket();
        linearLayout = (LinearLayout) view.findViewById(R.id.linearlayoutNoTickets);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewTicket);
        ticketAdapter = new TicketAdapter(tickets, new IClickTicketListener() {
            @Override
            public void onClickTicketListener(Ticket ticket) {
                OnClickGotoDetailBookedTicket(ticket);
            }
        });
    }

    private void setListTicket() {
        tickets = new ArrayList<>();
        ArrayList<Ticket> arrTicket = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referenceVe = database.getReference("VE");
        referenceVe.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String fbstatusTicket= dataSnapshot.child("statusTicket").getValue(String.class);
                    String keyVE = dataSnapshot.getKey();
                    Ticket ticket = dataSnapshot.getValue(Ticket.class);
                    if (idAccount.equals(ticket.getIdAccount()) && fbstatusTicket.equals("1")){
                        ticket.setIdTicket(keyVE);
                        arrTicket.add(ticket);
                    }
                }

                DatabaseReference referenceCX = database.getReference("CHUYENXE");
                referenceCX.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String keyCX = dataSnapshot.getKey();
                            for (Ticket element : arrTicket){
                                if (element.getIdTransition().equals(keyCX)){
                                    element.setBusNumber(dataSnapshot.child("busNumber").getValue(String.class));
                                    element.setPriceTicket(dataSnapshot.child("priceTicket").getValue(String.class));
                                    element.setDepartureDate(dataSnapshot.child("departureDate").getValue(String.class));
                                    element.setDepartureTime(dataSnapshot.child("departureTime").getValue(String.class));
                                    element.setStartPoint(dataSnapshot.child("startPoint").getValue(String.class));
                                    element.setEndPoint(dataSnapshot.child("endPoint").getValue(String.class));
                                }
                            }
                        }

                        DatabaseReference referenceXE = database.getReference("XE");
                        referenceXE.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    String keyXE = dataSnapshot.getKey();
                                    for (Ticket element : arrTicket){
                                        if (element.getBusNumber().equals(keyXE)){
                                            String nameBus = dataSnapshot.child("nameTicket").getValue(String.class);
                                            String srcImage = dataSnapshot.child("anhdaidienXe").getValue(String.class);
                                            element.setNameTicket(nameBus);
                                            element.setSrcImg(srcImage);
                                            Log.d("BUSNAME", element.getNameTicket());
                                            tickets.add(element);
                                        }
                                    }
                                }

                                Log.d("SIZETICKETS1", String.valueOf(tickets.size()));
                                if(ticketAdapter.getItemCount()>0){
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(ticketAdapter);

                                    recyclerView.setVisibility(View.VISIBLE);
                                    linearLayout.setVisibility(View.GONE);
                                }else{
                                    linearLayout.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void OnClickGotoDetailBookedTicket(Ticket ticket){
        Intent intent = new Intent(getContext(), PaymentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("CustomerTicket", ticket);
        bundle.putSerializable("ACCOUNT", account);
        bundle.putBoolean("from my ticket", true);
        intent.putExtra("to payment", bundle);
        startActivity(intent);
    }
}