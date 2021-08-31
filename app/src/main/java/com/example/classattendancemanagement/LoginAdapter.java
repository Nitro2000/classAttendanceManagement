package com.example.classattendancemanagement;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTab;

    public LoginAdapter(FragmentManager fm, Context cn, int totalTabs) {
        super(fm);
        this.context = cn;
        this.totalTab = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new LoginFragment();
        } else return null;
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
