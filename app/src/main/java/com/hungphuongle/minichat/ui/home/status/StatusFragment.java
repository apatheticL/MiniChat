package com.hungphuongle.minichat.ui.home.status;

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
import com.hungphuongle.minichat.model.request.StatusFriendRequest;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.interact.UserSevice;
import com.hungphuongle.minichat.ui.MainActivity;
import com.hungphuongle.minichat.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusFragment extends Fragment implements StatusAdapter.IStatus {
    private RecyclerView rcStatus;
    private StatusAdapter adapter;
    private UserSevice userSevice;
    public static final int PICK_IMAGE=1;
    private int numberLike;
    private List<StatusFriendRequest> statusResponses = new ArrayList<>();




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_status, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcStatus = view.findViewById(R.id.rc_status);
        getAllStatusByFriend();
        init();
    }

    private void getAllStatusByFriend() {
        userSevice = Common.getUserService();

        userSevice.getAllStatusByFriend(CommonData.getInstance().getUserProfile().getId())
                .enqueue(new Callback<List<StatusFriendRequest>>() {
            @Override
            public void onResponse(Call<List<StatusFriendRequest>> call, Response<List<StatusFriendRequest>> response) {
                statusResponses = response.body();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<StatusFriendRequest>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void init() {
        rcStatus.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StatusAdapter(this);
        rcStatus.setAdapter(adapter);
//        im_avatar.setImageURI(Uri.parse(CommonData.getInstance().getUserProfile().getAvatar()));

    }

    @Override
    public int getCount() {
        if (statusResponses==null){
            return 0;
        }
        return statusResponses.size();
    }

    @Override
    public StatusFriendRequest getItem(int position) {
        if (position==0){

       }
        return statusResponses.get(position);

    }

    @Override
    public void setNumberLike(int posotion,int numberClick) {
        numberLike = statusResponses.get(posotion).getNumberLike();
        if (numberClick%2!=0){
            numberLike +=1;
        }
    }

    @Override
    public void dataComment(int position) {
        if (statusResponses==null){
            return;
        }else {
            ((HomeActivity) getActivity()).openFragmentComment(statusResponses.get(position).getId());
        }
    }

    @Override
    public void setNumberShare(int position) {

    }
    public void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void goPorofile() {
        // di den trang ca nhan
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