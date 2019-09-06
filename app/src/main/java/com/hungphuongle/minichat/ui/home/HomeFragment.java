package com.hungphuongle.minichat.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.ui.home.menu.MenuFragment;
import com.hungphuongle.minichat.ui.home.messenger.MessengerFragment;
import com.hungphuongle.minichat.ui.home.notification.NotificationFragment;
import com.hungphuongle.minichat.ui.home.status.StatusFragment;
import com.hungphuongle.minichat.ui.search.SearchActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final int PICK_IMAGE = 19;
    private  ViewPager viewPager;
    private ImageButton btnCamera;
    private TextView search;
    private   ViewPagerAdapter viewPagerAdapter;
    private View view;
    //for set icon into tab items
    final int[] ICONS = new int[]{
            R.drawable.icon_home,
            R.drawable.icon_messenger,
            R.drawable.icon_notification,
            R.drawable.icon_menu
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_home, container, false);
        viewPager(view);
        btnCamera = view.findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(this);

        search=view.findViewById(R.id.tv_find);
        search.setOnClickListener(this);
        return view;
    }

    private void viewPager(View view) {
        viewPager= view.findViewById(R.id.view_pager);
        TabLayout tabs = view.findViewById(R.id.tapbar);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new StatusFragment(), "home");
        viewPagerAdapter.addFragment(new MessengerFragment(), "messenger");
        viewPagerAdapter.addFragment(new NotificationFragment(), "notification");
        viewPagerAdapter.addFragment(new MenuFragment(tabs), "menu");
        viewPager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(ICONS[0]);
        tabs.getTabAt(1).setIcon(ICONS[1]);
        tabs.getTabAt(2).setIcon(ICONS[2]);
        tabs.getTabAt(3).setIcon(ICONS[3]);

        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.grey_100));

        viewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                break;
            case R.id.tv_find:
                Intent intentSearch = new Intent(getActivity(),SearchActivity.class);
//                intentSearch.setClass(getContext(), SearchActivity.class);
                startActivity(intentSearch);
                break;
        }
    }

    //kết quả trả về sau khi click item image trong button camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode){
//            case PICK_IMAGE:
//                String path = CommonPostImage.getPath(getActivity(),data);
//
//                StatusResponse statusResponse = new StatusResponse();
//                StatusFragment statusFragment = (StatusFragment)viewPagerAdapter.getItem(0);
//                StatusAdapter statusAdapter = statusFragment.getAdapter();
//                CommonPostImage.postImage(path,statusResponse,getActivity(),statusAdapter.getView());
//        }
    }
    public void loadFragment(){
        ((StatusFragment)viewPagerAdapter.getItem(0)).getAllStatusByFriend();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
    }
}
