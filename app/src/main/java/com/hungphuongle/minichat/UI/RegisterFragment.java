package com.hungphuongle.minichat.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.hungphuongle.minichat.Model.Account;
import com.hungphuongle.minichat.R;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText edUserName, edPass, edConfirmPass;
    private DatabaseReference mData;
    private Account account;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        edUserName = view.findViewById(R.id.edt_username);
        edPass = view.findViewById(R.id.edt_password);
        edConfirmPass = view.findViewById(R.id.edt_confirm_password);
        view.findViewById(R.id.btn_register).setOnClickListener(this);
        view.findViewById(R.id.btn_login).setOnClickListener(this);
        return view;
    }

    private void init() {
        String userName  = edUserName.getText().toString();
        String pass  = edPass.getText().toString();
        String confirm  = edConfirmPass.getText().toString();
        if (pass.equals(confirm)==true){
            account=new Account(userName,pass);
        }
        else {
            // dua ra thong bao
        }
    }

    @Override
    public void onClick(View view) {
        switch (getId()) {
            case R.id.btn_register:
                mData.setValue(account);
                break;
            default:

                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = FirebaseDatabase.getInstance().getReference();
        init();

    }
}
