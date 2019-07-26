package com.hungphuongle.minichat.UI.UI.start;

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

import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.UI.UI.MainActivity;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText edUsername, edFullname, edPhonenumber, edBirthday, edSex, edEmail, edPassword, edRetypePass;
    private Button btnRegister;
    private TextView tvLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edUsername = view.findViewById(R.id.sign_up_username);
        edFullname = view.findViewById(R.id.sign_up_fullname);
//        edPhonenumber = view.findViewById(R.id.sign_up_mobile);
//        edBirthday = view.findViewById(R.id.sign_up_birthday);
//        edSex = view.findViewById(R.id.sign_up_copulation);
        edEmail = view.findViewById(R.id.sign_up_email);
        edPassword = view.findViewById(R.id.sign_in_pwd);
        edRetypePass = view.findViewById(R.id.sign_up_retype_pass);
        btnRegister = view.findViewById(R.id.sign_up_btn);
        tvLogin = view.findViewById(R.id.to_sign_in);
        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (getId()) {
            case R.id.to_sign_in:
                ((MainActivity) getActivity()).openFragmentLogin();
                break;
            case R.id.sign_up_btn:

                default:
                break;
        }

    }
}
