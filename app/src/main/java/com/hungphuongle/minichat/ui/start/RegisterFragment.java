package com.hungphuongle.minichat.ui.start;

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
import com.hungphuongle.minichat.ui.MainActivity;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.interact.UserService;
import com.hungphuongle.minichat.model.UserProfile;
import com.hungphuongle.minichat.model.request.BaseResponse;
import com.hungphuongle.minichat.model.request.RegisterRequest;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText edUsername, edFullname, edPhonenumber, edBirthday, edSex, edEmail, edPassword, edRetypePass;
    private Button btnRegister;
    private TextView tvLogin;
    private UserService sevice;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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
        edPhonenumber = view.findViewById(R.id.sign_up_mobile);
        edBirthday = view.findViewById(R.id.sign_up_birthday);
        edSex = view.findViewById(R.id.sign_up_copulation);
        edEmail = view.findViewById(R.id.sign_up_email);
        edPassword = view.findViewById(R.id.sign_up_pwd);
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
                Common.hideKeyBoard(getActivity());
                ((MainActivity) getActivity()).openFragmentLogin();
                break;
            case R.id.sign_up_btn:
                Common.hideKeyBoard(getActivity());
                final RegisterRequest request = new RegisterRequest();
                request.setUsername(edUsername.getText().toString());
                request.setFullname(edFullname.getText().toString());
                request.setEmail(edEmail.getText().toString());
                request.setPassword(edPassword.getText().toString());
                request.setAvatar(R.drawable.avatar+"");
                request.setBirthday((edBirthday.getText().toString()));
                request.setSex(edSex.getText().toString());
                request.setMobile(edPhonenumber.getText().toString());
                String retypePass = edRetypePass.getText().toString();
                if (retypePass.equals(edPassword.getText().toString())){
                    sevice.register(request).enqueue(new Callback<BaseResponse<UserProfile>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<UserProfile>> call, Response<BaseResponse<UserProfile>> response) {
                            if (response.body().getStatus()!=1){
                                Toast.makeText(getContext(),response.body().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                ((MainActivity)getActivity()).openFragmentLogin();
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<UserProfile>> call, Throwable t) {

                        }
                    });
                }
            default:

                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        edUsername.setText("");
        edFullname.setText("");
        edPhonenumber.setText("");
        edBirthday.setText("");
        edSex.setText("");
        edEmail.setText("");
        edPassword.setText("");
        edRetypePass.setText("");
    }
}
