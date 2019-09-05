package com.hungphuongle.minichat.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungphuongle.minichat.GlideApp;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.model.request.MessageChatResponse;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SEND_TEXT = 0;
    private static final int RECEIVE_TEXT = 1;
    private static final int SEND_IMG = 2;
    private static final int RECEIVE_IMG = 3;
    private IChat inter;

    public ChatAdapter(IChat inter) {
        this.inter = inter;
    }

    @Override
    public int getItemViewType(int position) {
        String type = inter.getData(position).getType();
        if (inter.getData(position).getSenderId()
                == CommonData.getInstance().getUserProfile().getId()) {
            if (type == null || type.equals(MessageChatResponse.TYPE_TEXT)) {
                return SEND_TEXT;
            }
            return SEND_IMG;
        }
        if (type == null || type.equals(MessageChatResponse.TYPE_TEXT)) {
            return RECEIVE_TEXT;
        }
        return RECEIVE_IMG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int itemViewType) {
        switch (itemViewType) {
            case SEND_TEXT:
                return new SendViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_send, parent, false));
            case SEND_IMG:
                return new SendImageViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_send_image, parent, false));
            case RECEIVE_TEXT:
                return new ReceiverViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_reviece, parent, false));
            default:
                return new ReceiveImageViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_receive_image, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        MessageChatResponse message = inter.getData(position);

        switch (getItemViewType(position)) {
            case SEND_TEXT:
                SendViewHolder sendViewHolder = (SendViewHolder) viewHolder;
                Glide.with(sendViewHolder.ivAvatar)
                        .load(message.getUserAvatar())
                        .error(R.drawable.avatar)
                        .into(sendViewHolder.ivAvatar);

                sendViewHolder.tvContent.setText(message.getContent());
                break;
            case RECEIVE_TEXT:
                ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) viewHolder;
                Glide.with(receiverViewHolder.ivAvatar)
                        .load(message.getUserAvatar())
                        .error(R.drawable.avatar)
                        .into(receiverViewHolder.ivAvatar);
                receiverViewHolder.tvContent.setText(message.getContent());
                break;
            case SEND_IMG:
                SendImageViewHolder sendImage = (SendImageViewHolder) viewHolder;
                GlideApp.with(sendImage.im)
                        .load(Common.getLinkImage(message.getContent()))
                        .error(R.drawable.avatar)
                        .placeholder(R.drawable.avatar)
                        .into(sendImage.im);
                break;
            case RECEIVE_IMG:
                ReceiveImageViewHolder reImage = (ReceiveImageViewHolder) viewHolder;
                GlideApp.with(reImage.im)
                        .load(Common.getLinkImage(message.getContent()))
                        .error(R.drawable.avatar)
                        .placeholder(R.drawable.avatar)
                        .into(reImage.im);
                break;
            default:
                break;
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

    static class SendImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView im;

        public SendImageViewHolder(@NonNull View itemView) {
            super(itemView);
            im = itemView.findViewById(R.id.iv_img);
        }
    }

    static class ReceiveImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView im, avatar;

        public ReceiveImageViewHolder(@NonNull View itemView) {
            super(itemView);
            im = itemView.findViewById(R.id.iv_img);
            avatar = itemView.findViewById(R.id.iv_avatar);
        }
    }
}
