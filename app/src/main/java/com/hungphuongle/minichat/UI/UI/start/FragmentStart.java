package com.hungphuongle.minichat.UI.UI.start;

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

public class FragmentStart extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_start,container,false);
        viewPager(view);
        return view;
    }

    private void viewPager(View view) {
        AdapterTabStart adapterTabStart = new AdapterTabStart( getActivity().getSupportFragmentManager(),getContext());
        ViewPager viewPager = view.findViewById(R.id.vp_start);
        TabLayout tabs = view.findViewById(R.id.tab_start);
        viewPager.setAdapter(adapterTabStart);
        tabs.setupWithViewPager(viewPager);
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.icons));
        tabs.setTabTextColors(getResources().getColor(R.color.grey_500), getResources().getColor(R.color.icons));
        viewPager.setOffscreenPageLimit(2);
    }
}
