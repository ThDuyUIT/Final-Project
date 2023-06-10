package com.example.finalproject.myaccount;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.finalproject.authentication.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.finalproject.myaccount.EditInfoActivity.RESULT_OK;
public class MyAccountFragment extends Fragment{

    private TextView txtUserName, txtMember,txtEdit, txtLogout, txtHelp, txtfullName, txtPhoneNum, txtGender;
    private LinearLayout linearLayout;
    private String userName;
    private CircleImageView civ;

    public MyAccountFragment(String userName) {
        this.userName = userName;
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
                    String changedName = bundle.getString("changed name");
                    String changedPhone = bundle.getString("changed phone");
                    String changedGender = bundle.getString("changed gender");

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

        if (userName.equals("")){
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
//            linearLayout.setVisibility(View.VISIBLE);
//            txtLogout.setVisibility(View.VISIBLE);
            txtUserName.setText(userName);
//            txtMember.setText("Member");
//            txtEdit.setText("Edit");

            txtEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fullName = txtfullName.getText().toString();
                    String phoneNum = txtPhoneNum.getText().toString();
                    String gender = txtGender.getText().toString();

                    Intent intent = new Intent(getActivity(), EditInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("NAME", fullName);
                    bundle.putString("PHONE", phoneNum);
                    bundle.putString("GENDER", gender);
                    //bundle.putParcelable("IMAGE", bitmap);
                    intent.putExtra("to edit", bundle);

                    activityResultLauncher.launch(intent);
                    //startActivity(intent);
                }
            });
        }

        txtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent with the ACTION_DIAL action and the phone number
                Intent intent = new Intent(getActivity(), HelpCenter_Activity.class);
                startActivity(intent);
            }
        });


        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        civ = (CircleImageView) view.findViewById(R.id.circleimageUser);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearContainInfoUser);
    }
}
