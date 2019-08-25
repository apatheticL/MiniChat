package com.hungphuongle.minichat.interact;

import com.hungphuongle.minichat.model.Comment;
import com.hungphuongle.minichat.model.Status;
import com.hungphuongle.minichat.model.request.CommentRequest;
import com.hungphuongle.minichat.model.request.StatusResponse;
import com.hungphuongle.minichat.ui.home.messenger.FriendResponse;
import com.hungphuongle.minichat.model.request.StatusFriendRequest;
import com.hungphuongle.minichat.model.UserProfile;
import com.hungphuongle.minichat.model.request.BaseResponse;
import com.hungphuongle.minichat.model.request.LoginRequest;
import com.hungphuongle.minichat.model.request.RegisterRequest;
import java.util.List;
import java.util.ListIterator;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
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

//    @GET(value = "/getStatusByFriendUser/{id}")
//    Call<List<StatusFriendRequest>> getAllStatusByFriend(@Path("id") int id);

    @GET(value = "/getAllCommentByStatus")
    Call<List<CommentRequest>> getAllCommentByStatus(@Query("statusid") int statusid);

    @POST(value = "/insertStatus")
    Call<BaseResponse<Status>> insertStatus(@Body StatusResponse statusResponse);

    @Multipart
    @POST("postImage")
    Call<String> upload(
            @Part MultipartBody.Part image
    );

    @POST(value = "/insertComment")
    Call<BaseResponse<Comment>> insertComment(@Body CommentRequest commentRequest);


    @GET(value = "/getStatus")
    Call<List<StatusFriendRequest>> getStatus(@Query("id") int id);

    @POST(value = "/updateNumberLikeByUser")
    Call<BaseResponse<Status>> updateNumberLikeByUser(@Query("newNumberLike") int newNumberLike, @Query("statusId") int statusId);

    @GET(value = "/getNumberShare")
    Call<Integer>getNumberShare(@Query("id")int id);

    @GET(value = "/getNumberLike")
    Call<Integer>getNumberLike(@Query("id")int id);
    @GET(value = "/getNumberComment")
    Call<Integer>getNumberComment(@Query("id")int id);

    @POST(value = "/deleteStatusById")
    Call<List<StatusResponse>>deleteStatusById(@Query("id")int id);
    @POST(value = "/shareStatusByUser")
    Call<BaseResponse<Status>>shareStatusByUser(@Body StatusResponse statusResponse);

    @GET (value = "/getStatusByUser")
    Call<List<StatusResponse>>getStatusByUser(@Query("Id") int Id);
}