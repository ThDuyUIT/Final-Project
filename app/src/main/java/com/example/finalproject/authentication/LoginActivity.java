package com.example.finalproject.authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.admin.MainActivityAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName,edtPass;
    private Button btnLogin;
    private TextView txtRegister;
    private boolean isLoggedIn = false;
    private ImageView btnBack;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        Bundle bundle = intent.getBundleExtra("Account_info");
                        String getUserName = bundle.getString("Username");
                        //Log.d("test",getUserName);
                        edtUserName.setText(getUserName);
                        String getPass = bundle.getString("Password");
                        edtPass.setText(getPass);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mapping();

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                //startActivity(intent);
                activityResultLauncher.launch(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUserName.getText().toString();
                String pass = edtPass.getText().toString();

                if(username.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please input Username!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please input Password!", Toast.LENGTH_LONG).show();
                    return;
                }

//                if(username.equals("demo@gmail.com") && pass.equals("demo")){
//                    isLoggedIn = true;
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.putExtra("LOGGED_IN", isLoggedIn);
//                    intent.putExtra("USER_NAME", username);
//                    startActivity(intent);
//
//                    finish();
//                }


                auth.signInWithEmailAndPassword(username, pass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    if (user.getUid().equals("MrAjwN1ZYsZNjqFBeZs9D3vEfer2")){
                                        Intent intent = new Intent(LoginActivity.this, MainActivityAdmin.class);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    }

                                    String userId = "KH" + user.getUid();
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("KHACHHANG").child(userId);

                                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                // Lấy thông tin người dùng từ snapshot
                                                Account account = snapshot.getValue(Account.class);
                                                String fullName = account.getHoTen();

                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("ID ACCOUNT", userId);
                                                bundle.putSerializable("ACCOUNT", account);
                                                intent.putExtra("login successful", bundle);
                                                startActivity(intent);
                                                finish();

                                            } else {
                                                Toast.makeText(LoginActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            //Toast.makeText(RegisterActivity.this, "Failed to retrieve user data.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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

    private void mapping() {
        progressBar = new ProgressBar(this);

        btnBack = (ImageView) findViewById(R.id.backBefore);
        edtUserName = (EditText) findViewById(R.id.editTextUsername);
        edtPass = (EditText) findViewById(R.id.editTextPass);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        txtRegister = (TextView) findViewById(R.id.textViewRegister);

        auth = FirebaseAuth.getInstance();
    }
}