package com.hungphuongle.minichat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.model.UserProfile;
import com.hungphuongle.minichat.ui.home.HomeActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread runSplash =new Thread(){
            @Override
            public void run() {
                try{
                    sleep(1500);
                    UserProfile userProfile = ShareUtil.getUserProfile(SplashScreen.this);
                    if (userProfile != null){
                        CommonData.getInstance().setUserProfile(userProfile);
                        Intent runMain = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(runMain);
                    }else {
                        Intent runMain = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(runMain);

                    }

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        runSplash.start();
    }
}
