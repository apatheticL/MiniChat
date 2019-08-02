package com.hungphuongle.minichat.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hungphuongle.minichat.R;

public class LoginFragment extends Fragment {
    private EditText edUserName,edPassword;
    private Button btnLogin;
    private TextView tvForgotPass;
    private FloatingActionButton fbRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edUserName = view.findViewById(R.id.edt_username);
        edPassword = view.findViewById(R.id.edt_password);
        tvForgotPass = view.findViewById(R.id.fg_pass);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        fbRegister = view.findViewById(R.id.fb_regis);
        fbRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                Fragment f = getChildFragmentManager().findFragmentByTag(LoginFragment.class.getName());
                transaction.hide(f);
                transaction.add(R.id.fm_register,new RegisterFragment(),RegisterFragment.class.getName());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });



    }
}
