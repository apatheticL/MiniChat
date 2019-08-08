package com.hungphuongle.minichat.ui.home.status;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.databinding.ItemStatusBinding;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> implements View.OnClickListener {
    private IStatus inter;
    private int positionClick;
    private int numberClickLike;

    public StatusAdapter(IStatus inter) {
        this.inter = inter;
    }


    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStatusBinding binding = ItemStatusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StatusViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        StatusFriendRespomse srarus = inter.getItem(position);
        Glide.with(holder.binding.ivAvatarStatus)
                .load(srarus.getAvatarFriend())
                .into(holder.binding.ivAvatarStatus);
        Glide.with(holder.binding.ivImgcontent)
                .load(srarus.getAttachments())
                .into(holder.binding.ivImgcontent);
        holder.binding.tvTimeStatus.setText(srarus.getCreateTime() + "");
        holder.binding.tvNameAvatar.setText(srarus.getFullName());
        holder.binding.tvContentStatus.setText(srarus.getContent() + "");
        holder.binding.tvNumberLike.setText(srarus.getNumberLike()+"");
        holder.binding.tvNumberComment.setText(srarus.getNumberComment()+"");
        holder.binding.tvNumberShare.setText(srarus.getNumberShare()+"");
        holder.binding.btnLike.setOnClickListener(this);
        holder.binding.btnComment.setOnClickListener(this);
        holder.binding.btnShare.setOnClickListener(this);
        positionClick = holder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return inter.getCount();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_like:
                numberClickLike++;
                inter.setNumberLike(positionClick,numberClickLike);
                break;
            case R.id.btn_comment:
                inter.dataComment(positionClick);
                break;
            case R.id.btn_share:
                inter.setNumberShare(positionClick);
                break;
            default:
                break;
        }
    }

    interface IStatus {
        int getCount();

        StatusFriendRespomse getItem(int position);

        void setNumberLike(int posotion, int numberClick);

        void dataComment(int position);

        void setNumberShare(int position);
    }

    static class StatusViewHolder extends RecyclerView.ViewHolder {
        private ItemStatusBinding binding;


        public StatusViewHolder(ItemStatusBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
