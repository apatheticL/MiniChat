package com.hungphuongle.minichat.interact;

import com.hungphuongle.minichat.ui.home.messenger.FriendResponse;
import com.hungphuongle.minichat.ui.home.status.StatusFriendRespomse;
import com.hungphuongle.minichat.model.UserProfile;
import com.hungphuongle.minichat.model.request.BaseResponse;
import com.hungphuongle.minichat.model.request.LoginRequest;
import com.hungphuongle.minichat.model.request.RegisterRequest;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserSevice {
    @POST(value = "/login")
    Call<BaseResponse<UserProfile>> login(
            @Body LoginRequest request
    );

    @POST(value = "/register")
    Call<BaseResponse<UserProfile>> register(
            @Body RegisterRequest request
    );

    @GET(value = "/getAllFriend")
    Call<List<FriendResponse>> getAllFriend(
            @Query("id") int id
    );

    @GET(value = "/getStatusByFriendUser/{id}")
    Call<List<StatusFriendRespomse>> getAllStatusByFriend(@Path("id") int id);

}
