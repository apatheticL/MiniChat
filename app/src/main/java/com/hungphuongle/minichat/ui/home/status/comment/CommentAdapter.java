package com.hungphuongle.minichat.ui.home.status.comment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungphuongle.minichat.databinding.ItemCommentBinding;
import com.hungphuongle.minichat.model.request.CommentRequest;

import java.text.SimpleDateFormat;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private IComment intent;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public CommentAdapter(IComment intent) {
        this.intent = intent;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CommentHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        CommentRequest comment = intent.getItem(position);
        Glide.with(holder.binding.ivAvatarComment)
                .load(comment.getAvatarUser()).into(holder.binding.ivAvatarComment);
        holder.binding.tvFullName.setText(comment.getFullName());
        holder.binding.tvContent.setText(comment.getContent());
        String date = dateFormat.format(comment.getCreatedTime());
        holder.binding.createdTime.setText(date+"");
    }

    @Override
    public int getItemCount() {
        return intent.getCount();
    }

    public interface IComment {
        int getCount();
        CommentRequest getItem(int position);
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {
        private ItemCommentBinding binding;
        public CommentHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
