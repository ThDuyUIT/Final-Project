package com.example.finalproject.authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText edtName, edtPhoneNum, edtUser, edtPass, edtPass2;
    Button btnRegister;
    TextView txtGotoLogin;
    ImageView btnBack;
    private FirebaseAuth auth;
    private DatabaseReference userRef;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mapping();

        txtGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUserName = edtUser.getText().toString().trim();
                String strPass = edtPass.getText().toString();
                String strFullName = edtName.getText().toString();
                String strPhone = edtPhoneNum.getText().toString();
                String strConfirmPass = edtPass2.getText().toString();
                String strGender = "unknow";
                int srcHinh = 0;

                //Kiem tra ko bo trong cac thong tin
                if(strFullName.isEmpty() || strPhone.isEmpty() || strUserName.isEmpty() || strPass.isEmpty() || strConfirmPass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill in all the information", Toast.LENGTH_LONG).show();
                    return;
                }
                //Do dai sdt >=10
                if(strPhone.length() < 10){
                    Toast.makeText(RegisterActivity.this, "Phone number length greater than 9.", Toast.LENGTH_LONG).show();
                    return;
                }

                //ktra format email
                if(!Patterns.EMAIL_ADDRESS.matcher(strUserName).matches()) {
                    Toast.makeText(RegisterActivity.this, "Invalid email format.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!strConfirmPass.equals(strPass)){
                    Toast.makeText(RegisterActivity.this, "Confirmation password does not match.", Toast.LENGTH_LONG).show();
                    return;
                }

                account = new Account(strFullName, strPhone, strUserName, "Unknow", "Unknow");
                createAccount(account, strPass);

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
        edtName = (EditText) findViewById(R.id.editTextName);
        edtPhoneNum = (EditText) findViewById(R.id.editPhoneNumbers);
        edtUser = (EditText) findViewById(R.id.editTextRegisterUsername);
        edtPass = (EditText) findViewById(R.id.editTextRegisterPass);
        edtPass2 = (EditText) findViewById(R.id.editTextConfirmPass);
        txtGotoLogin = (TextView) findViewById(R.id.textViewGotoLogin);
        btnRegister = (Button) findViewById(R.id.buttonRegister);

        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("KHACHHANG");
    }

    private void createAccount(Account account, String pass){
        //kiem tra ten tai khoan da ton tai chua?
        auth.fetchSignInMethodsForEmail(account.getTenTK())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            List<String> signInMethods = result.getSignInMethods();
                            if (signInMethods != null && signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                                // user with this email already exists
                                Toast.makeText(RegisterActivity.this, "An account with this email already exists.", Toast.LENGTH_LONG).show();
                            }else{
                                addNew(account, pass);
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error checking for existing email.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void addNew(Account account, String pass){
        // create new user account
        auth.createUserWithEmailAndPassword(account.getTenTK(), pass)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(RegisterActivity.this, "Sign up successfully.", Toast.LENGTH_LONG).show();

                            Bundle bundle = new Bundle();
                            bundle.putString("Username", account.getTenTK());
                            bundle.putString("Password", pass);

                            Intent intent = new Intent();
                            intent.putExtra("Account_info", bundle);
                            setResult(RESULT_OK, intent);
                            finish();

                            saveInfoUser(account);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Sign up failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void saveInfoUser(Account account){
        String Uid = auth.getCurrentUser().getUid();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

//                Date now = new Date();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
//
//                String currentTime = dateFormat.format(now);

                String userId = "KH" + Uid;
                userRef.child(userId).setValue(account);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
}