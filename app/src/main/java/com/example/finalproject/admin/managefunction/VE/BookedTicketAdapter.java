package com.example.finalproject.admin.managefunction.VE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.admin.managefunction.CHUYENXE.AddRouteActivity;
import com.example.finalproject.search.ticket.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookedTicketAdapter extends RecyclerView.Adapter<BookedTicketAdapter.bookedticketViewHolder>{
    private List<Ticket> ticketList;
    private Context context;

    public BookedTicketAdapter(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }

    @Override
    public bookedticketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_booked_ticket, parent, false);
        return new BookedTicketAdapter.bookedticketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bookedticketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        if (ticket == null) {
            return;
        }

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
            Date date = inputFormat.parse(ticket.getIdTicket().substring(2, 10));

            // Format the date into the desired format "dd/MM/yyyy"
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = outputFormat.format(date);

            holder.txtBookedDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("KHACHHANG");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String keyKH = dataSnapshot.getKey();
                    if (keyKH.equals(ticket.getIdAccount())){
                        String fbNameKH = dataSnapshot.child("hoTen").getValue(String.class);
                        String fbPhoneNums = dataSnapshot.child("sdt").getValue(String.class);
                        holder.txtNameKH.setText(fbNameKH);
                        holder.txtPhoneNums.setText(fbPhoneNums);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        reference = database.getReference("CHUYENXE");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String keyCX = dataSnapshot.getKey();
                    if (keyCX.equals(ticket.getIdTransition())){
                        Ticket fbTicket = dataSnapshot.getValue(Ticket.class);
                        ticket.setBusNumber(fbTicket.getBusNumber());
                        ticket.setDepartureDate(fbTicket.getDepartureDate());
                        ticket.setDepartureTime(fbTicket.getDepartureTime());
                        ticket.setStartPoint(fbTicket.getStartPoint());
                        ticket.setEndPoint(fbTicket.getEndPoint());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        ArrayList<String> seats = new ArrayList<>();
        reference = database.getReference("CTVE");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String fbidticket= dataSnapshot.child("ticketId").getValue(String.class);
                    String fbnumberSeat = dataSnapshot.child("numberSeat").getValue(String.class);
                    if (fbidticket.equals(ticket.getIdTicket())){
                        seats.add(fbnumberSeat);
                    }
                }

                String numberSeat = "";
                for (String element : seats){
                    numberSeat += element + " ";
                }

                ticket.setNumberSeat(numberSeat);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddRouteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("INFO ROUTE",ticket);
                bundle.putBoolean("from mange ve", true);
                intent.putExtra("from manage route", bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ticketList != null){
            return ticketList.size();
        }
        return 0;
    }

    public class bookedticketViewHolder extends RecyclerView.ViewHolder {


        private TextView txtNameKH, txtPhoneNums, txtBookedDate;
        private LinearLayout layout;


        public bookedticketViewHolder(@NonNull View itemView) {
            super(itemView);
           txtNameKH = (TextView) itemView.findViewById(R.id.textviewNameKH);
           txtPhoneNums = (TextView) itemView.findViewById(R.id.textviewPhoneNums);
           txtBookedDate = (TextView) itemView.findViewById(R.id.textviewBookedDate);
           layout = (LinearLayout) itemView.findViewById(R.id.linearLayoutBookedTicket);
        }
    }
}
