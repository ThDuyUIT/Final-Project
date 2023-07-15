package com.example.finalproject.search.ticket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.authentication.Account;
import com.example.finalproject.search.ChuyenXe;
import com.example.finalproject.search.ticket.ticket_interface.IClickTicketListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private String idStartPoint, idEndPoint, startDate, idAccount;
    private Account account;

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

        setListTicket();

        //listView.setAdapter(ticketAdapter);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("searchTicketInfo");
        if(bundle == null){
            return;
        }

        account = (Account) bundle.get("ACCOUNT");
        idAccount = bundle.getString("ID ACCOUNT");
        idStartPoint = bundle.getString("ID START");
        idEndPoint = bundle.getString("ID END");
        startDate = bundle.getString("DATE");


//        Log.d("START POINT", idStartPoint);
//        Log.d("END POINT", idEndPoint);
//        Log.d("DATE", startDate);

        boolean fromChuyenXeAdapter = bundle.getBoolean("from ChuyenXeAdapter", false);
        if(fromChuyenXeAdapter){
            ChuyenXe chuyenXe = (ChuyenXe) bundle.get("Featured Route");
            String nameCity = bundle.getString("City");
            txtRoute.setText(nameCity);
            String idCity = chuyenXe.getTitle();
            String[] parts = idCity.split(" - ");
            String idStart = parts[0];
            String idEnd = parts[1];
            idStartPoint = idStart;
            idEndPoint = idEnd;
            account = chuyenXe.getAccount();
            idAccount = chuyenXe.getIdAccount();

            Log.d("idStartPoint", idStartPoint);
            Log.d("idEndPoint", idEndPoint);


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(new Date());
            txtDate.setText(formattedDate);
            startDate =formattedDate;
            Log.d("startDate", startDate);
        }else {
            txtRoute.setText(bundle.getString("START") + " - " + bundle.getString("END"));
            txtDate.setText(bundle.getString("DATE"));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ticketAdapter = new TicketAdapter(tickets, new IClickTicketListener() {
            @Override
            public void onClickTicketListener(Ticket ticket) {
                OnClickGotoChooseSeat(ticket);
            }
        });
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
        //setListTicket();

        //listView = (ListView) findViewById(R.id.listviewTicket);
        //ticketAdapter = new TicketAdapter(ChooseTicketActivity.this, R.layout.line_ticket, tickets);

        recyclerView = (RecyclerView) findViewById(R.id.recycleviewTicket);
//        ticketAdapter = new TicketAdapter(tickets, new IClickTicketListener() {
//            @Override
//            public void onClickTicketListener(Ticket ticket) {
//                OnClickGotoChooseSeat(ticket);
//            }
//        });

        txtRoute = (TextView) findViewById(R.id.textviewRoute);
        txtDate = (TextView) findViewById(R.id.textviewDepartureDate);
        btnBack = (ImageView) findViewById(R.id.backBefore);
    }

    private void setListTicket(){
        tickets = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referenceChuyenXe = database.getReference("CHUYENXE");

        referenceChuyenXe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Ticket ticket = dataSnapshot.getValue(Ticket.class);
                    String idTransition = dataSnapshot.getKey();
                    if (ticket.getStartPoint().equals(idStartPoint)
                        && ticket.getEndPoint().equals(idEndPoint)
                        && ticket.getDepartureDate().equals(startDate)){
                        ticket.setIdTransition(idTransition);
                        getInfoXe(database, ticket);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ChooseTicketActivity.this, "Get list ticket fail.", Toast.LENGTH_LONG).show();
            }
        });

//        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ (WC)","51A3-21212", "9:00 - 11:30", "250.000VND"));
//        tickets.add(new Ticket(R.drawable.limousine21v2, "Giường nằm 40 chỗ","52A2-21216", "9:00 - 11:30", "250.000VND"));
//        tickets.add(new Ticket(R.drawable.limousine21v3, "Giường nằm 40 chỗ (WC)","52A2-21216", "9:00 - 11:30", "250.000VND"));
//        tickets.add(new Ticket(R.drawable.limousine21v4, "Giường nằm 40 chỗ","52A2-21216", "9:00 - 11:30", "250.000VND"));
//        tickets.add(new Ticket(R.drawable.limousine21v5, "Giường nằm 40 chỗ","52A2-21216", "9:00 - 11:30", "250.000VND"));
//        tickets.add(new Ticket(R.drawable.limousine21v6, "Giường nằm 40 chỗ (WC)","52A2-21216", "9:00 - 11:30", "250.000VND"));
//        tickets.add(new Ticket(R.drawable.limousine21v3, "Giường nằm 40 chỗ","", "9:00 - 11:30", "250.000VND"));
//        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ (WC)","52A2-21216", "9:00 - 11:30", "250.000VND"));
    }

    private void OnClickGotoChooseSeat(Ticket ticket) {
        String route = txtRoute.getText().toString();
        String date = txtDate.getText().toString();

        ticket.setNameRoute(route);
        ticket.setDepartureDate(date);
        if (idAccount != null) {
            ticket.setIdAccount(idAccount);
        }

        Intent intent = new Intent(this, ChooseSeatActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putString("ROUTE", route);
//        bundle.putString("DATE", date);
        bundle.putSerializable("ACCOUNT", account);
        bundle.putSerializable("busInformation", ticket);
        intent.putExtra("to choose seat", bundle);
        startActivity(intent);
    }

    private void getInfoXe(FirebaseDatabase database, Ticket ticket){
        DatabaseReference referenceXe = database.getReference("XE");
        referenceXe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String keyXe = dataSnapshot.getKey();
                    Log.d("KEY XE", keyXe);
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
                Toast.makeText(ChooseTicketActivity.this, "Get bus info fail.", Toast.LENGTH_LONG).show();
            }
        });
    }
}