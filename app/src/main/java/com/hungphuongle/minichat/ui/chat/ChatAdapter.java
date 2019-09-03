package com.hungphuongle.minichat.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.model.UserProfile;
import com.hungphuongle.minichat.model.request.MessageChatResponse;
import com.hungphuongle.minichat.ui.home.messenger.FriendResponse;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private IChat inter;
    private UserProfile userProfile;

    public ChatAdapter(IChat inter,UserProfile userProfile) {
        this.inter = inter;
        this.userProfile=userProfile;
    }

    @Override
    public int getItemViewType(int position) {
        if (inter.getData(position).getSenderId() == CommonData.getInstance().getUserProfile().getId()) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        if (itemViewType == 0) {
            return new SendViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_send, viewGroup, false));
        }
        return new ReceiverViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reviece, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder viewHolder, int position) {
        MessageChatResponse message = inter.getData(position);

        if (getItemViewType(position) == 0) {
            SendViewHolder holder = (SendViewHolder) viewHolder;
            Glide.with(holder.ivAvatar)
                    .load(message.getUserAvatar())
                    .error(R.drawable.avatar)
                    .into(holder.ivAvatar);

            holder.tvContent.setText(message.getContent());
        } else {
            ReceiverViewHolder holder = (ReceiverViewHolder) viewHolder;
            Glide.with(holder.ivAvatar)
                    .load(message.getUserAvatar())
                    .error(R.drawable.avatar)
                    .into(holder.ivAvatar);
            holder.tvContent.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return inter.getCount();
    }

    public interface IChat {
        int getCount();

        MessageChatResponse getData(int position);
    }

    static class SendViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvContent;

        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvContent;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
