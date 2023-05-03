package com.example.finalproject.search.ticket;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.search.SearchFragment;
import com.example.finalproject.search.list_city_points.ChooseCityActivity;
import com.example.finalproject.search.list_city_points.City;
import com.example.finalproject.search.list_city_points.CityAdapter;
import com.example.finalproject.search.ticket.ticket_interface.IClickTicketListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
        txtRoute.setText(bundle.getString("START") + '-' + bundle.getString("END"));
        txtDate.setText(bundle.getString("DATE"));

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
        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 30 chỗ có toilet", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ có toilet", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ có toilet", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ có toilet", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ có toilet", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ có toilet", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ có toilet", "9:00 - 11:30", "250.000VND"));
        tickets.add(new Ticket(R.drawable.limousine21v1, "Giường nằm 40 chỗ có toilet", "9:00 - 11:30", "250.000VND"));
    }

    private void OnClickGotoChooseSeat(Ticket ticket) {
        Intent intent = new Intent(this, ChooseSeatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Ticket route / time / ", ticket);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
