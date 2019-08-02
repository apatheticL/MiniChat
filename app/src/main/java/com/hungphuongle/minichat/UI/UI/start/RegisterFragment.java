package com.hungphuongle.minichat.UI.UI.start;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.UI.UI.MainActivity;
import com.hungphuongle.minichat.UI.interact.Common;
import com.hungphuongle.minichat.UI.interact.CommonData;
import com.hungphuongle.minichat.UI.interact.UserSevice;
import com.hungphuongle.minichat.UI.model.UserProfile;
import com.hungphuongle.minichat.UI.model.request.BaseResponse;
import com.hungphuongle.minichat.UI.model.request.RegisterRequest;
import com.hungphuongle.minichat.UI.socket.SocketManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText edUsername, edFullname, edPhonenumber, edBirthday, edSex, edEmail, edPassword, edRetypePass;
    private Button btnRegister;
    private TextView tvLogin;
    private UserSevice sevice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        sevice = Common.getUserService();
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
        switch (view.getId()) {
            case R.id.to_sign_in:
                ((MainActivity) getActivity()).openFragmentLogin();
                break;
            case R.id.sign_up_btn:
//                final RegisterRequest request = new RegisterRequest();
//                request.setUsername(edUsername.getText().toString());
//                request.setFullname(edFullname.getText().toString());
//                request.setEmail(edEmail.getText().toString());
//                request.setPassword(edPassword.getText().toString());
//                String retypePass = edRetypePass.getText().toString();
//                if (retypePass.equals(edPassword.getText().toString())){
//                    sevice.register(request).enqueue(new Callback<BaseResponse<UserProfile>>() {
//                        @Override
//                        public void onResponse(Call<BaseResponse<UserProfile>> call, Response<BaseResponse<UserProfile>> response) {
//                            if (response.body().getStatus()!=1){
//                                Toast.makeText(getContext(),response.body().getMessage(),
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                               registerSuccess();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<BaseResponse<UserProfile>> call, Throwable t) {
//
//                        }
//                    });
//                }
//                default:
                break;
        }

    }

    private void registerSuccess() {

        SocketManager.getInstance().connect();
        ((MainActivity)getActivity()).openFragmentLogin();
    }
}