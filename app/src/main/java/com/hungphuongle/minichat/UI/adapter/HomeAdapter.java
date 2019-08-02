package com.hungphuongle.minichat.UI.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hungphuongle.minichat.UI.ProfileFragment;
import com.hungphuongle.minichat.UI.UI.chat.MessageFragment;
import com.hungphuongle.minichat.UI.UI.home.FragmentHome;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();

    public HomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentHome fragment = new FragmentHome();
                return fragment;
            case 1:
                MessageFragment f = new MessageFragment();
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
