package com.hungphuongle.minichat.UI.socket;

import android.util.Log;

import com.hungphuongle.minichat.UI.UI.Constants;
import com.hungphuongle.minichat.UI.interact.CommonData;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {
    private static final String TAG="SocketManager";
    private Socket socket;
    private static SocketManager instance;
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
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
