package com.hungphuongle.minichat.UI.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hungphuongle.minichat.UI.ProfileFragment;
import com.hungphuongle.minichat.UI.UI.chat.ChatFragment;
import com.hungphuongle.minichat.UI.UI.home.HomeFragment;

public class HomeAdapter extends FragmentPagerAdapter {
    public HomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment fragment = new HomeFragment();
                return fragment;
            case 1:
                ChatFragment f = new ChatFragment();
                return f;
            default:
                ProfileFragment fr = new ProfileFragment();
                return fr;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

}
