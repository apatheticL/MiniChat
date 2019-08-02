package com.hungphuongle.minichat.UI.interact;

import com.hungphuongle.minichat.UI.UI.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Common {
    public static UserSevice getUserService(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserSevice userService =
                retrofit.create(UserSevice.class);
        return userService;
    }
}
