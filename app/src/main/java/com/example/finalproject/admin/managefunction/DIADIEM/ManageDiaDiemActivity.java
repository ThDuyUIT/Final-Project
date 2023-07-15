package com.example.finalproject.admin.managefunction.DIADIEM;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.search.list_city_points.City;
import com.example.finalproject.search.list_city_points.CityAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageDiaDiemActivity extends AppCompatActivity {

    private Button btnAddNew;
    private ListView listViewDiaDiem;
    private ArrayList<City> cities;
    private CityAdapter cityAdapter;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_dia_diem);

        mapping();

        getListDiaDiem();

        cityAdapter = new CityAdapter(ManageDiaDiemActivity.this, R.layout.line_name_city, cities);
        listViewDiaDiem.setAdapter(cityAdapter);

        listViewDiaDiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                City city = cities.get(i);
                Intent intent = new Intent(ManageDiaDiemActivity.this, AddCityActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("INFO CITY",city);
                intent.putExtra("Detail Info City", bundle);
                startActivity(intent);
            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ManageDiaDiemActivity.this, AddCityActivity.class);
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
        btnAddNew = (Button) findViewById(R.id.buttonAddNewCity);
        listViewDiaDiem = (ListView)findViewById(R.id.listviewDiaDiem);
        btnBack = (ImageView) findViewById(R.id.backBefore);

    }

    private void getListDiaDiem(){
        cities = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("DIADIEM");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    City city = dataSnapshot.getValue(City.class);
                    String fbimg = dataSnapshot.child("anhdaidienDD").getValue(String.class);
                    String keyCity = dataSnapshot.getKey();
                    city.setImageCity(fbimg);
                    city.setIdCity(keyCity);
                    cities.add(city);
                }
                cityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ManageDiaDiemActivity.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}