package com.hungphuongle.minichat.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.ui.home.HomeActivity;
import com.hungphuongle.minichat.ui.start.FragmentStart;
import com.hungphuongle.minichat.ui.start.LoginFragment;
import com.hungphuongle.minichat.ui.start.RegisterFragment;
import com.hungphuongle.minichat.socket.SocketManager;

public class MainActivity extends AppCompatActivity {
    private LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        setContentView(R.layout.activity_main);
        loginFragment= new LoginFragment();
        FragmentStart fragmentStart=new FragmentStart();
        getSupportFragmentManager().beginTransaction().add(R.id.fm_main,fragmentStart,FragmentStart.class.getName()).commit();
    }

    public void openFragmentRegister() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment1 = manager.findFragmentByTag(FragmentStart.class.getName());
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
        Fragment fragment1 = manager.findFragmentByTag(FragmentStart.class.getName());


        transaction.remove(fragment1);
        transaction.addToBackStack(null);
        transaction.commit();
        loginSuccessNotifi();
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
//        finish();

    }
    public void openFragmentLogin() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment1 = manager.findFragmentByTag(RegisterFragment.class.getName());
        Fragment fragment2 = manager.findFragmentByTag(FragmentStart.class.getName());
        transaction.hide(fragment1);
        transaction.show(fragment2);
        transaction.addToBackStack(null);
        transaction.commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fm_main,new LoginFragment(),LoginFragment.class.getName()).commit();
    }

    private void loginSuccessNotifi(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Friendly")
                .setContentText("Hello!!! Login successful")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SocketManager.getInstance().disconnect();
    }

    private void checkPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                1);
    }
}
