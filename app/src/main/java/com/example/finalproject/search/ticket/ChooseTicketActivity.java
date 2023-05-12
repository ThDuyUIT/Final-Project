package com.example.finalproject.search.ticket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.search.ChuyenXe;
import com.example.finalproject.search.ticket.ticket_interface.IClickTicketListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChooseTicketActivity extends AppCompatActivity{
    private ArrayList<Ticket> tickets;
    private ListView listView;
    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;
    private ImageView btnBack;
    private TextView txtRoute, txtDate;

//    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new
//            ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//        @Override
//        public void onActivityResult(ActivityResult result) {
//            if(result.getResultCode() == RESULT_OK){
//                Intent intent = result.getData();
//                Bundle bundle = intent.getBundleExtra("searchTicketInfo");
//                if (bundle!= null) {
//                    txtRoute.setText(bundle.getString("START") + '-' + bundle.getString("END"));
//                    txtDate.setText(bundle.getString("DATE"));
//                }
//            }
//        }
//    });

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ticket);
        mapping();

        //listView.setAdapter(ticketAdapter);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("searchTicketInfo");
        if(bundle == null){
            return;
        }

        boolean fromChuyenXeAdapter = bundle.getBoolean("from ChuyenXeAdapter", false);
        if(fromChuyenXeAdapter){
            ChuyenXe chuyenXe = (ChuyenXe) bundle.get("Featured Route");
            txtRoute.setText(chuyenXe.getTitle().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(new Date());
            txtDate.setText(formattedDate);
        }else {
            txtRoute.setText(bundle.getString("START") + " - " + bundle.getString("END"));
            txtDate.setText(bundle.getString("DATE"));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ticketAdapter);

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
        setListTicket();

        //listView = (ListView) findViewById(R.id.listviewTicket);
        //ticketAdapter = new TicketAdapter(ChooseTicketActivity.this, R.layout.line_ticket, tickets);

        recyclerView = (RecyclerView) findViewById(R.id.recycleviewTicket);
        ticketAdapter = new TicketAdapter(tickets, new IClickTicketListener() {
            @Override
            public void onClickTicketListener(Ticket ticket) {
                OnClickGotoChooseSeat(ticket);
            }
        });

        txtRoute = (TextView) findViewById(R.id.textviewRoute);
        txtDate = (TextView) findViewById(R.id.textviewDepartureDate);
        btnBack = (ImageView) findViewById(R.id.backBefore);
    }

    private void setListTicket(){
        tickets = new ArrayList<>();
        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ (WC)","51A3-21212", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v2, "Giường nằm 40 chỗ","52A2-21216", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v3, "Giường nằm 40 chỗ (WC)","52A2-21216", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v4, "Giường nằm 40 chỗ","52A2-21216", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v5, "Giường nằm 40 chỗ","52A2-21216", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v6, "Giường nằm 40 chỗ (WC)","52A2-21216", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v3, "Giường nằm 40 chỗ","", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ (WC)","52A2-21216", "9:00 - 11:30", "250.000VND"));
    }

    private void OnClickGotoChooseSeat(Ticket ticket) {
        String route = txtRoute.getText().toString();
        String date = txtDate.getText().toString();

        ticket.setNameRoute(route);
        ticket.setDepartureDate(date);

        Intent intent = new Intent(this, ChooseSeatActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putString("ROUTE", route);
//        bundle.putString("DATE", date);
        bundle.putSerializable("busInformation", ticket);
        intent.putExtra("to choose seat", bundle);
        startActivity(intent);
    }
}