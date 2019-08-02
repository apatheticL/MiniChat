package com.hungphuongle.minichat.UI.UI.home;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hungphuongle.minichat.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().add(R.id.content, new FragmentHome()).commit();
    }
}
