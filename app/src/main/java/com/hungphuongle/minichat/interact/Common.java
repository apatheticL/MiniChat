package com.hungphuongle.minichat.interact;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Common {
    public static UserService getUserService(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .callTimeout(10, TimeUnit.MINUTES)
                        .connectTimeout(10, TimeUnit.MINUTES)
                        .readTimeout(10, TimeUnit.MINUTES)
                        .readTimeout(10, TimeUnit.MINUTES).build())
                .build();
        UserService userService =
                retrofit.create(UserService.class);
        return userService;
    }
    public static String getLinkImage(String path){
        return Constants.BASE_URL+"/getImage?fileName="+path;
    }

    public static void hideKeyBoard(Activity act){
        InputMethodManager input = (InputMethodManager)act.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = act.getCurrentFocus();
        if (view != null ){
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
