package com.example.finalproject.admin.managefunction.DIADIEM;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.admin.managefunction.XE.AddCarActivity;
import com.example.finalproject.admin.managefunction.XE.ManageXeActivity;
import com.example.finalproject.admin.managefunction.XE.Xe;
import com.example.finalproject.search.list_city_points.City;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import androidx.annotation.NonNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddCityActivity extends AppCompatActivity {
    private Button btnAdd;
    private EditText edtNameCity;
    private ImageView imgCity, btnBack;
    private City city;
    private FirebaseStorage storage;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Boolean isUsedCity = false;
    private TextView btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        mapping();

        Bundle bundle = getIntent().getBundleExtra("Detail Info City");
        if(bundle != null){
            btnAdd.setText("Update");
            city = (City) bundle.get("INFO CITY");
            edtNameCity.setText(city.getNameCity());
            if (city.getImageCity() != null){
                Uri uri = Uri.parse(city.getImageCity());
                Picasso.get().load(uri).into(imgCity);
            }

            checkUsedCity();
            if(isUsedCity) {
                edtNameCity.setEnabled(false);
            }
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city.setNameCity(edtNameCity.getText().toString());
                if (city.getNameCity().isEmpty()){
                    Toast.makeText(AddCityActivity.this, "Please fill in Name City!", Toast.LENGTH_LONG).show();
                    return;
                }

                DatabaseReference ref = database.getReference("DIADIEM");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (bundle == null) {
                            long count = snapshot.getChildrenCount();
                            count += 1;
                            if (count < 10) {
                                city.setIdCity("DD0" + count);
                            } else {
                                city.setIdCity("DD" + count);
                            }
                        }

                        if (imgCity.getDrawable() != null){
                            StorageReference storageRef = storage.getReference();
                            StorageReference AvataRef = storageRef.child("DIADIEM");
                            StorageReference UidRef = AvataRef.child("img" + city.getIdCity() + ".png");

                            Drawable drawable = imgCity.getDrawable();
                            Bitmap bitmap = null;

                            if (drawable instanceof BitmapDrawable) {
                                bitmap = ((BitmapDrawable) drawable).getBitmap();
                            } else {
                                // If the drawable is not a BitmapDrawable, you can create a new Bitmap from it
                                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                                Canvas canvas = new Canvas(bitmap);
                                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                drawable.draw(canvas);
                            }

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            byte[] data = baos.toByteArray();

                            UploadTask uploadTask = UidRef.putBytes(data);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(AddCityActivity.this, "Upload avatar fail", Toast.LENGTH_LONG).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    UidRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imgUrl = uri.toString();
                                            city.setImageCity(imgUrl);
                                            updateInfo(ref, city);
                                        }
                                    });

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(AddCityActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        imgCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddCityActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUsedCity){
                    Toast.makeText(AddCityActivity.this, "Can't delete used city", Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AddCityActivity.this);
                builder.setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this city?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked Delete button, proceed with deletion
                                deleteCity();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            imgCity.setImageURI(uri);
        }
    }

    private void mapping(){
        btnBack = (ImageView) findViewById(R.id.backBefore);
        btnAdd = (Button) findViewById(R.id.buttonAddCity);
        edtNameCity = (EditText) findViewById(R.id.editNameCity);
        imgCity = (ImageView) findViewById(R.id.imageCity);
        storage = FirebaseStorage.getInstance("gs://booking-transition.appspot.com/");
        btnDelete = (TextView) findViewById(R.id.textviewDeleteCity);

        city =new City();
    }

    private void updateInfo(DatabaseReference ref, City city){

        Map<String, Object> firebaseXe = new HashMap<>();
        firebaseXe.put("nameCity", city.getNameCity());
        firebaseXe.put("anhdaidienDD", city.getImageCity());

        ref = ref.child(city.getIdCity());
        ref.updateChildren(firebaseXe).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddCityActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddCityActivity.this, ManageDiaDiemActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(AddCityActivity.this, "Fail Add New", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkUsedCity(){
        DatabaseReference referenceRoute = database.getReference("CHUYENXE");
        referenceRoute.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String fbStart = dataSnapshot.child("startPoint").getValue(String.class);
                    String fbEnd = dataSnapshot.child("endPoint").getValue(String.class);
                    Log.d("CITY", city.getIdCity());
                    Log.d("START", fbStart);
                    Log.d("END", fbEnd);
                    if (fbStart.equals(city.getIdCity()) || fbEnd.equals(city.getIdCity())) {
                        isUsedCity = true;
                        edtNameCity.setEnabled(false);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AddCityActivity.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteCity() {
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("DIADIEM/img" + city.getIdCity() + ".png");

        // Delete the image from Firebase Storage
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Image deleted successfully, now delete the car data from the database
                DatabaseReference referenceXe = FirebaseDatabase.getInstance().getReference("DIADIEM");
                referenceXe.child(city.getIdCity()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddCityActivity.this, "City deleted successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddCityActivity.this, ManageDiaDiemActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddCityActivity.this, "Failed to delete city", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCityActivity.this, "Failed to delete image", Toast.LENGTH_LONG).show();
            }
        });
    }
}