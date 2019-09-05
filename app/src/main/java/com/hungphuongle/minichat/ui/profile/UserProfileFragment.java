package com.hungphuongle.minichat.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungphuongle.minichat.GlideApp;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.databinding.FragManagerUserProfileBinding;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.model.UserProfile;
import com.hungphuongle.minichat.model.request.BaseResponse;
import com.hungphuongle.minichat.model.request.StatusResponse;
import com.hungphuongle.minichat.ui.home.HomeActivity;
import com.hungphuongle.minichat.interact.UserService;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserProfileFragment extends Fragment implements UserProfileAdapter.IUserProfile {
    private UserProfileAdapter adapter;
    private List<StatusResponse> statusResponseList = new ArrayList<>();
    private UserService sevice;
    private final int PICK_IMAGE =1;
    private int numberLike;
    private int id;
    private UserProfile userProfile;
    private FragManagerUserProfileBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragManagerUserProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        getInfo(id);
        getAllStatus();
        loadProfile();
    }

    private void getAllStatus() {
        sevice = Common.getUserService();
        if (id == CommonData.getInstance().getUserProfile().getId()){
        sevice.getStatusByUser(CommonData.getInstance().getUserProfile().getId()).enqueue(new Callback<List<StatusResponse>>() {
            @Override
            public void onResponse(Call<List<StatusResponse>> call, Response<List<StatusResponse>> response) {

                statusResponseList = response.body();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<StatusResponse>> call, Throwable t) {

            }
        });}
        else {
            sevice.getStatusByUser(id).enqueue(new Callback<List<StatusResponse>>() {
                @Override
                public void onResponse(Call<List<StatusResponse>> call, Response<List<StatusResponse>> response) {

                    statusResponseList = response.body();
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<StatusResponse>> call, Throwable t) {

                }
            });
        }
    }

    private void init() {
        adapter = new UserProfileAdapter(this);
        binding.rcUserProfile.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcUserProfile.setAdapter(adapter);

    }

    @Override
    public int getCount() {
        if (statusResponseList.size() == 0){
            return 0;
        }
        return statusResponseList.size();
    }

    @Override
    public StatusResponse getItem(int position) {
        return statusResponseList.get(position);
    }

    @Override
    public void setNumberLike(int posotion, int numberClick) {
        numberLike = statusResponseList.get(posotion).getNumberLike();
        if (numberClick%2!=0){
            numberLike +=1;
        }
        sevice.updateNumberLikeByUser(numberLike, statusResponseList.get(posotion).getId());
    }

    @Override
    public void dataComment(int position) {

    }

    @Override
    public void setNumberShare(int position) {

    }

    @Override
    public void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void goPorofile() {
        adapter.notifyDataSetChanged();
    }

    private void loadProfile(){
        sevice.getUser(id).enqueue(new Callback<BaseResponse<UserProfile>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserProfile>> call, Response<BaseResponse<UserProfile>> response) {
                if (response.body() != null ){
                    userProfile = response.body().getData();
                    if (userProfile.getAvatar() != null ){
                        Glide.with(getContext())
                                .load(userProfile.getAvatar())
                                .placeholder(R.drawable.user)
                                .error(R.drawable.ic_error_outline_black_48dp)
                                .into(binding.imAvatar);
                        binding.tvFullnameTool.setText(userProfile.getFullname());
                        binding.tvPhone.setText(
                                (userProfile.getPhoneNumber() == null || userProfile.getPhoneNumber().equals("")) ?
                                "No phonenumber" : userProfile.getPhoneNumber());
                        binding.tvEmail.setText(
                                (userProfile.getEmail() == null || userProfile.getEmail().equals("")) ?
                                        "No Email" : userProfile.getEmail());
                        binding.tvSex.setText(
                                (userProfile.getSex() == null || userProfile.getSex().equals("")) ?
                                        "No Sex" : userProfile.getSex());
                        binding.tvBirthday.setText(
                                (userProfile.getBirthday() == null || userProfile.getBirthday().equals("")) ?
                                        "No Birthday" : userProfile.getBirthday());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<UserProfile>> call, Throwable t) {

            }
        });
    }

    @Override
    public UserProfile getItemPosition() {
        final UserProfile userProfile = new UserProfile();

        return null;
    }

//    @Override
//    public void goFragmentAddStatus() {
//        ((HomeActivity) getActivity()).openFragmentAddStatus();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void getInfo(int id) {
        this.id = id;


    }
}
