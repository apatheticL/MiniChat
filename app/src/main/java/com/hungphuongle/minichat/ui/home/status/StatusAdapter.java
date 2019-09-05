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


public class StatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private IStatus inter;
    private int positionClick;
    private int numberClickLike;
    private static final int INSERT_STATUS = 0;
    private static final int LIST_STATUS = 1;
    private static final int EMPTY = 2;
    private StatusViewHolder statusViewHolder;
    private ImageButton delete;
    private Activity activity;
    private StartStatusViewHolder startStatusViewHolder;

    public StatusAdapter(IStatus inter,Activity activity) {
        this.inter = inter;
        this.activity=activity;
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
                 startStatusViewHolder= (StartStatusViewHolder) holder;
                Glide.with(startStatusViewHolder.binding.ivAvatarByUser)
                        .load(CommonData.getInstance().getUserProfile().getAvatar())
                        .into(startStatusViewHolder.binding.ivAvatarByUser);
                startStatusViewHolder.binding.ivAvatarByUser.setOnClickListener(this);
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

                delete=statusViewHolder.binding.btnMoreStatus.findViewById(R.id.btn_more_status);
                delete.setOnClickListener(this);
                statusViewHolder.binding.btnLike.setOnClickListener(this);
                if (numberClickLike%2!=0){
                    statusViewHolder.binding.btnLike.setImageResource(R.drawable.btn_like);
                }
//                else {
//                    statusViewHolder.binding.btnLike.setImageResource(R.drawable.btn_after_like);
//                }
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
                statusViewHolder.binding.btnLike.setImageResource(R.drawable.btn_after_like);

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
            case R.id.iv_avatar_by_user:
                inter.goPorofile();
            case R.id.tv_content_insert:
                inter.goFragmentAddStatus();
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

        void goPorofile();

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
