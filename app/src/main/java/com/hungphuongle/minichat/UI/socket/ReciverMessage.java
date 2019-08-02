package com.hungphuongle.minichat.UI.socket;


import com.hungphuongle.minichat.UI.model.request.MessageChatResponse;

public interface ReciverMessage {
    void recieve(MessageChatResponse response);
}
