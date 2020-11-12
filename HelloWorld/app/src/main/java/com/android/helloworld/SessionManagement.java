package com.android.helloworld;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private  final String SESSION_KEY = "USER_SESSION";
    public SessionManagement(Context context){
        String SHARED_PREF_NAME = "SESSION";
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user){
        int id = user.getID();
        editor.putInt(SESSION_KEY,id).commit();
    }
    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY,-1);
    }
    public void removeSesion(){
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
