package com.example.finalproject.admin.managefunction.CHUYENXE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.admin.managefunction.VE.ManageVeActivity;
import com.example.finalproject.admin.managefunction.XE.ManageXeActivity;
import com.example.finalproject.admin.managefunction.XE.Xe;
import com.example.finalproject.search.calendar.ChooseDateActivity;
import com.example.finalproject.search.list_city_points.ChooseCityActivity;
import com.example.finalproject.search.ticket.Ticket;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddRouteActivity extends AppCompatActivity {
    private TextView txtStart, txtEnd, txtDate, txtTime, txtXe, btnDelete, txtSeat, txtPaymentStt, txtTicketStt, txtTitle;
    private EditText edtPrice;
    private Button btnAdd;
    private String idStartPoint;
    private String idEndPoint;
    private String changedsttPayment;
    private String changedsttTicket;
    private String changedsttFeatured;
    private Xe xe;
    private Ticket ticket;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ImageView btnBack;
    private boolean isBookedRoute = false;
    private boolean fromManageVe = false;
    private RadioGroup rdgPaymentStatus, rdgTicketStatus, rdgFeaturedRoute;

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                Bundle bundle = intent.getBundleExtra("selectedValue");
                if (bundle!= null) {
                    String strNamePoint = bundle.getString("namepoint");
                    String strNameOption = bundle.getString("nameoption");
                    if (strNamePoint.equals("Start point")) {
                        txtStart.setText(strNameOption);
                        idStartPoint = bundle.getString("idoption");
                        //Log.d("ID start point", idStartPoint);
                    } else {
                        txtEnd.setText(strNameOption);
                        idEndPoint = bundle.getString("idoption");
                        //Log.d("ID end point", idEndPoint);
                    }
                }

                long selectedDate = intent.getLongExtra("selectedDate", 0);
                if (selectedDate != 0) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(selectedDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                    txtDate.setText(dateFormat.format(calendar.getTime()));
                }

                bundle = intent.getBundleExtra("Detail Info Xe");
                if (bundle != null){
                    xe = (Xe) bundle.get("INFO XE");
                    txtXe.setText(xe.getIdNumber());
                }

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        mapping();

        Bundle bundle = getIntent().getBundleExtra("from manage route");
        if (bundle != null){
            ticket = (Ticket) bundle.getSerializable("INFO ROUTE");
            getNameCity(ticket.getStartPoint(), ticket.getEndPoint());
            idStartPoint = ticket.getStartPoint();
            idEndPoint = ticket.getEndPoint();
            txtDate.setText(ticket.getDepartureDate());
            txtTime.setText(ticket.getDepartureTime());
            txtXe.setText(ticket.getBusNumber());
            edtPrice.setText(ticket.getPriceTicket());
            btnAdd.setText("Update");

            getFeatured();

            updateFeatured();

            checkBookedRoute();

            fromManageVe = bundle.getBoolean("from mange ve");
            if (fromManageVe){
                edtPrice.setText(ticket.getPriceTotal());
                txtSeat.setVisibility(View.VISIBLE);
                txtSeat.setText(ticket.getNumberSeat());

                rdgPaymentStatus.setVisibility(View.VISIBLE);
                rdgTicketStatus.setVisibility(View.VISIBLE);
                rdgFeaturedRoute.setVisibility(View.GONE);

                txtPaymentStt.setVisibility(View.VISIBLE);
                txtTicketStt.setVisibility(View.VISIBLE);

                txtTitle.setText("Manage Ticket");

                getStatus();

                updateStatus();


            }
        }

        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromManageVe || isBookedRoute){
                    return;
                }

                Intent intent = new Intent(AddRouteActivity.this, ChooseCityActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("text", "Start point");
                bundle.putInt("drawableLeft", R.drawable.point_start);

                intent.putExtra("valueTitle", bundle);
                activityResultLauncher.launch(intent);
            }
        });

        txtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromManageVe || isBookedRoute){
                    return;
                }

                Intent intent = new Intent(AddRouteActivity.this, ChooseCityActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("text", "Where to?");
                bundle.putInt("drawableLeft", R.drawable.point_end);

                intent.putExtra("valueTitle", bundle);
                activityResultLauncher.launch(intent);
                //startActivity(intent);
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromManageVe || isBookedRoute){
                    return;
                }

                Intent intent = new Intent(AddRouteActivity.this, ChooseDateActivity.class);
                activityResultLauncher.launch(intent);
            }
        });

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromManageVe || isBookedRoute){
                    return;
                }

                pickTime();
            }
        });

        txtXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromManageVe || isBookedRoute){
                    return;
                }

                Intent intent = new Intent(AddRouteActivity.this, ManageXeActivity.class);
                intent.putExtra("from add route", true);
                activityResultLauncher.launch(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fromManageVe){

                    DatabaseReference ref = database.getReference("VE");

                    Map<String, Object> firebaseVe = new HashMap<>();
                    firebaseVe.put("statusPayment", changedsttPayment);
                    firebaseVe.put("statusTicket", changedsttTicket);

                    ref = ref.child(ticket.getIdTicket());
                    ref.updateChildren(firebaseVe ).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddRouteActivity.this, "Update successfull", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddRouteActivity.this, ManageVeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(AddRouteActivity.this, "Update fail", Toast.LENGTH_LONG).show();
                        }
                    });

                    return;
                }

                if (isBookedRoute){
                    DatabaseReference ref = database.getReference("CHUYENXE");

                    Map<String, Object> firebaseChuyenXe = new HashMap<>();
                    firebaseChuyenXe.put("busNumber", txtXe.getText().toString());
                    if (changedsttFeatured == null){
                        firebaseChuyenXe.put("featuredRoute", "0");
                    }else{
                        firebaseChuyenXe.put("featuredRoute", changedsttFeatured);
                    }

                    ref = ref.child(ticket.getIdTransition());
                    ref.updateChildren(firebaseChuyenXe).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddRouteActivity.this, "Upload featured route successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddRouteActivity.this, ManageChuyenXeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(AddRouteActivity.this, "Fail upload featured route", Toast.LENGTH_LONG).show();
                        }
                    });
                    return;
                }

                DatabaseReference ref = database.getReference("CHUYENXE");

                Map<String, Object> firebaseChuyenXe = new HashMap<>();
                firebaseChuyenXe.put("startPoint", idStartPoint);
                firebaseChuyenXe.put("endPoint", idEndPoint);
                firebaseChuyenXe.put("departureDate", txtDate.getText().toString());
                firebaseChuyenXe.put("departureTime", txtTime.getText().toString());
                firebaseChuyenXe.put("busNumber", txtXe.getText().toString());
                if (changedsttFeatured == null){
                    firebaseChuyenXe.put("featuredRoute", "0");
                }else{
                    firebaseChuyenXe.put("featuredRoute", changedsttFeatured);
                }

                String prices = edtPrice.getText().toString().replace("VND","")+"VND";
                firebaseChuyenXe.put("priceTicket", prices);

                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
                String currentTime = dateFormat.format(now);
                String keyCX = "";
                if ( bundle != null){
                    keyCX = ticket.getIdTransition();
                }else {
                    keyCX = "CX" + currentTime;
                }

                ref = ref.child(keyCX);
                ref.updateChildren(firebaseChuyenXe).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddRouteActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddRouteActivity.this, ManageChuyenXeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddRouteActivity.this, "Fail Add New", Toast.LENGTH_LONG).show();
                    }
                });
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromManageVe){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddRouteActivity.this);
                    builder.setTitle("Confirm Delete")
                            .setMessage("Are you sure you want to delete this ticket?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // User clicked Delete button, proceed with deletion
                                    deleteTicket();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // User clicked Cancel button, do nothing
                                }
                            })
                            .show();
                    return;
                }

                if (isBookedRoute && !fromManageVe){
                    Toast.makeText(AddRouteActivity.this, "Can't delete booked route", Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AddRouteActivity.this);
                builder.setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this route?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked Delete button, proceed with deletion
                                deleteRoute();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked Cancel button, do nothing
                            }
                        })
                        .show();
            }
        });
    }

    private void mapping(){
        txtTitle = (TextView) findViewById(R.id.textviewTitle);
        txtStart = (TextView) findViewById(R.id.textViewStartPoint);
        txtEnd = (TextView) findViewById(R.id.textViewEndPoint);
        txtDate = (TextView) findViewById(R.id.textviewDepartureDate);
        txtTime = (TextView) findViewById(R.id.textviewDepartureTime);
        txtXe = (TextView) findViewById(R.id.textviewXe);
        edtPrice = (EditText) findViewById(R.id.edittextPrice);
        btnAdd = (Button) findViewById(R.id.buttonAddRoute);
        btnBack = (ImageView) findViewById(R.id.backBefore);
        btnDelete = (TextView) findViewById(R.id.textviewDeleteRoute);
        txtSeat = (TextView) findViewById(R.id.textviewSeat);
        rdgPaymentStatus = (RadioGroup) findViewById(R.id.radiogroupPaymentStatus);
        rdgTicketStatus = (RadioGroup) findViewById(R.id.radiogroupTicketStatus);
        txtPaymentStt = (TextView) findViewById(R.id.textviewTitlePaymentStt);
        txtTicketStt = (TextView) findViewById(R.id.textviewTitleTicketStt);
        rdgFeaturedRoute = (RadioGroup) findViewById(R.id.radiogroupFeatureRoute);
    }

    private void pickTime(){
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        calendar.set(0, 0, 0, i, i1);
                        txtTime.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, hours, minutes, true);
        timePickerDialog.show();
    }

    private void getNameCity(String idStart, String idEnd){
        DatabaseReference referenceCity = database.getReference("DIADIEM");
        referenceCity.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String keyCity = dataSnapshot.getKey();
                    if (keyCity.equals(idStart)){
                        String nameCity = dataSnapshot.child("nameCity").getValue(String.class);
                        txtStart.setText(nameCity);
                    }

                    if (keyCity.equals(idEnd)){
                        String nameCity = dataSnapshot.child("nameCity").getValue(String.class);
                        txtEnd.setText(nameCity);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AddRouteActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkBookedRoute(){
        DatabaseReference referenceVe = database.getReference("VE");
        referenceVe.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String fbidRoute = dataSnapshot.child("idTransition").getValue(String.class);
                    if (fbidRoute.equals(ticket.getIdTransition())){
                        //btnAdd.setEnabled(false);
                        isBookedRoute = true;
                        return;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void deleteRoute() {
        DatabaseReference referenceCX = FirebaseDatabase.getInstance().getReference("CHUYENXE");
        referenceCX.child(ticket.getIdTransition()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddRouteActivity.this, "Route deleted successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddRouteActivity.this, ManageChuyenXeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(AddRouteActivity.this, "Failed to delete route", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteTicket() {
        DatabaseReference referenceVE = FirebaseDatabase.getInstance().getReference("VE");
        referenceVE.child(ticket.getIdTicket()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddRouteActivity.this, "Ticket deleted successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddRouteActivity.this, ManageVeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(AddRouteActivity.this, "Failed to delete ticket", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getStatus(){
        for (int i = 0; i < rdgPaymentStatus.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rdgPaymentStatus.getChildAt(i);
            if (ticket.getStatusPayment().equals(i+"")) {
                radioButton.setChecked(true);// Select the matching RadioButton
                changedsttPayment = i+"";
                break;
            }
        }

        for (int i = 0; i < rdgTicketStatus.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rdgTicketStatus.getChildAt(i);
            Log.d(i+"", rdgTicketStatus.getChildAt(i).toString());
            if (ticket.getStatusTicket().equals(i+"")) {
                radioButton.setChecked(true); // Select the matching RadioButton
                changedsttTicket = i+"";
                break;
            }
        }
    }

    private void updateStatus(){
        rdgPaymentStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButtonUnpaid:
                        changedsttPayment = "0";
                        break;
                    case R.id.radioButtonPaid:
                        changedsttPayment = "1";
                        break;
                }

            }
        });

        rdgTicketStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d("i", i+"");
                switch (i){
                    case R.id.radioButtonUpcomming:
                        changedsttTicket = "0";
                        break;
                    case R.id.radioButtonComplete:
                        changedsttTicket = "1";
                        break;
                    case R.id.radioButtonCancel:
                        changedsttTicket = "2";
                        break;
                }

            }
        });
    }

    private void getFeatured(){
        for (int i = 0; i < rdgFeaturedRoute.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rdgFeaturedRoute.getChildAt(i);
            if (ticket.getFeaturedRoute()!=null && ticket.getFeaturedRoute().equals(i+"")) {
                radioButton.setChecked(true);// Select the matching RadioButton
                changedsttFeatured = i+"";
                break;
            }
        }
    }

    private void updateFeatured(){
        rdgFeaturedRoute.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButtonUnFeatureless:
                        changedsttFeatured = "0";
                        break;
                    case R.id.radioButtonFeature:
                        changedsttFeatured = "1";
                        break;
                }

            }
        });
    }

}