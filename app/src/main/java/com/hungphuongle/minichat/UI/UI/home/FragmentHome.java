package com.hungphuongle.minichat.UI.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.hungphuongle.minichat.R;

public class FragmentHome extends Fragment {
    //for set icon into tab items
    final int[] ICONS = new int[]{
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_message_black_24dp,
            R.drawable.ic_notifications_black_24dp,
            R.drawable.ic_menu_black_24dp
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_home,container,false);
        viewPager(view);
        return view;
    }

    private void viewPager(View view) {
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabs = view.findViewById(R.id.tapbar);


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter( getActivity().getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new FragmentStatus(),"home");
        viewPagerAdapter.AddFragment(new FragmentMessenger(),"messenger");
        viewPagerAdapter.AddFragment(new FragmentNotification(),"notification");
        viewPagerAdapter.AddFragment(new FragmentMenu(),"menu");
        viewPager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(viewPager);

        tabs.getTabAt(0).setIcon(ICONS[0]);
        tabs.getTabAt(1).setIcon(ICONS[1]);
        tabs.getTabAt(2).setIcon(ICONS[2]);
        tabs.getTabAt(3).setIcon(ICONS[3]);

        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.grey_100));
        viewPager.setOffscreenPageLimit(4);
    }
}
