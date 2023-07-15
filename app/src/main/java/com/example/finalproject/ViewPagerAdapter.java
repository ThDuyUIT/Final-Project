package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.finalproject.authentication.Account;
import com.example.finalproject.myaccount.MyAccountFragment;
import com.example.finalproject.myticket.LoginRequiredFragment;
import com.example.finalproject.myticket.MyTicketFragment;
import com.example.finalproject.search.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private boolean isLoggedIn;
    private String userName;
    private FirebaseUser user;
    private Account account;
    private String idAccount;


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);


        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Bundle bundleFromPayment = fragmentActivity.getIntent().getBundleExtra("from payment");
            if (bundleFromPayment != null){
                account = (Account) bundleFromPayment.get("ACCOUNT");
                userName = account.getTenTK();
                idAccount = bundleFromPayment.getString("ID ACCOUNT");
            }else {
                Log.d("FROM PAYMENT", "NULL");
                Bundle bundle = fragmentActivity.getIntent().getBundleExtra("login successful");
                if (bundle == null)
                    return;
                account = (Account) bundle.get("ACCOUNT");
                userName = account.getTenTK();
                idAccount = bundle.getString("ID ACCOUNT");
            }
        }
//        else {
//            Log.d("FROM PAYMENT", "it work");
//            Bundle bundleFromPayment = fragmentActivity.getIntent().getBundleExtra("from payment");
//            if (bundleFromPayment == null)
//
//                return;
//            account = (Account) bundleFromPayment.get("ACCOUNT");
//            userName = account.getTenTK();
//            idAccount = bundleFromPayment.getString("ID ACCOUNT");
//        }
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new SearchFragment(account, idAccount);
            case 1:
                if (userName == null)
                    return new LoginRequiredFragment();
                else
                    return new MyTicketFragment(idAccount, account);
            case 2:
                return new MyAccountFragment(account);
            default:
                return new SearchFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
