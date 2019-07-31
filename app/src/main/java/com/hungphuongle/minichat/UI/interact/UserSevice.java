package com.hungphuongle.minichat.UI.interact;

import com.hungphuongle.minichat.UI.model.UserProfile;
import com.hungphuongle.minichat.UI.model.request.BaseResponse;
import com.hungphuongle.minichat.UI.model.request.LoginRequest;
import com.hungphuongle.minichat.UI.model.request.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserSevice {
    @POST(value = "/login")
    Call<BaseResponse<UserProfile>>login(
            @Body LoginRequest request
            );
    @POST(value = "/register")
    Call<BaseResponse<UserProfile>>register(
            @Body RegisterRequest request
            );
}
