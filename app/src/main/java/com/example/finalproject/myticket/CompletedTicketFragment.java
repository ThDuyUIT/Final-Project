package com.example.finalproject.myticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.search.ticket.Ticket;
import com.example.finalproject.search.ticket.TicketAdapter;
import com.example.finalproject.search.ticket.ticket_interface.IClickTicketListener;

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
                //OnClickGotoChooseSeat(ticket);
            }
        });
    }

    private void setListTicket(){
        tickets = new ArrayList<>();
//        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ có toilet" + '\n' + "(Vinh Long - Ho Chi Minh)", "9:00 - 11:30 30/04/2023", "250.000VND"));
//        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ có toilet" + '\n' + "(Vinh Long - Ho Chi Minh)", "9:00 - 11:30 30/04/2023", "250.000VND"));
    }
}