package com.b2gsoft.sailorsexpress.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.b2gsoft.sailorsexpress.model.User;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreference {

    private Context context;
    private SharedPreferences.Editor editor;
    SharedPreferences prefs;

    public SharedPreference(Context context) {
        this.context = context;
        editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    private final String MY_PREFS_NAME = "SailorsExpress";
    private final String USER = " gvigdidg";
    private final String LoginTime = "fgudfau66";


    public void saveUser(User user)
    {
        Gson gson = new Gson();
        String json = gson.toJson(user);

        editor.putString(USER, json);
        editor.apply();
    }

    public User getCurrentUser()
    {
        User user = new User();

        if(prefs.contains(USER)) {

            Gson gson = new Gson();
            String json = prefs.getString(USER, "");

            user = gson.fromJson(json, User.class);
        }

        return user;
    }

    public void clearUserData() {

        editor.remove(USER);
        editor.apply();
    }

    public void setLoginTime(String time) {

        editor.putString(LoginTime, time);
        editor.apply();
    }

    public String getLoginTime()
    {
        return prefs.getString(LoginTime, "");
    }
}
