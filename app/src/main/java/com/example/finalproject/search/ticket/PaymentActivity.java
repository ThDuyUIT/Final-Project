package com.example.finalproject.search.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.finalproject.R;

public class PaymentActivity extends AppCompatActivity {
    private ImageView rtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mapping();
        rtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mapping(){
        rtnBack = (ImageView) findViewById(R.id.backBefore);
    }
}