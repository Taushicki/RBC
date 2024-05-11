package com.example.rbc.preference;

import android.content.Context;
import android.content.SharedPreferences;
import org.example.client.dto.UserDTO;

public class UserDataManager {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static final String PREFS_NAME = "Prefs";
    private static final String LOGIN_ACTIVITY_STARTED = "loginActivityStarted";
    private static final String USER_ID = "userId";
    private static final String USER_LOGIN = "userLogin";
    private static final String USER_EMAIL = "userEmail";

    public UserDataManager(Context context){
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void firstLoginSetup(UserDTO user){
        editor.putBoolean(LOGIN_ACTIVITY_STARTED, true);
        editor.putString(USER_ID, String.valueOf(user.getId()));
        editor.putString(USER_LOGIN, user.getLogin());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.apply();
    }

    public static boolean getLoginStatus(){
        return preferences.getBoolean(LOGIN_ACTIVITY_STARTED, false);
    }
}
