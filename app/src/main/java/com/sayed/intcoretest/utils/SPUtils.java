package com.sayed.intcoretest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sayed.intcoretest.R;
import com.sayed.intcoretest.model.User;

public class SPUtils {

    //dec data
    private final SharedPreferences sp;

    //keys
    private static final String SP_USER_DATA="SPUtils.UserData";


    //create constructor
    public SPUtils(Context context) {
        this.sp = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    //set and get user
    public void setUser(User user){
        String data = new Gson().toJson(user);
        sp.edit().putString(SP_USER_DATA, data).apply();
    }
    public User getUser(){
        String data = sp.getString(SP_USER_DATA, null);
        if (data==null) return null;
        return new Gson().fromJson(data, new TypeToken<User>() {
        }.getType());
    }

    //clear all data
    public void clear() {
        sp.edit().remove(SP_USER_DATA)
                .apply();
    }
}
