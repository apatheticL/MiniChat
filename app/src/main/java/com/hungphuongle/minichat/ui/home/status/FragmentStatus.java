package com.hungphuongle.minichat.ui.home.status;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.ui.home.FragmentHome;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.interact.UserSevice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStatus extends Fragment implements StatusAdapter.IStatus {
    private ImageView im_avatar, im_image;
    private TextView tv_startStatus;
    private RecyclerView rc_status;
    private StatusAdapter adapter;
    private UserSevice userSevice;
    private int numberLike;
    private boolean opent = false;
    private List<StatusFriendRespomse> statusResponses = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_status, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        im_avatar = view.findViewById(R.id.iv_avatar);
        im_image = view.findViewById(R.id.iv_image);
        tv_startStatus = view.findViewById(R.id.tv_startSt);
        rc_status = view.findViewById(R.id.rc_status);

        init();
        getAllStatusByFriend();
    }

    private void getAllStatusByFriend() {
        userSevice = Common.getUserService();

        userSevice.getAllStatusByFriend(CommonData.getInstance().getUserProfile().getId())
                .enqueue(new Callback<List<StatusFriendRespomse>>() {
            @Override
            public void onResponse(Call<List<StatusFriendRespomse>> call, Response<List<StatusFriendRespomse>> response) {
                statusResponses = response.body();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<StatusFriendRespomse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void init() {
        rc_status.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StatusAdapter(this);
        rc_status.setAdapter(adapter);
        im_avatar.setImageURI(Uri.parse(CommonData.getInstance().getUserProfile().getAvatar()));

    }

    @Override
    public int getCount() {
        if (statusResponses==null){
            return 0;
        }
        return statusResponses.size();
    }

    @Override
    public StatusFriendRespomse getItem(int position) {
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
        opent = true;

        FragmentHome.openFragmentComment(opent);
    }

    @Override
    public void setNumberShare(int position) {

    }


}
