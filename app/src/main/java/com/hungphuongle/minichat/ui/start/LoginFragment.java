package com.hungphuongle.minichat.ui.start;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.ui.MainActivity;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.interact.UserService;
import com.hungphuongle.minichat.model.UserProfile;
import com.hungphuongle.minichat.model.request.BaseResponse;
import com.hungphuongle.minichat.model.request.LoginRequest;
import com.hungphuongle.minichat.socket.SocketManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText edUserName, edPassword;
//    private Button btnLogin;
//    private TextView tvSignup;
    private UserService userSevice;
    private View progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        userSevice = Common.getUserService();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edUserName = view.findViewById(R.id.sign_in_username);
        edPassword = view.findViewById(R.id.sign_in_pwd);
        progress = view.findViewById(R.id.progress);
        view.findViewById(R.id.to_sign_up).setOnClickListener(this);
        view.findViewById(R.id.btn_login).setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Common.hideKeyBoard(getActivity());
                progress.setVisibility(View.VISIBLE);
                Log.d("sssssssssssssssss", "onClick: ");
//                System.out.println("hsahdhbhdh djkssdkjkjds");
//                ((MainActivity)getActivity()).openFragmentHome();

                LoginRequest request = new LoginRequest();
                request.setPassword(edPassword.getText().toString());
                request.setUsername(edUserName.getText().toString());
                userSevice.login(request).enqueue(new Callback<BaseResponse<UserProfile>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<UserProfile>> call, Response<BaseResponse<UserProfile>> response) {
                        if (response.body().getStatus()!=1){
                            Toast.makeText(getContext(),response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            loginSuccess(response.body().getData());
                        }
                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<UserProfile>> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.to_sign_up:
                Common.hideKeyBoard(getActivity());
                ((MainActivity)getActivity()).openFragmentRegister();

                break;
            default:
                break;
        }
    }
    private void loginSuccess(UserProfile userProfile){
        CommonData.getInstance().setUserProfile(userProfile);
        SocketManager.getInstance().connect();
        ((MainActivity)getActivity()).openFragmentHome();
    }


}
