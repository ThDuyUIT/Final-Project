package com.example.finalproject.myaccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditInfoActivity extends AppCompatActivity {

    private EditText edtFullName, edtPhone;
    private RadioGroup rdgGender;
    private String gender;
    private String str;
    private Button btnSave;
    private ImageView btnBack;
    private CircleImageView civ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        mapping();

        Bundle bundle = getIntent().getBundleExtra("to edit");
        edtFullName.setText(bundle.getString("NAME"));
        edtPhone.setText(bundle.getString("PHONE"));
        gender = bundle.getString("GENDER");

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

                Intent intent = new Intent();
                Bundle bundle1 = new Bundle();
                bundle1.putString("changed name", edtFullName.getText().toString());
                bundle1.putString("changed phone", edtPhone.getText().toString());
                bundle1.putString("changed gender", gender);
                intent.putExtra("changedInfo", bundle1);
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

        Uri uri = data.getData();
        civ.setImageURI(uri);
    }

    private void mapping(){
        edtFullName = (EditText) findViewById(R.id.edittextFullName);
        edtPhone = (EditText) findViewById(R.id.edittextPhoneNumber);
        rdgGender = (RadioGroup) findViewById(R.id.radioGender);
        btnSave = (Button) findViewById(R.id.buttonSave);
        btnBack = (ImageView) findViewById(R.id.backBefore);
        civ = (CircleImageView) findViewById(R.id.circleimageEditUser);
    }
}