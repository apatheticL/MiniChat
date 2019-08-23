package com.hungphuongle.minichat.ui.home.status;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.databinding.ItemInsertStatusBinding;
import com.hungphuongle.minichat.databinding.ItemStatusBinding;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.model.request.StatusFriendRequest;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private IStatus inter;
    private int positionClick;
    private int numberClickLike;
    private static final int INSERT_STATUS = 0;
    private static final int LIST_STATUS = 1;
    private static final int EMPTY = 2;
    private StatusViewHolder statusViewHolder;

    public StatusAdapter(IStatus inter) {
        this.inter = inter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case INSERT_STATUS:
                ItemInsertStatusBinding binding1 = ItemInsertStatusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new StartStatusViewHolder(binding1);
            case LIST_STATUS:
                ItemStatusBinding binding = ItemStatusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new StatusViewHolder(binding);
            case EMPTY:
                // rong
                break;
                default:
                    break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case INSERT_STATUS:
                StartStatusViewHolder startStatusViewHolder = (StartStatusViewHolder) holder;
                Glide.with(startStatusViewHolder.binding.ivAvatar)
                        .load(CommonData.getInstance().getUserProfile().getAvatar())
                        .into(startStatusViewHolder.binding.ivAvatar);
                startStatusViewHolder.binding.ivImage.setOnClickListener(this);
                startStatusViewHolder.binding.tvContentInsert.setOnClickListener(this);
                break;
            case LIST_STATUS:
                 statusViewHolder = (StatusViewHolder) holder;
                StatusFriendRequest srarus = inter.getItem(position);
                Glide.with(statusViewHolder.binding.ivAvatarStatus)
                        .load(srarus.getAvatarFriend())
                        .into(statusViewHolder.binding.ivAvatarStatus);
                Glide.with(statusViewHolder.binding.ivImgcontent)
                        .load(srarus.getAttachments())
                        .into(statusViewHolder.binding.ivImgcontent);
                statusViewHolder.binding.tvTimeStatus.setText(srarus.getCreateTime() + "");
                statusViewHolder.binding.tvNameAvatar.setText(srarus.getFullName());
                statusViewHolder.binding.tvContentStatus.setText(srarus.getContent() + "");
                statusViewHolder.binding.tvNumberLike.setText(srarus.getNumberLike() + "");
                statusViewHolder.binding.tvNumberComment.setText(srarus.getNumberComment() + "");
                statusViewHolder.binding.tvNumberShare.setText(srarus.getNumberShare() + "");
                statusViewHolder.binding.btnLike.setOnClickListener(this);
                if (numberClickLike%2!=0){
                    statusViewHolder.binding.btnLike.setImageResource(R.drawable.btn_like);
                }
                else {
                    statusViewHolder.binding.btnLike.setImageResource(R.drawable.btn_after_like);
                }
                statusViewHolder.binding.btnComment.setOnClickListener(this);
                statusViewHolder.binding.btnShare.setOnClickListener(this);
                positionClick = holder.getAdapterPosition();
                break;
            case EMPTY:

                //rong
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return INSERT_STATUS;
        }
        Object recyclerViewItem = inter.getItem(position);
        if (recyclerViewItem instanceof StatusFriendRequest) {
            return LIST_STATUS;
        }
        return EMPTY;

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
                inter.setNumberLike(positionClick, numberClickLike);
                break;
            case R.id.btn_comment:
                inter.dataComment(positionClick);
                break;
            case R.id.btn_share:
                inter.setNumberShare(positionClick);
                break;
            case R.id.iv_image:
                inter.getImage();
                break;
            case R.id.iv_avatar:
                inter.goPorofile();
            case R.id.tv_content_insert:
                inter.goFragmentAddStatus();
        }
    }

    interface IStatus {
        int getCount();

        StatusFriendRequest getItem(int position);

        void setNumberLike(int posotion, int numberClick);

        void dataComment(int position);

        void setNumberShare(int position);

        void getImage();

        void goPorofile();

        void goFragmentAddStatus();
//        int getCountLike(int position);
//        int getCountComment(int position);
//        int getCountShare(int position);
    }

    static class StatusViewHolder extends RecyclerView.ViewHolder {
        private ItemStatusBinding binding;

        public StatusViewHolder(ItemStatusBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    static class StartStatusViewHolder extends RecyclerView.ViewHolder {
        private ItemInsertStatusBinding binding;

        public StartStatusViewHolder(ItemInsertStatusBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
