package com.example.finalproject.search.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.authentication.Account;
import com.example.finalproject.zalopay.API.CreateOrder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPaySDK;

public class PaymentActivity extends AppCompatActivity {
    private TextView txtRouteTicket, txtTrip, txtNameTicket, txtBusNumber, txtPrice, txtSeatChoose, txtPaymentStt;
    private TextView txtFullName, txtPhone, txtEmail;
    private ImageView rtnBack;
    private Button btnConfirm;
    private Account account;
    private DatabaseReference ref;
    private Ticket ticket;
    private RadioGroup rdgPayments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mapping();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        Bundle bundle = getIntent().getBundleExtra("to payment");
        if(bundle == null){
            return;
        }

        Boolean isFromMyTicket = bundle.getBoolean("from my ticket", false);
        if (isFromMyTicket){
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeContainButton);
            relativeLayout.setVisibility(View.GONE);
        }

        ticket = (Ticket) bundle.get("CustomerTicket");
        account = (Account) bundle.get("ACCOUNT");



        if (!isFromMyTicket) {
            txtPaymentStt.setText("Unpaid");
            txtRouteTicket.setText(ticket.getNameRoute());
            txtSeatChoose.setText(ticket.getNumberSeat());
            txtPrice.setText(ticket.getPriceTicket());

        }else{
            txtPrice.setText(ticket.getPriceTotal());
            if (ticket.getStatusPayment().equals("0")){
                txtPaymentStt.setText("Unpaid");
            }else {
                txtPaymentStt.setText("Paid");
            }

            getdataRoute(ref);

            getdataSeats(ref);
        }

        txtTrip.setText(ticket.getDepartureDate() + " | " + ticket.getDepartureTime());
        txtNameTicket.setText(ticket.getNameTicket());
        txtBusNumber.setText(ticket.getBusNumber());


        // Thông tin khách hàng
        if (account != null) {
            txtFullName.setText(account.getHoTen());
            txtPhone.setText(account.getSDT());
            txtEmail.setText(account.getTenTK());
        }

        rtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedRadiobuttonId = rdgPayments.getCheckedRadioButtonId();
                if (selectedRadiobuttonId != R.id.radiobuttonstation && selectedRadiobuttonId != R.id.radiobuttonzalopay){
                    Toast.makeText(PaymentActivity.this, "Please choose a payment method", Toast.LENGTH_LONG).show();
                    return;
                }

                if (selectedRadiobuttonId == R.id.radiobuttonzalopay){
                    CreateOrder order =  new CreateOrder();

                    try {
                        JSONObject data = order.createOrder(ticket.getPriceTicket());
                        String code = data.getString("return_code");
                        Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                        if (code.equals("1")) {
                            String token = data.getString("zp_trans_token");
                            Log.d("Token", token);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                ref = FirebaseDatabase.getInstance().getReference("VE");

                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
                String currentTime = dateFormat.format(now);
                String ticketId = "VE" + currentTime;

                Map<String, Object> firebaseTicket = new HashMap<>();
                firebaseTicket.put("idAccount", ticket.getIdAccount());
                firebaseTicket.put("idTransition", ticket.getIdTransition());
                firebaseTicket.put("priceTotal", ticket.getPriceTicket());
                firebaseTicket.put("statusPayment", "0");
                firebaseTicket.put("statusTicket", "0");

                ref = ref.child(ticketId);
                ref.updateChildren(firebaseTicket).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PaymentActivity.this, "Successful ticket booking", Toast.LENGTH_LONG).show();
                        String numberSeat = ticket.getNumberSeat();
                        setDetailTicket(ticketId, numberSeat);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(PaymentActivity.this, "Fail ticket booking", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void mapping(){
        txtRouteTicket = (TextView) findViewById(R.id.TicketRouteInf);
        txtTrip = (TextView) findViewById(R.id.TicketTripInf);
        txtNameTicket = findViewById(R.id.BusTypeInf);
        txtBusNumber = findViewById(R.id.BusNumberInf);
        txtSeatChoose =findViewById(R.id.NumberTicketInf);
        txtPrice = findViewById(R.id.TotalPriceInf);
        txtPaymentStt = findViewById(R.id.PaymentStatusInf);

        rtnBack = (ImageView) findViewById(R.id.backBefore);

        btnConfirm = (Button) findViewById(R.id.buttonConfirmInfo);

        // Thong tin khach hang
        txtFullName = (TextView) findViewById(R.id.FullNameInf);
        txtPhone = (TextView) findViewById(R.id.PhoneNumberInf);
        txtEmail = (TextView) findViewById(R.id.EmailInf);

        rdgPayments  = (RadioGroup) findViewById(R.id.radioPaymentMethods);
    }

    private void setDetailTicket(String ticketId, String numberSeat){
        DatabaseReference childRef = FirebaseDatabase.getInstance().getReference("CTVE");
        childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                String idCT;

                String[] seatArr = numberSeat.split(", ");
                for (int i = 0; i < seatArr.length; i++){
                    if (count < 10) {
                        idCT = "CT0" + count;
                    }else{
                        idCT = "CT" + count;
                    }
                    DatabaseReference detailRef = childRef.child(idCT);
                    detailRef.child("ticketId").setValue(ticketId);
                    detailRef.child("numberSeat").setValue(seatArr[i]);
                    count++;
                }
                navigateToChooseTicketActivity();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void navigateToChooseTicketActivity() {
        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ACCOUNT", account);
        bundle.putString("ID ACCOUNT", ticket.getIdAccount());
        intent.putExtra("from payment", bundle);
        startActivity(intent);
        finish();

    }

    private void getdataRoute(DatabaseReference ref){
        String idstartPoint = ticket.getStartPoint();
        String idendPoint = ticket.getEndPoint();
        ref = FirebaseDatabase.getInstance().getReference("DIADIEM");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String Start = "";
                String End = "";
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String idcity = dataSnapshot.getKey();
                    if (idcity.equals(idstartPoint)){
                        Start = dataSnapshot.child("nameCity").getValue(String.class);
                    }

                    if (idcity.equals(idendPoint)){
                        End = dataSnapshot.child("nameCity").getValue(String.class);
                    }
                }
                txtRouteTicket.setText(Start + " - " + End);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(PaymentActivity.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getdataSeats(DatabaseReference ref){
        String idTicket = ticket.getIdTicket();

        ref = FirebaseDatabase.getInstance().getReference("CTVE");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String seats = "";
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String fbidTicket = dataSnapshot.child("ticketId").getValue(String.class);
                    if (idTicket.equals(fbidTicket)){
                        seats += dataSnapshot.child("numberSeat").getValue(String.class) + " ";
                    }
                }
                txtSeatChoose.setText(seats);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}