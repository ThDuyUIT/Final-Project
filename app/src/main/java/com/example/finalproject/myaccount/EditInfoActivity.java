package com.example.finalproject.myaccount;

import android.app.Activity;
import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.authentication.Account;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditInfoActivity extends AppCompatActivity {

    private EditText edtFullName, edtPhone;
    private RadioGroup rdgGender;
    private String gender;
    private String str;
    private Button btnSave;
    private ImageView btnBack;
    private CircleImageView civ;
    private Account account;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        mapping();

        Bundle bundle = getIntent().getBundleExtra("to edit");
        account = (Account) bundle.get("ACCOUNT");
        if(!account.getAnhDaiDien().equals("Unknow")){
            Uri uri = Uri.parse(account.getAnhDaiDien());
            Picasso.get().load(uri).into(civ);
        }
        edtFullName.setText(account.getHoTen());
        edtPhone.setText(account.getSDT());
        gender = account.getGioiTinh();

        for (int i = 0; i < rdgGender.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rdgGender.getChildAt(i);
            if (radioButton.getText().toString().equals(gender)) {
                radioButton.setChecked(true); // Select the matching RadioButton
                break;
            }
        }

        rdgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                gender = radioButton.getText().toString();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account changedInfo = new Account();
                changedInfo.setHoTen(edtFullName.getText().toString());
                changedInfo.setSDT(edtPhone.getText().toString());
                changedInfo.setGioiTinh(gender);

                String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("KHACHHANG/" + "KH" + Uid);

                if(civ.getDrawable() != null) {
                    StorageReference storageRef = storage.getReference();
                    String current = System.currentTimeMillis() + "";
                    StorageReference AvataRef = storageRef.child("Avata");
                    StorageReference UidRef = AvataRef.child("img" + Uid + ".png");

                    Drawable drawable = civ.getDrawable();
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
                            Toast.makeText(EditInfoActivity.this, "Upload avatar fail", Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditInfoActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            //account.setAnhDaiDien(UidRef.getDownloadUrl().toString());
                            //getUri(UidRef, changedInfo);
                            UidRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imgUrl = uri.toString();
                                    changedInfo.setAnhDaiDien(imgUrl);
                                    updateInfo(ref, changedInfo);
                                }
                            });

                        }
                    });
                }else{
                    updateInfo(ref, changedInfo);
                }
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

        civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(EditInfoActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            civ.setImageURI(uri);
        }
    }

    private void mapping(){
        edtFullName = (EditText) findViewById(R.id.edittextFullName);
        edtPhone = (EditText) findViewById(R.id.edittextPhoneNumber);
        rdgGender = (RadioGroup) findViewById(R.id.radioGender);
        btnSave = (Button) findViewById(R.id.buttonSave);
        btnBack = (ImageView) findViewById(R.id.backBefore);
        civ = (CircleImageView) findViewById(R.id.circleimageEditUser);
        storage = FirebaseStorage.getInstance("gs://booking-transition.appspot.com/");
    }

    private void updateInfo(DatabaseReference ref, Account changedInfo){
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("hoTen", changedInfo.getHoTen());
        userData.put("sdt", changedInfo.getSDT());
        userData.put("gioiTinh", changedInfo.getGioiTinh());
        userData.put("anhDaiDien", changedInfo.getAnhDaiDien());
        ref.updateChildren(userData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(EditInfoActivity.this, "Update failed", Toast.LENGTH_LONG).show();
                } else {
                    // Data updated successfully
                    Intent intent = new Intent();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("EDIT ACCOUNT", changedInfo);
                    intent.putExtra("changedInfo", bundle1);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}