package com.example.finalproject.myticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.finalproject.R;
import com.example.finalproject.authentication.Account;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyTicketFragment extends Fragment{
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewTicketPagerAdapter viewTicketPagerAdapter;
    private String idAccount;
    private Account account;

    public MyTicketFragment(String idAccount, Account account) {
        this.idAccount = idAccount;
        this.account = account;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myticket_fragment, container, false);

        mapping(view);

        viewTicketPagerAdapter = new ViewTicketPagerAdapter(this, idAccount, account);
        viewPager.setAdapter(viewTicketPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Upcoming");
                    break;
                case 1:
                    tab.setText("Completed");
                    break;
                case 2:
                    tab.setText("Cancelled");
                    break;
            }
        }).attach();

        return view;
    }

    private void mapping(View view){
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager2) view.findViewById(R.id.viewTicketPager);
    }
}
