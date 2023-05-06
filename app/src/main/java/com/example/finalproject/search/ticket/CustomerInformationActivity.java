package com.example.finalproject.search.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.finalproject.R;

public class CustomerInformationActivity extends AppCompatActivity {
    private ImageView rtnBack;
    private Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        mapping();
        rtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Tiep tuc sang trang dien thong tin ve
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(CustomerInformationActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping(){
        rtnBack = (ImageView) findViewById(R.id.backBefore);
        btnContinue = findViewById(R.id.buttonNextPayment);
    }
}