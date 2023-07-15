package com.example.finalproject.admin.reportfunction;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.search.ticket.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {
    private TextView txtDatePicker, txtTotalVe, txtCancel, txtComplete, txtRevenue;
    private Button btnStart;
    private ImageView btnBack;
    private ProgressBar pgbCancel, pgbComplete, pgbRevenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mapping();

        txtDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);

                // Create a DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        // Month is zero-based, so increment it by 1
                        int month = monthOfYear + 1;

                        // Format the selected date as "MM/yyyy"
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%04d", month, year);

                        // Set the selected date to txtDatePicker
                        txtDatePicker.setText(selectedDate);
                    }
                }, year, month, 1);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Ticket> tickets = new ArrayList<>();
                String condition = txtDatePicker.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference referenceVe = database.getReference("VE");
                referenceVe.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String keyVe =dataSnapshot.getKey();
                            String formattedDate= "";
                            try {
                                // Parse the input string into a Date object
                                SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
                                Date date = inputFormat.parse(keyVe.replace("VE",""));

                                // Format the date into "MM/yyyy"
                                SimpleDateFormat outputFormat = new SimpleDateFormat("MM/yyyy");
                                formattedDate = outputFormat.format(date);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (condition.equals(formattedDate)){
                                Ticket ticket = dataSnapshot.getValue(Ticket.class);
                                tickets.add(ticket);
                            }
                        }

                        int bestRevenue = 0;
                        int revenue = 0;
                        int countCancel = 0;
                        int countComplete = 0;
                        int index = 0;
                        for (Ticket element : tickets){
                            bestRevenue += Integer.parseInt(element.getPriceTotal().replace("VND",""));

                            if (element.getStatusTicket().equals("2")){
                                countCancel++;
                            }

                            if (element.getStatusTicket().equals("1")){
                                countComplete++;
                                Log.d("total price", element.getPriceTotal());
                                revenue += Integer.parseInt(element.getPriceTotal().replace("VND",""));
                                Log.d("revenue", revenue + "");
                            }
                            index++;
                        }

                        txtTotalVe.setText(index+"");
                        pgbCancel.setMax(index);
                        pgbComplete.setMax(index);

                        txtCancel.setText(countCancel + "/" + index);
                        pgbCancel.setProgress(countCancel);

                        txtComplete.setText(countComplete+ "/" + index);
                        pgbComplete.setProgress(countComplete);

                        txtRevenue.setText(revenue + "/" + bestRevenue + "VND");
                        pgbRevenue.setMax(bestRevenue);
                        pgbRevenue.setProgress(revenue);


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });

    }

    private void mapping(){
        txtDatePicker = (TextView) findViewById(R.id.textviewDatePicker);
        //txtPickedMonth = (TextView) findViewById(R.id.textviewPickedDate);
        btnStart = (Button) findViewById(R.id.buttonStart);
        txtTotalVe = (TextView) findViewById(R.id.textviewTotalVe);
        txtCancel = (TextView) findViewById(R.id.textviewCancelRate);
        txtComplete = (TextView) findViewById(R.id.textviewCompleteRate);
        btnBack = (ImageView) findViewById(R.id.backBefore);
        pgbCancel = (ProgressBar) findViewById(R.id.progressCancel);
        pgbComplete = (ProgressBar) findViewById(R.id.progressComplete);
        pgbRevenue = (ProgressBar) findViewById(R.id.progressRevenue);
        txtRevenue = (TextView) findViewById(R.id.textviewRevenueRate);
    }
}