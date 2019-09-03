package com.hungphuongle.minichat.ui.home.status;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.databinding.FragmentAddStatusBinding;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.interact.UserSevice;
import com.hungphuongle.minichat.model.Status;
import com.hungphuongle.minichat.model.request.BaseResponse;
import com.hungphuongle.minichat.model.request.StatusResponse;
import com.hungphuongle.minichat.ui.home.HomeActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddStatusFragment extends Fragment implements View.OnClickListener {
    private FragmentAddStatusBinding binding;
    public static final int PICK_IMAGE = 1;
    public StatusResponse statusResponse;
    private String pathIM;
    private UserSevice userSevice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddStatusBinding.inflate(inflater, container, false);
        userSevice = Common.getUserService();
        statusResponse = new StatusResponse();
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        Glide.with(binding.ivAvatarStatus)
                .load(CommonData.getInstance().getUserProfile().getAvatar())
                .into(binding.ivAvatarStatus);
        binding.tvNameAvatar.setText(CommonData.getInstance().getUserProfile().getFullname());
        binding.addPhoto.setOnClickListener(this);
        binding.btnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_photo:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                break;
            case R.id.btn_post:

                statusResponse.setContent(binding.tvContentStatus.getText().toString());
                statusResponse.setUserId(CommonData.getInstance().getUserProfile().getId());
                statusResponse.setAttachments(Common.getLinkImage(statusResponse.getAttachments()));
                // van chua lay dk link anh
                userSevice.insertStatus(statusResponse).enqueue(new Callback<BaseResponse<Status>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Status>> call, Response<BaseResponse<Status>> response) {
                        Toast.makeText(getContext(),response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        ((HomeActivity)getActivity()).openFragmentHome();

                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Status>> call, Throwable t) {
                        binding.tvContentStatus.setText("");
                    }
                });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) { String path = data.getStringExtra("PATH");
                    postImage(path);
                }
                break;
        }
    }



    private void postImage( String path) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                );
        final MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        userSevice.upload(body).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
//                statusResponse.setAttachments(response.body());
                statusResponse.setAttachments( response.body().getData());
                Glide.with(getActivity())
                        .load(Common.getLinkImage(statusResponse.getAttachments()))
                        .into(binding.ivAttachments);
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
//        userSevice.upload(body).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
////                statusResponse.setAttachments(response.body());
//                    statusResponse.setAttachments( response.body().getData());
//                Glide.with(getActivity())
//                        .load(Common.getLinkImage(statusResponse.getAttachments()))
//                        .into(binding.ivAttachments);
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });
    }

}
