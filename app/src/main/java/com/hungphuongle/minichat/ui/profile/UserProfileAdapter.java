package com.hungphuongle.minichat.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.databinding.ItemInsertStatusBinding;
import com.hungphuongle.minichat.databinding.ItemProfileBinding;
import com.hungphuongle.minichat.databinding.ItemStatusBinding;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.model.UserProfile;
import com.hungphuongle.minichat.model.request.StatusResponse;

public class UserProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private final int USER_PROFILE = 0;
    private final int INSERT_STATUS = 1;
    private final int LIST_STATUS = 2;
    private IUserProfile inten;
    private int positionClick;
    private int numberClickLike;
    private StatusViewHolder statusViewHolder;

    public UserProfileAdapter(IUserProfile inten) {
        this.inten = inten;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case USER_PROFILE:

                ItemProfileBinding binding1 = ItemProfileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new UserProfileHolder(binding1);
            case INSERT_STATUS:
                ItemInsertStatusBinding binding2 = ItemInsertStatusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new StartStatusViewHolder(binding2);
            case LIST_STATUS:
                ItemStatusBinding binding = ItemStatusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new StatusViewHolder(binding);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case USER_PROFILE:
                UserProfileHolder userProfileHolder = (UserProfileHolder) holder;
                UserProfile userProfile = CommonData.getInstance().getUserProfile();
                userProfileHolder.binding.tvFullnameTool.setText(userProfile.getFullname());
                if (userProfile.getAvatar() == null) {
                    Glide.with(userProfileHolder.binding.imAvatar)
                            .load(R.drawable.user)
                            .into(userProfileHolder.binding.imAvatar);
                }
                Glide.with(userProfileHolder.binding.imAvatar)
                        .load(userProfile.getAvatar())
                        .error(R.drawable.user)
                        .into(userProfileHolder.binding.imAvatar);
                userProfileHolder.binding.fullNameUser.setText(userProfile.getFullname());
                userProfileHolder.binding.tvBirthday.setText(userProfile.getBirthday());
                userProfileHolder.binding.tvEmail.setText(userProfile.getEmail());
                userProfileHolder.binding.tvSex.setText(userProfile.getCopulation());
                userProfileHolder.binding.tvPhone.setText(userProfile.getPhoneNumber());
                break;
            case INSERT_STATUS:
                StartStatusViewHolder startStatusViewHolder = (StartStatusViewHolder) holder;
                Glide.with(startStatusViewHolder.binding.ivAvatarByUser)
                        .load(CommonData.getInstance().getUserProfile().getAvatar())
                        .into(startStatusViewHolder.binding.ivAvatarByUser);
                startStatusViewHolder.binding.ivImage.setOnClickListener(this);
                startStatusViewHolder.binding.tvContentInsert.setOnClickListener(this);
                break;
            case LIST_STATUS:
                statusViewHolder = (StatusViewHolder) holder;
                StatusResponse srarus = inten.getItem(position);
                Glide.with(statusViewHolder.binding.ivAvatarStatus)
                        .load(CommonData.getInstance().getUserProfile().getAvatar())
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
                if (numberClickLike % 2 != 0) {
                    statusViewHolder.binding.btnLike.setImageResource(R.drawable.btn_like);
                } else {
                    statusViewHolder.binding.btnLike.setImageResource(R.drawable.btn_after_like);
                }
                statusViewHolder.binding.btnComment.setOnClickListener(this);
                statusViewHolder.binding.btnShare.setOnClickListener(this);
                positionClick = holder.getAdapterPosition();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return inten.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        switch ((position)) {
            case 0:
                return USER_PROFILE;
            case 1:
                return INSERT_STATUS;
            default:
                return LIST_STATUS;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_like:
                numberClickLike++;
                inten.setNumberLike(positionClick, numberClickLike);
                break;
            case R.id.btn_comment:
                inten.dataComment(positionClick);
                break;
            case R.id.btn_share:
                inten.setNumberShare(positionClick);
                break;
            case R.id.iv_image:
                inten.getImage();
                break;
            case R.id.tv_content_insert:
                inten.goFragmentAddStatus();
            case R.id.iv_avatar_by_user:
                inten.goPorofile();
        }
    }

    public interface IUserProfile {
        int getCount();
        StatusResponse getItem(int position);
        void setNumberLike(int posotion, int numberClick);

        void dataComment(int position);

        void setNumberShare(int position);

        void getImage();

        void goPorofile();

        void goFragmentAddStatus();
    }

    public static class UserProfileHolder extends RecyclerView.ViewHolder {
        private ItemProfileBinding binding;

        public UserProfileHolder(@NonNull ItemProfileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
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
