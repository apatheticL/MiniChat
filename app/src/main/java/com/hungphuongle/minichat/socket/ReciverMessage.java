package com.hungphuongle.minichat.socket;


import com.hungphuongle.minichat.model.request.MessageChatResponse;

public interface ReciverMessage {
    void recieve(MessageChatResponse response);
}
