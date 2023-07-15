//package com.example.finalproject.search.ticket;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.finalproject.R;
//
//public class CustomerInformationActivity extends AppCompatActivity {
//    private TextView txtRoute, txtDate, txtNameTicket, txtBusNumber, txtTime, txtPrice, txtSeatChoose;
//    private TextView txtFullName, txtPhone, txtEmail, txtNoteOther;
//    private ImageView rtnBack, imgBus;
//    private Button btnContinue;
//    private Ticket ticket;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_customer_information);
//        mapping();
//
//        Bundle bundle = getIntent().getBundleExtra("to customer information");
//        if(bundle == null){
//            return;
//        }
//
//        ticket = (Ticket) bundle.get("TicketMoreInformation");
//
//        txtRoute.setText(ticket.getNameRoute());
//        txtDate.setText(ticket.getDepartureDate());
////        txtRoute.setText(bundle.getString("ROUTE"));
////        txtDate.setText(bundle.getString("DATE"));
//        imgBus.setImageResource(ticket.getResourceID());
//        txtNameTicket.setText(ticket.getNameTicket());
//        txtBusNumber.setText(ticket.getBusNumber());
//        txtTime.setText(ticket.getDepartureTime());
//        txtPrice.setText(ticket.getPriceTicket());
//        txtSeatChoose.setText(ticket.getNumberSeat());
//        rtnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        // Tiep tuc sang trang dien thong tin ve
//        btnContinue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String route = txtRoute.getText().toString();
////                String date = txtDate.getText().toString();
////                String seat = txtSeatChoose.getText().toString();
//
//                String fullName = txtFullName.getText().toString();
//                if (fullName.isEmpty()){
//                    Toast.makeText(CustomerInformationActivity.this,"Please fill in passenger's full name", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                String phone = txtPhone.getText().toString();
//                if (phone.isEmpty()){
//                    Toast.makeText(CustomerInformationActivity.this,"Please fill in passenger's phone numbers", Toast.LENGTH_SHORT).show();
//                    return;
//                }else{
//                    if (phone.length() > 9){
//                        Toast.makeText(CustomerInformationActivity.this,"Length of phone numbers is greater than 9", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
//
//                String email = txtEmail.getText().toString();
//                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    Toast.makeText(CustomerInformationActivity.this, "Invalid email format.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                ticket.setFullName(fullName);
//                ticket.setPhone(phone);
//                ticket.setEmail(email);
//
//                Intent intent = new Intent(CustomerInformationActivity.this, PaymentActivity.class);
//                Bundle bundle = new Bundle();
////                bundle.putString("ROUTE", route);
////                bundle.putString("DATE", date);
////                bundle.putString("SEAT", seat);
////                bundle.putString("NAME", fullName);
////                bundle.putString("PHONE", phone);
////                bundle.putString("EMAIL", email);
//                bundle.putSerializable("CustomerTicket", ticket);
//                intent.putExtra("to payment", bundle);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void mapping(){
//        txtRoute = (TextView) findViewById(R.id.textviewRoute);
//        txtDate = (TextView) findViewById(R.id.textviewDepartureDate);
//        rtnBack = (ImageView) findViewById(R.id.backBefore);
//        btnContinue = findViewById(R.id.buttonNextPayment);
//        txtSeatChoose =findViewById(R.id.seatChoose);
//
//        // Thong tin khach hang
//        txtFullName = (TextView) findViewById(R.id.editTextName);
//        txtPhone = (TextView) findViewById(R.id.editTextPhone);
//        txtEmail = (TextView) findViewById(R.id.editTextEmail);
//        txtNoteOther = (TextView) findViewById(R.id.editTextOtherRequest);
//
//        // Vé xe
//        imgBus = findViewById(R.id.imageTicket);
//        txtNameTicket = findViewById(R.id.nameTicket);
//        txtBusNumber = findViewById(R.id.busNumber);
//        txtTime = findViewById(R.id.departureTime);
//        txtPrice = findViewById(R.id.priceTicket);
//    }
//}