package com.hungphuongle.minichat.ui.home.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.socket.SocketManager;
import com.hungphuongle.minichat.ui.home.HomeFragment;
import com.hungphuongle.minichat.ui.home.status.comment.CommentFragment;
import com.hungphuongle.minichat.ui.start.FragmentStart;

public class MenuFragment extends Fragment implements View.OnClickListener {
    private CardView home, logout;
    private TabLayout tabLayout;

    public MenuFragment(TabLayout tabLayout){
        this.tabLayout=tabLayout;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu,container,false);

        home=view.findViewById(R.id.menu_home);
        logout=view.findViewById(R.id.menu_logout);
        home.setOnClickListener(this);
        logout.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_home:
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();

                break;
            case R.id.menu_logout:
                SocketManager.getInstance().disconnect();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment1 = manager.findFragmentByTag(HomeFragment.class.getName());
                FragmentStart fragment = new FragmentStart();
                transaction.remove(fragment1);
                transaction.add(R.id.content, fragment, FragmentStart.class.getName());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SocketManager.getInstance().disconnect();
    }
}
