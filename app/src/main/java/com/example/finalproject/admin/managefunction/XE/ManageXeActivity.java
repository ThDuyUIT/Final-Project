package com.example.finalproject.admin.managefunction.XE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageXeActivity extends AppCompatActivity {

    private TextView txtTitle;
    private Button btnAddNew;
    private ListView listViewXe;
    private ArrayList<Xe> xeList;
    private XeAdapter xeAdapter;
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_xe);

        mapping();

        getListXe();

        xeAdapter = new XeAdapter(ManageXeActivity.this, R.layout.line_number_xe, xeList);
        listViewXe.setAdapter(xeAdapter);

        boolean fromAddRoute = getIntent().getBooleanExtra("from add route", false);
        if (fromAddRoute){
            txtTitle.setText("List Car");
            btnAddNew.setVisibility(View.GONE);
        }

        listViewXe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Xe xe = xeList.get(i);
                if (!fromAddRoute) {
                    Intent intent = new Intent(ManageXeActivity.this, AddCarActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("INFO XE", xe);
                    intent.putExtra("Detail Info Xe", bundle);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    Bundle bundle  = new Bundle();
                    bundle.putSerializable("INFO XE", xe);
                    intent.putExtra("Detail Info Xe", bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageXeActivity.this, AddCarActivity.class);
                startActivity(intent);
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
        btnAddNew = (Button) findViewById(R.id.buttonAddNewCar);
        listViewXe = (ListView) findViewById(R.id.listviewXe);
        btnBack = (ImageView) findViewById(R.id.backBefore);
        txtTitle = (TextView) findViewById(R.id.textviewTitle);
    }

    private void getListXe(){
        xeList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("XE");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String keyXe = dataSnapshot.getKey();
                    String nameXe = dataSnapshot.child("nameTicket").getValue(String.class);
                    String imgXe = dataSnapshot.child("anhdaidienXe").getValue(String.class);
                    Xe xe = new Xe();
                    xe.setIdNumber(keyXe);
                    xe.setNameXe(nameXe);
                    xe.setImgXe(imgXe);
                    xeList.add(xe);
                }
                xeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ManageXeActivity.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}