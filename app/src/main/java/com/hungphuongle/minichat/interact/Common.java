package com.hungphuongle.minichat.interact;

import com.hungphuongle.minichat.ui.Constants;

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
    public static String getLinkImage(String path){
        return Constants.BASE_URL+"/getImage?fileName="+path;
    }
}
