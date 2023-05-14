package com.example.finalproject;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.finalproject.myaccount.MyAccountFragment;
import com.example.finalproject.myticket.LoginRequiredFragment;
import com.example.finalproject.myticket.MyTicketFragment;
import com.example.finalproject.search.SearchFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private boolean isLoggedIn;
    private String userName = "";

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
        Intent intent = fragmentActivity.getIntent();
//        Bundle bundle = intent.getBundleExtra("from loginactivity");
        isLoggedIn = intent.getBooleanExtra("LOGGED_IN", false);
        if (isLoggedIn){
            userName = intent.getStringExtra("USER_NAME");
        }
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new SearchFragment(userName);
//                if (isLoggedIn) {
//                    return new SearchFragment(userName);
//                }
//                else {
//                    return new SearchFragment();
//                }
            case 1:
                if (isLoggedIn)
                    return new MyTicketFragment();
                else
                    return new LoginRequiredFragment();
            case 2:
                return new MyAccountFragment(userName);
//                if (isLoggedIn){
//                    return new MyAccountFragment(userName);
//                }else {
//                    return new MyAccountFragment();
//                }

            default:
                return new SearchFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
