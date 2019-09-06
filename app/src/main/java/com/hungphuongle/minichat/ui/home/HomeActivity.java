package com.hungphuongle.minichat.ui.home;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.socket.SocketManager;
import com.hungphuongle.minichat.ui.home.status.AddStatusFragment;
import com.hungphuongle.minichat.ui.home.status.comment.CommentFragment;
import com.hungphuongle.minichat.ui.home.status.StatusFragment;
import com.hungphuongle.minichat.ui.profile.UserProfileFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (CommonData.getInstance().getUserProfile() != null){
            SocketManager.getInstance().connect();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.content,  new HomeFragment(),HomeFragment.class.getName()).commit();
    }
    public void openFragmentComment(int id) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment1 = manager.findFragmentByTag(HomeFragment.class.getName());
        CommentFragment fragment = new CommentFragment();
        fragment.sendIdStatus(id);
        transaction.hide(fragment1);
        transaction.add(R.id.content, fragment, CommentFragment.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    public void openFragmentAddStatus() {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        Fragment fragment1 = manager.findFragmentByTag(HomeFragment.class.getName());
//        AddStatusFragment fragment = new AddStatusFragment();
//        transaction.hide(fragment1);
//        transaction.add(R.id.content, fragment,AddStatusFragment.class.getName());
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
    public void openFragmentAddStatus() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddStatusFragment fragment = new AddStatusFragment();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(HomeFragment.class.getName());
        fragmentTransaction.commit();
    }
    public void openFragmentHome() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment1 = manager.findFragmentByTag(HomeFragment.class.getName());
        Fragment fragment2= manager.findFragmentByTag(AddStatusFragment.class.getName());
        transaction.remove(fragment2);
        transaction.show(fragment1);
        ((HomeFragment)fragment1).loadFragment();
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    public void openProfileFragment() {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        Fragment fragment1 = manager.findFragmentByTag(HomeFragment.class.getName());
////        Fragment fragment =  manager.findFragmentByTag(AddStatusFragment.class.getName());
//        UserProfileFragment fragment2 = new UserProfileFragment();
//        transaction.hide(fragment1);
////        transaction.hide(fragment);
//        transaction.add(R.id.content, fragment2, UserProfileFragment.class.getName());
//
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
    public void openProfileFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserProfileFragment fragment = new UserProfileFragment();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(HomeFragment.class.getName());
        fragmentTransaction.commit();
    }

    public void openFragmentProfileFriend(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.getInfo(id);
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(HomeFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.getInstance().disconnect();
    }
}
