package com.hungphuongle.minichat.UI.interact;

import com.hungphuongle.minichat.UI.model.UserProfile;

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
