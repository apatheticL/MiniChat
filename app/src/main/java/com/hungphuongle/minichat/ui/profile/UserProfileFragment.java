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

import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.model.request.StatusResponse;
import com.hungphuongle.minichat.ui.home.HomeActivity;
import com.hungphuongle.minichat.interact.UserService;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserProfileFragment extends Fragment implements UserProfileAdapter.IUserProfile {
    private RecyclerView rcProfile;
    private UserProfileAdapter adapter;
    private List<StatusResponse> statusResponses = new ArrayList<>();
    private UserService sevice;
    private final int PICK_IMAGE =1;
    private int numberLike;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_manager_user_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcProfile = view.findViewById(R.id.rc_user_profile);
        init();
        getAllStatus();
    }

    private void getAllStatus() {
        sevice = Common.getUserService();

        sevice.getStatusByUser(CommonData.getInstance().getUserProfile().getId()).enqueue(new Callback<List<StatusResponse>>() {
            @Override
            public void onResponse(Call<List<StatusResponse>> call, Response<List<StatusResponse>> response) {
                statusResponses = response.body();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<StatusResponse>> call, Throwable t) {

            }
        });
    }

    private void init() {
        adapter = new UserProfileAdapter(this);
        rcProfile.setLayoutManager(new LinearLayoutManager(getContext()));
        rcProfile.setAdapter(adapter);

    }

    @Override
    public int getCount() {
        if (statusResponses.size() == 0){
            return 0;
        }
        return statusResponses.size();
    }

    @Override
    public StatusResponse getItem(int position) {
        return statusResponses.get(position);
    }

    @Override
    public void setNumberLike(int posotion, int numberClick) {
        numberLike = statusResponses.get(posotion).getNumberLike();
        if (numberClick%2!=0){
            numberLike +=1;
        }
        sevice.updateNumberLikeByUser(numberLike,statusResponses.get(posotion).getId());
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

    @Override
    public void goFragmentAddStatus() {
        ((HomeActivity) getActivity()).openFragmentAddStatus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
