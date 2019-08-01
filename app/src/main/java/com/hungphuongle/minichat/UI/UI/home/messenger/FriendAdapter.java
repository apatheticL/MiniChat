package com.hungphuongle.minichat.UI.UI.home.messenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hungphuongle.minichat.R;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.HolderFriend> {
    private IFriend inter;

    public FriendAdapter(IFriend inter) {
        this.inter = inter;
    }

    @NonNull
    @Override
    public HolderFriend onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderFriend(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderFriend holder, int position) {
        FriendResponse data = inter.getItem(position);
        Glide.with(holder.ivAvatar)
                .load(data.getFriendAvatar())
                .into(holder.ivAvatar);
        holder.tvUsername.setText(
                data.getFriendUsername()
        );
        holder.tvChat.setText(
                data.getFriendName()
        );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.onClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return inter.getCount();
    }

    static class HolderFriend extends RecyclerView.ViewHolder{
        private ImageView ivAvatar;
        private TextView tvUsername,tvChat;
        public HolderFriend(@NonNull View itemView) {
            super(itemView);
            ivAvatar=itemView.findViewById(R.id.iv_avatar_friend);
            tvUsername=itemView.findViewById(R.id.tv_username);
            tvChat=itemView.findViewById(R.id.tv_chat);
        }
    }

    public interface IFriend{
        int getCount();
        void onClick(int position);
        FriendResponse getItem(int position);
    }
}
