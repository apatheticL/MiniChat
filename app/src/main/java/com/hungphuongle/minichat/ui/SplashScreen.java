package com.hungphuongle.minichat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hungphuongle.minichat.R;

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
                    Intent runMain = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(runMain);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        runSplash.start();
    }
}
