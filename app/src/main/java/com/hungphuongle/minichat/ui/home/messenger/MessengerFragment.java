package com.hungphuongle.minichat.ui.home.messenger;

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
import com.hungphuongle.minichat.ui.chat.ChatActivity;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.interact.UserSevice;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessengerFragment extends Fragment implements FriendAdapter.IFriend {
    private RecyclerView rcFriend;
    private List<FriendResponse> friendResponses;
    private FriendAdapter adapter;
    private UserSevice userService;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_friend,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcFriend = view.findViewById(R.id.rc_friend);
        rcFriend.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FriendAdapter(this);
        rcFriend.setAdapter(adapter);
        getAllFriend();

    }

    private void getAllFriend() {
        userService = Common.getUserService();
        userService.getAllFriend(
                CommonData.getInstance().getUserProfile().getId())
                .enqueue(new Callback<List<FriendResponse>>() {
                    @Override
                    public void onResponse(Call<List<FriendResponse>> call, Response<List<FriendResponse>> response) {
                        friendResponses = response.body();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<FriendResponse>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public int getCount() {
        if (friendResponses==null){
            return 0;
        }
        return friendResponses.size();

    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent();
        intent.setClass(getContext(), ChatActivity.class);
        intent.putExtra("FRIEND", friendResponses.get(position));
        startActivity(intent);
    }

    @Override
    public FriendResponse getItem(int position) {
        return friendResponses.get(position);
    }
}
