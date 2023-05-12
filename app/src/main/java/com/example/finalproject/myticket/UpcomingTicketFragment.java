package com.example.finalproject.myticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.search.ticket.PaymentActivity;
import com.example.finalproject.search.ticket.Ticket;
import com.example.finalproject.search.ticket.TicketAdapter;
import com.example.finalproject.search.ticket.ticket_interface.IClickTicketListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcomingTicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingTicketFragment extends Fragment {

    private ArrayList<Ticket> tickets;
    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;
    private LinearLayout linearLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.upcoming_ticket_fragment, container, false);

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

    private void setListTicket(){
        tickets = new ArrayList<>();
        tickets.add(new Ticket(R.drawable.limousine21v1, "Vinh Long - Ho Chi Minh", "Giường nằm 40 chỗ có toilet" + '\n' + "(Vinh Long - Ho Chi Minh)", "51A3-21212","9:00 - 11:30 30/04/2023", "30/04/2023", "A3", "250.000VND", "Lương Việt Hoàng", "1234567890","hoang@gmail.com"));
        tickets.add(new Ticket(R.drawable.limousine21v1, "Vinh Long - Ho Chi Minh", "Giường nằm 40 chỗ có toilet" + '\n' + "(Vinh Long - Ho Chi Minh)", "51A3-21212","9:00 - 11:30 30/04/2023", "30/04/2023", "A3", "250.000VND", "Lương Việt Hoàng", "1234567890","hoang@gmail.com"));
    }

    private void OnClickGotoDetailBookedTicket(Ticket ticket){
        Intent intent = new Intent(getContext(), PaymentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("CustomerTicket", ticket);
        bundle.putBoolean("from my ticket", true);
        intent.putExtra("to payment", bundle);
        startActivity(intent);
    }
}