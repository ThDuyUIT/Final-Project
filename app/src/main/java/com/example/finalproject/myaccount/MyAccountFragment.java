package com.example.finalproject.myaccount;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.authentication.Account;
import com.example.finalproject.authentication.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.finalproject.myaccount.EditInfoActivity.RESULT_OK;
public class MyAccountFragment extends Fragment{

    private TextView txtUserName, txtMember,txtEdit, txtLogout, txtHelp, txtfullName, txtPhoneNum, txtGender, txtEmail;
    private LinearLayout linearLayout;
    private String userName;
    private CircleImageView civ;
    private Account account;

    public MyAccountFragment(String userName) {
        this.userName = userName;
    }

    public MyAccountFragment(Account account) {
        this.account = account;
    }

    public MyAccountFragment() {
    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                Bundle bundle = intent.getBundleExtra("changedInfo");
                if (bundle!= null) {
                    account = (Account) bundle.get("EDIT ACCOUNT");
//                    String changedName = bundle.getString("changed name");
//                    String changedPhone = bundle.getString("changed phone");
//                    String changedGender = bundle.getString("changed gender");


                    String changedName = account.getHoTen();
                    String changedPhone = account.getSDT();
                    String changedGender = account.getGioiTinh();
                    Uri uri = Uri.parse(account.getAnhDaiDien());

                    Picasso.get().load(uri).into(civ);
                    txtfullName.setText(changedName);
                    txtPhoneNum.setText(changedPhone);
                    txtGender.setText(changedGender);

                }
            }
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myaccount_fragment, container, false);

        mapping(view);

        if (account == null){
            linearLayout.setVisibility(View.GONE);
            txtUserName.setText("Become a member");
            txtMember.setText("To enjoy extra promotions");
            txtEdit.setText("Sign in");
            txtLogout.setVisibility(View.GONE);

            txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            txtUserName.setText(account.getTenTK());
            txtfullName.setText(account.getHoTen());
            txtEmail.setText(account.getTenTK());
            txtGender.setText(account.getGioiTinh());
            txtPhoneNum.setText(account.getSDT());

            if(!account.getAnhDaiDien().equals("Unknow")){
                Uri uri = Uri.parse(account.getAnhDaiDien());
                Picasso.get().load(uri).into(civ);
            }

            txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String fullName = txtfullName.getText().toString();
//                    String phoneNum = txtPhoneNum.getText().toString();
//                    String gender = txtGender.getText().toString();

                    Intent intent = new Intent(getActivity(), EditInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ACCOUNT", account);
                    intent.putExtra("to edit", bundle);

                    activityResultLauncher.launch(intent);
                    //startActivity(intent);
                }
            });
        }

        txtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "1234567890"; // Replace with the phone number you want to call

                // Create an intent with the ACTION_DIAL action and the phone number
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);

            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void mapping(View view){
        txtUserName = (TextView) view.findViewById(R.id.textviewUserName);
        txtMember = (TextView) view.findViewById(R.id.textviewMember);
        txtEdit = (TextView) view.findViewById(R.id.textviewEdit);
        txtLogout = (TextView) view.findViewById(R.id.textviewLogout);
        txtHelp = (TextView) view.findViewById(R.id.textviewHelp);
        txtfullName = (TextView) view.findViewById(R.id.textviewFullName);
        txtPhoneNum = (TextView) view.findViewById(R.id.textviewPhone);
        txtGender = (TextView) view.findViewById(R.id.textviewGender);
        txtEmail = (TextView) view.findViewById(R.id.textviewEmail);
        civ = (CircleImageView) view.findViewById(R.id.circleimageUser);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearContainInfoUser);
    }
}
