package com.hungphuongle.minichat.UI.UI.chat;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hungphuongle.minichat.R;

public class ChatActivity  extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_chat);
        getSupportFragmentManager().beginTransaction().add(R.id.content,new MessageFragment(),MessageFragment.class.getName()).commit();
    }
}
