package com.hungphuongle.minichat.UI.UI.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.UI.adapter.HomeAdapter;

public class HomeManagerFragment extends Fragment {
    private ViewPager vp;
    private TextView mTextMessage;
    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_manager,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vp = view.findViewById(R.id.vp);

        bottomNavigationView = view.findViewById(R.id.nav);
        initBottonNa();
        mTextMessage = view.findViewById(R.id.message);

//
    }
    private void initBottonNa(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        vp.setCurrentItem(0);
                        return true;
                    case R.id.navigation_chat:

                        vp.setCurrentItem(1);
                        return true;
                    case R.id.navigation_notifications:

                        vp.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem!=null){
                    menuItem.setChecked(false);
                }else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                menuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(vp);
    }
    private void setupViewPager(ViewPager viewPager){
        HomeAdapter adapter = new HomeAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

    }
}
