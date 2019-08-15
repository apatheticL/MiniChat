package com.hungphuongle.minichat.ui.home.status.comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.databinding.FragmentCommentBinding;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.interact.UserSevice;
import com.hungphuongle.minichat.model.Comment;
import com.hungphuongle.minichat.model.request.BaseResponse;
import com.hungphuongle.minichat.model.request.CommentRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment implements CommentAdapter.IComment, View.OnClickListener {
    private RecyclerView rc;
    private CommentAdapter adapter;
    private FragmentCommentBinding binding;
    private UserSevice userSevice;
    private int id;
    private final int PICK_IMAGE = 1;
    private List<CommentRequest> commentRequests;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCommentBinding.inflate(inflater, container, false);
        userSevice  = Common.getUserService();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rcComment.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommentAdapter(this);
        binding.rcComment.setAdapter(adapter);
        insert();
        initService();
    }

    private void insert() {
        binding.ivImage.setOnClickListener(this);
        binding.btnSendComment.setOnClickListener(this);
    }

    private void initService() {

        userSevice.getAllCommentByStatus(id).enqueue(new Callback<List<CommentRequest>>() {
            @Override
            public void onResponse(Call<List<CommentRequest>> call, Response<List<CommentRequest>> response) {
                commentRequests = response.body();
                adapter.notifyDataSetChanged();
                initService();
            }

            @Override
            public void onFailure(Call<List<CommentRequest>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void sendIdStatus(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        if (commentRequests==null){
            return 0;
        }
        return commentRequests.size();
    }

    @Override
    public CommentRequest getItem(int position) {
        return commentRequests.get(position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                break;
            case R.id.btn_send_comment:
                CommentRequest request  = getDataComment();
                userSevice.insertComment(request).enqueue(new Callback<BaseResponse<Comment>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Comment>> call, Response<BaseResponse<Comment>> response) {
                        Toast.makeText(getContext(),response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        binding.edContentComment.setText("");
                        initService();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Comment>> call, Throwable t) {
                        binding.edContentComment.setText("");
                    }
                });
                break;
        }
    }

    private CommentRequest getDataComment() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setContent(binding.edContentComment.getText().toString());
        commentRequest.setStatusId(id);
        commentRequest.setUserId(CommonData.getInstance().getUserProfile().getId());
        return commentRequest;
    }
}
