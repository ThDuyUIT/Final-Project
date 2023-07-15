package com.example.finalproject.admin.managefunction.XE;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddCarActivity extends AppCompatActivity {

    private EditText edtIdXE, edtNameXe;
    private TextView btnDelete;
    private ImageView imageViewXe, btnBack;
    private Button btnAdd;
    private DatabaseReference ref;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseStorage storage;
    private Xe xe;
    private boolean isBookedXe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        mapping();

        Bundle bundle = getIntent().getBundleExtra("Detail Info Xe");
        if(bundle != null){
            edtIdXE.setEnabled(false);
            btnAdd.setText("Update");
            xe = (Xe) bundle.get("INFO XE");
            edtIdXE.setText(xe.getIdNumber());
            edtNameXe.setText(xe.getNameXe());
            Uri uri = Uri.parse(xe.getImgXe());
            Picasso.get().load(uri).into(imageViewXe);
            checkUsedXe();
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xe.setIdNumber(edtIdXE.getText().toString());
                xe.setNameXe(edtNameXe.getText().toString());

                if (xe.getIdNumber().isEmpty()){
                    Toast.makeText(AddCarActivity.this, "Please fill in ID Number!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (xe.getNameXe().isEmpty()){
                    Toast.makeText(AddCarActivity.this, "Please fill in vehicle's name!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (imageViewXe.getDrawable() != null){
                    StorageReference storageRef = storage.getReference();
                    StorageReference AvataRef = storageRef.child("XE");
                    StorageReference UidRef = AvataRef.child("img" + xe.getIdNumber() + ".png");

                    Drawable drawable = imageViewXe.getDrawable();
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
                            Toast.makeText(AddCarActivity.this, "Upload avatar fail", Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Toast.makeText(AddCarActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            UidRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imgUrl = uri.toString();
                                    xe.setImgXe(imgUrl);
                                    updateInfo(ref, xe);
                                }
                            });

                        }
                    });
                }


            }
        });

        imageViewXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddCarActivity.this)
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
                if (isBookedXe){
                    Toast.makeText(AddCarActivity.this, "Can't delete used car", Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AddCarActivity.this);
                builder.setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this car?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked Delete button, proceed with deletion
                                deleteCar();
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
            imageViewXe.setImageURI(uri);
        }
    }

    private void mapping(){
        edtIdXE = (EditText) findViewById(R.id.editIdXe);
        edtNameXe = (EditText) findViewById(R.id.editNameXe);

        imageViewXe = (ImageView) findViewById(R.id.imageXe);

        btnAdd = (Button) findViewById(R.id.buttonAddCar);
        xe = new Xe();

        btnBack = (ImageView) findViewById(R.id.backBefore);
        btnDelete = (TextView) findViewById(R.id.textviewDeleteXe);

        storage = FirebaseStorage.getInstance("gs://booking-transition.appspot.com/");
    }

    private void updateInfo(DatabaseReference ref, Xe xe){
        ref = database.getReference("XE");

        Map<String, Object> firebaseXe = new HashMap<>();
        firebaseXe.put("nameTicket", xe.getNameXe());
        firebaseXe.put("anhdaidienXe", xe.getImgXe());

        ref = ref.child(xe.getIdNumber());
        ref.updateChildren(firebaseXe).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddCarActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddCarActivity.this, ManageXeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(AddCarActivity.this, "Fail Add New", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkUsedXe(){
        DatabaseReference referenceRoute = database.getReference("CHUYENXE");
        referenceRoute.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String fbnumberBusNumber = dataSnapshot.child("busNumber").getValue(String.class);
                    if (fbnumberBusNumber.equals(xe.getIdNumber())) {
                        isBookedXe = true;
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AddCarActivity.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteCar() {
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("XE/img" + xe.getIdNumber() + ".png");

        // Delete the image from Firebase Storage
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Image deleted successfully, now delete the car data from the database
                DatabaseReference referenceXe = FirebaseDatabase.getInstance().getReference("XE");
                referenceXe.child(xe.getIdNumber()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddCarActivity.this, "Car deleted successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddCarActivity.this, ManageXeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddCarActivity.this, "Failed to delete car", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCarActivity.this, "Failed to delete image", Toast.LENGTH_LONG).show();
            }
        });
    }

}