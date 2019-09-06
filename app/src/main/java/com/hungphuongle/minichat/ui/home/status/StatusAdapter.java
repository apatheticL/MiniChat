package com.hungphuongle.minichat.ui.home.status;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.databinding.ItemInsertStatusBinding;
import com.hungphuongle.minichat.databinding.ItemStatusBinding;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.model.request.StatusFriendRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private IStatus inter;
    private int positionClick;
    private int numberClickLike;
    private static final int INSERT_STATUS = 0;
    private static final int LIST_STATUS = 1;
//    private static final int EMPTY = 3;
    private StatusViewHolder statusViewHolder;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ImageButton delete;
    private Activity activity;
    private int id;
    private StartStatusViewHolder startStatusViewHolder;

    public StatusAdapter(IStatus inter,Activity activity) {
        this.inter = inter;
        this.activity=activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStatusBinding binding = ItemStatusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StatusViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                 statusViewHolder = (StatusViewHolder) holder;
                StatusFriendRequest srarus = inter.getItem(position);
                Glide.with(statusViewHolder.binding.ivAvatarStatus)
                        .load(srarus.getAvatarFriend())
                        .into(statusViewHolder.binding.ivAvatarStatus);
                Glide.with(statusViewHolder.binding.ivImgcontent)
                        .load(srarus.getAttachments())
                        .into(statusViewHolder.binding.ivImgcontent);

                String createtime = dateFormat.format(srarus.getCreateTime());
                statusViewHolder.binding.tvTimeStatus.setText(createtime);
                statusViewHolder.binding.tvNameAvatar.setText(srarus.getFullName());
                statusViewHolder.binding.tvContentStatus.setText(srarus.getContent() + "");
                statusViewHolder.binding.tvNumberLike.setText(srarus.getNumberLike() + "");
                statusViewHolder.binding.tvNumberComment.setText(srarus.getNumberComment() + "");
                statusViewHolder.binding.tvNumberShare.setText(srarus.getNumberShare() + "");
                delete=statusViewHolder.binding.btnMoreStatus.findViewById(R.id.btn_more_status);
                delete.setOnClickListener(this);
                statusViewHolder.binding.btnLike.setOnClickListener(this);
                setOnClickAvatar(statusViewHolder.binding.ivAvatarStatus, holder);
                if (numberClickLike%2!=0){
                    statusViewHolder.binding.btnLike.setImageResource(R.drawable.btn_like);
                }
//                else {
//                    statusViewHolder.binding.btnLike.setImageResource(R.drawable.btn_after_like);
//                }
                statusViewHolder.binding.btnComment.setOnClickListener(this);
                statusViewHolder.binding.btnShare.setOnClickListener(this);
                positionClick = holder.getAdapterPosition();


    }

    private void setOnClickAvatar(ImageView iv, final RecyclerView.ViewHolder holder){
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inter.goProfileFriend(inter.getItem(holder.getAdapterPosition()).getUserId());
                id = (inter.getItem(holder.getAdapterPosition()).getUserId());
            }
        });
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
                statusViewHolder.binding.btnLike.setImageResource(R.drawable.btn_after_like);

                break;
            case R.id.btn_comment:
                inter.dataComment(positionClick);
                break;
            case R.id.btn_share:
                inter.setNumberShare(positionClick);
                break;

            case R.id.iv_avatar_status:
                inter.goProfileFriend(id);
                break;

            case R.id.btn_more_status:
                PopupMenu popup = new PopupMenu(activity, delete);
                popup.inflate(R.menu.menu_status);
                popup.setOnMenuItemClickListener(this);
                popup.show();
                break;
        }
    }
    public ImageView getView(){
        return statusViewHolder.binding.ivImgcontent;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }


    interface IStatus {
        int getCount();

        StatusFriendRequest getItem(int position);

        void setNumberLike(int posotion, int numberClick);

        void dataComment(int position);

        void setNumberShare(int position);

        void getImage();

        void goProfileFriend(int id);

        void goFragmentAddStatus();

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
