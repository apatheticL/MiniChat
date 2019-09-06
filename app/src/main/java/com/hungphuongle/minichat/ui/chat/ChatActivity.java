package com.hungphuongle.minichat.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.interact.Common;
import com.hungphuongle.minichat.model.UserProfile;
import com.hungphuongle.minichat.interact.UserService;
import com.hungphuongle.minichat.model.request.BaseResponse;
import com.hungphuongle.minichat.ui.home.messenger.FriendResponse;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.model.request.MessageChatResponse;
import com.hungphuongle.minichat.socket.ReciverMessage;
import com.hungphuongle.minichat.socket.SocketManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.IChat, View.OnClickListener, ReciverMessage, PopupMenu.OnMenuItemClickListener {
    private RecyclerView rc;
    private EditText edtSend;
    private ChatAdapter adapter;
    private List<MessageChatResponse> messages;
    private FriendResponse friendResponse;
    private UserProfile userProfile;
    private AppCompatImageButton btnMore;
    private UserService userService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userService = Common.getUserService();
        rc = findViewById(R.id.rc);
        edtSend = findViewById(R.id.edt_send);
        rc.setLayoutManager(new LinearLayoutManager(this));
        messages = new ArrayList<>();
        adapter = new ChatAdapter(this);
        rc.setAdapter(adapter);
        friendResponse = (FriendResponse) getIntent().getSerializableExtra("FRIEND");
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        init();
        btnMore=findViewById(R.id.btn_more);
        btnMore.setOnClickListener(this);
        SocketManager.getInstance()
                .register(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 100:
                if (resultCode ==  RESULT_OK){
                    String path = data.getStringExtra("PATH");
                    sendImage(path);
                }
                break;
            default:
                break;
        }
    }

    private void init() {
        if (friendResponse.getFriendAvatar() != null) {
            Glide.with(this)
                    .load(friendResponse.getFriendAvatar())
                    .error(R.drawable.avatar)
                    .placeholder(R.drawable.avatar)
                    .into((ImageView) findViewById(R.id.iv_avatar));
        }
        ((TextView) findViewById(R.id.tv_name)).setText(friendResponse.getFriendName());

        userService.getHistoryChat(CommonData.getInstance().getUserProfile().getId(),
                friendResponse.getFriendId())
                .enqueue(new Callback<BaseResponse<List<MessageChatResponse>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<MessageChatResponse>>> call, Response<BaseResponse<List<MessageChatResponse>>> response) {
                        messages = response.body().getData();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<MessageChatResponse>>> call, Throwable t) {

                    }
                });

    }

    @Override
    public int getCount() {
        if (messages == null) {
            return 0;
        }
        return messages.size();
    }

    @Override
    public MessageChatResponse getData(int position) {
        return messages.get(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                sendMessage();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_more:
                PopupMenu popup = new PopupMenu(this,btnMore);
                popup.inflate(R.menu.menu_chat);
                popup.setOnMenuItemClickListener(this);
                popup.show();
                break;
            default:
                break;
        }
    }

    private void sendMessage() {
        MessageChatResponse message = new MessageChatResponse();
        message.setReceiverId(friendResponse.getFriendId());
        message.setSenderId(CommonData.getInstance().getUserProfile().getId());
        message.setContent(edtSend.getText().toString());
        messages.add(message);
        adapter.notifyItemInserted(messages.size() - 1);
        rc.scrollToPosition(messages.size());

        SocketManager.getInstance().sendMessage(new Gson().toJson(message));
        edtSend.setText("");
    }
    private void sendImage(String path) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                );
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        userService.upload(body)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println("image: " + response.body());
                        MessageChatResponse mes = new MessageChatResponse();
                        mes.setReceiverId(friendResponse.getFriendId());
                        mes.setSenderId(CommonData.getInstance().getUserProfile().getId());
                        mes.setType(MessageChatResponse.TYPE_IMG);
                        mes.setContent(response.body());
                        messages.add(mes);
                        adapter.notifyItemInserted(messages.size()-1);
                        rc.smoothScrollToPosition(messages.size()-1);

                        SocketManager.getInstance().sendMessage(
                                new Gson().toJson(mes)
                        );
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    @Override
    public void recieve(final MessageChatResponse response) {
        if (response.getSenderId() != friendResponse.getFriendId()) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int send = response.getReceiverId();
                response.setReceiverId(CommonData.getInstance().getUserProfile().getId());
                response.setSenderId(friendResponse.getFriendId());
                messages.add(response);
                adapter.notifyItemInserted(messages.size() - 1);
                rc.scrollToPosition(messages.size());
            }
        });

    }



    @Override
    protected void onDestroy() {
        SocketManager.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}
