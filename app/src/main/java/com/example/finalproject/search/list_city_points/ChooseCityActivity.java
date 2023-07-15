package com.example.finalproject.search.list_city_points;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class ChooseCityActivity extends AppCompatActivity {
    private ArrayList<City> cityList;
    private ListView listView;
    private CityAdapter cityAdapter;
    private TextView txtTitle;
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        mapping();

        getListCities();

        cityAdapter = new CityAdapter(ChooseCityActivity.this, R.layout.line_name_city, cityList);

        Bundle bundle = getIntent().getBundleExtra("valueTitle");

        int drawableLeft = bundle.getInt("drawableLeft");
        Drawable drawable = getResources().getDrawable(drawableLeft);

        String text = bundle.getString("text");

        txtTitle.setHint(text);
        txtTitle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        listView.setAdapter(cityAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                City city = cityList.get(i);
                Intent intent = new Intent();
                Bundle bundle1 = new Bundle();
                bundle.putString("namepoint", txtTitle.getHint().toString());
                bundle.putString("nameoption", city.getNameCity());
                bundle.putString("idoption", city.getIdCity());
                intent.putExtra("selectedValue", bundle);
                setResult(RESULT_OK, intent);
                finish();
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
    }

    private void mapping(){
        btnBack = (ImageView) findViewById(R.id.backBefore);
        txtTitle = (TextView) findViewById(R.id.textviewTitlePoint);
        listView = (ListView) findViewById(R.id.listviewCity);

    }

    private void getListCities(){
        cityList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("DIADIEM");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    City city = dataSnapshot.getValue(City.class);
                    String keyCity = dataSnapshot.getKey();
                    city.setIdCity(keyCity);
                    Log.d("Name city:", city.getNameCity());
                    cityList.add(city);
                }

                cityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ChooseCityActivity.this, "Get list city fail.", Toast.LENGTH_LONG).show();
            }
        });

//        cityList.add(new City("Tp Ho Chi Minh"));
//        cityList.add(new City("Vinh Long"));
//        cityList.add(new City("Soc Trang"));
//        cityList.add(new City("Hau Giang"));
//        cityList.add(new City("Ca Mau"));
    }
}