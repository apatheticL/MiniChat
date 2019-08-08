package com.hungphuongle.minichat.interact;

import com.hungphuongle.minichat.model.UserProfile;

public class CommonData {
    private UserProfile userProfile;
    private static CommonData instance;
    private CommonData(){

    }
    public static CommonData getInstance(){
        if (instance == null){
            instance = new CommonData();
        }
        return instance;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
