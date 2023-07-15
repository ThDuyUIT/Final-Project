package com.example.finalproject.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.admin.managefunction.CHUYENXE.ManageChuyenXeActivity;
import com.example.finalproject.admin.managefunction.DIADIEM.ManageDiaDiemActivity;
import com.example.finalproject.admin.managefunction.VE.ManageVeActivity;
import com.example.finalproject.admin.managefunction.XE.ManageXeActivity;
import com.example.finalproject.admin.reportfunction.ReportActivity;

public class MainActivityAdmin extends AppCompatActivity {

    private TextView txtManageXe, txtManageCity, txtManageRoute, txtManageTicket, txtReport, txtLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        mapping();

        txtManageXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this, ManageXeActivity.class);
                startActivity(intent);
            }
        });

        txtManageCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this, ManageDiaDiemActivity.class);
                startActivity(intent);
            }
        });

        txtManageRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this, ManageChuyenXeActivity.class);
                startActivity(intent);
            }
        });

        txtManageTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this, ManageVeActivity.class);
                startActivity(intent);
            }
        });

        txtReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAdmin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void mapping(){
        txtManageXe = (TextView) findViewById(R.id.textviewManageCar);
        txtManageCity = (TextView) findViewById(R.id.textviewManageCity);
        txtManageRoute = (TextView) findViewById(R.id.textviewManageRoute);
        txtManageTicket = (TextView) findViewById(R.id.textviewManageTicket);
        txtReport = (TextView) findViewById(R.id.textviewReport);
        txtLogout = (TextView) findViewById(R.id.textviewLogout);
    }
}