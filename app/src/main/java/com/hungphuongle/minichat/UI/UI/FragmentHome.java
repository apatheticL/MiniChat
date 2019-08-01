package com.hungphuongle.minichat.UI.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hungphuongle.minichat.R;

public class FragmentHome extends Fragment {
    //for set icon into tab items
//    final int[] ICONS = new int[]{
//            R.drawable.ic_home_black_24dp,
//            R.drawable.ic_message_black_24dp,
//            R.drawable.ic_notifications_black_24dp,
//            R.drawable.ic_menu_black_24dp
//    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_home_manager,container,false);
//        viewPager(view);
        return view;
    }

//    private void viewPager(View view) {
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter( getActivity().getSupportFragmentManager());
//        ViewPager viewPager = view.findViewById(R.id.view_pager);
//        viewPagerAdapter.AddFragment(new FragmentStatus());
//        viewPagerAdapter.AddFragment(new FragmentMess());
//        viewPagerAdapter.AddFragment(new FragmentNotification());
//        viewPager.setAdapter(viewPagerAdapter);
//        TabLayout tabs = view.findViewById(R.id.tapbar);
//        tabs.getTabAt(0).setIcon(ICONS[0]);
//        tabs.getTabAt(1).setIcon(ICONS[1]);
//        tabs.getTabAt(2).setIcon(ICONS[2]);
//        tabs.getTabAt(3).setIcon(ICONS[3]);
//
//        tabs.setupWithViewPager(viewPager);
//        viewPager.setOffscreenPageLimit(4);
//    }
}
