package com.example.finalproject.myticket;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.finalproject.authentication.Account;

public class ViewTicketPagerAdapter extends FragmentStateAdapter {
    private String idAccount;
    private Account account;

    public ViewTicketPagerAdapter(@NonNull MyTicketFragment fragmentActivity, String idAccount, Account account) {
        super(fragmentActivity);
        this.idAccount = idAccount;
        this.account = account;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UpcomingTicketFragment(idAccount, account);
            case 1:
                return new CompletedTicketFragment(idAccount, account);
            case 2:
                return new CancelledTicketFragment(idAccount, account);

            default:
                return new UpcomingTicketFragment(idAccount, account);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }



}
