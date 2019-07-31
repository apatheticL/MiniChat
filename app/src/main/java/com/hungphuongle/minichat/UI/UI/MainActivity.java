package com.hungphuongle.minichat.UI.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.UI.UI.start.LoginFragment;
import com.hungphuongle.minichat.UI.UI.start.RegisterFragment;
import com.hungphuongle.minichat.UI.socket.SocketManager;

public class MainActivity extends AppCompatActivity {
    private LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFragment= new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fm_main,loginFragment,LoginFragment.class.getName()).commit();
    }

    public void openFragmentRegister() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment1 = manager.findFragmentByTag(LoginFragment.class.getName());
        RegisterFragment fragment = new RegisterFragment();

        transaction.hide(fragment1);
        transaction.add(R.id.fm_main, fragment, RegisterFragment.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fm_main,new RegisterFragment(),RegisterFragment.class.getName()).commit();
    }

    public void openFragmentHome() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment1 = manager.findFragmentByTag(LoginFragment.class.getName());
        HomeManagerFragment fragment = new HomeManagerFragment();

        transaction.remove(fragment1);
        transaction.add(R.id.fm_main, fragment, RegisterFragment.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();


    }
    public void openFragmentLogin() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment1 = manager.findFragmentByTag(RegisterFragment.class.getName());

        transaction.hide(fragment1);
        transaction.show(loginFragment);
        transaction.addToBackStack(null);
        transaction.commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fm_main,new LoginFragment(),LoginFragment.class.getName()).commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.getInstance().disconnect();
    }
}
