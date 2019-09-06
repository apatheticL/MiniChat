package com.hungphuongle.minichat.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hungphuongle.minichat.model.UserProfile;

public class ShareUtil {
    public static void saveUserProfile(Context context, UserProfile userProfile) {
        SharedPreferences sh = context.getSharedPreferences("SHARE", Context.MODE_PRIVATE);
        sh.edit().putString("USER",
                new Gson().toJson(userProfile))
                .apply();
    }

    public static UserProfile getUserProfile(Context context) {
        String userString = context.getSharedPreferences("SHARE", Context.MODE_PRIVATE)
                .getString("USER", null);
        if (userString != null) {
            return new Gson().fromJson(userString, UserProfile.class);
        }
        return null;
    }

    public static void clearProfile(Context context) {
        context.getSharedPreferences("SHARE", Context.MODE_PRIVATE)
                .edit().putString("USER",
                null)
                .apply();
    }
}
