package com.hungphuongle.minichat.socket;

import android.util.Log;
import com.google.gson.Gson;
import com.hungphuongle.minichat.ui.Constants;
import com.hungphuongle.minichat.interact.CommonData;
import com.hungphuongle.minichat.model.request.MessageChatResponse;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {
    private static final String TAG="SocketManager";
    private Socket socket;
    private static SocketManager instance;
    private List<ReciverMessage> reciverMessages = new ArrayList<>();



    public static SocketManager getInstance(){
        if (instance==null){
            instance= new SocketManager();
        }
        return instance;
    }
    private SocketManager(){

    }
    public void disconnect(){
        if (socket!=null){
            socket.disconnect();
            socket= null;
        }
    }
    public void connect(){
        try {
            socket = IO.socket(Constants.URL_SOCKET);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "EVENT_CONNECT: " + args);
                    socket.emit("connected",
                            CommonData.getInstance()
                                    .getUserProfile()
                                    .getId() + "");
                }
            });

            socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "EVENT_DISCONNECT: " + args);
                }
            });
            socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "EVENT_CONNECT_ERROR: " + args);
                }
            });
            socket.on("message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "send message " + args);
                }
            });
            socket.on("insert", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "inserted data " + args);
                }
            });

            socket.on("message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    reciveMessage((String) args[0]);
                }
            });
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void reciveMessage(String s){
        MessageChatResponse message = new Gson().fromJson(s, MessageChatResponse.class);
        for (ReciverMessage reciverMessage : reciverMessages) {
            reciverMessage.recieve(message);
        }
    }


    public void sendMessage(String toJson) {
        if ( socket != null){
            socket.emit("message", toJson);
        }
    }

    public void register(ReciverMessage reciverMessage){
        reciverMessages.add(reciverMessage);
    }

    public void unregister(ReciverMessage reciverMessage){
        reciverMessages.remove(reciverMessage);
    }
}
