package com.hungphuongle.minichat.UI.UI.home.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hungphuongle.minichat.R;

public class FragmentNotification extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_notification,container,false);
        return view;
    }
}
