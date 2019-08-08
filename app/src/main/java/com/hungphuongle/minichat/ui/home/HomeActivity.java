package com.hungphuongle.minichat.ui.home;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.ui.home.status.comment.CommentFragment;
import com.hungphuongle.minichat.ui.home.status.FragmentStatus;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().add(R.id.content, new FragmentHome()).commit();
    }

    public void createFragmentComment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment1 = manager.findFragmentByTag(FragmentStatus.class.getName());
        CommentFragment fragment = new CommentFragment();

        transaction.hide(fragment1);
        transaction.add(R.id.content, fragment, CommentFragment.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
