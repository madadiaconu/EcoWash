package com.myapps.ecowash.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.myapps.ecowash.App;
import com.myapps.ecowash.model.User;

public class LocalStorageHandler {

    private static final String USER = "user";
    private static final String PASS = "pass";
    private static final String MY_PREFS = "myPrefs";

    public static void saveUser(String user, String pass){
        SharedPreferences sharedpreferences = App.getContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USER, user);
        editor.putString(PASS,pass);
        editor.commit();
    }

    public static User retrieveUser() throws Exception{
        SharedPreferences prefs = App.getContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        String user = prefs.getString(USER, null);
        String pass = prefs.getString(PASS,null);
        if ((user == null) || (pass == null)){
            throw new Exception("User is not saved locally");
        }
        return new User(user,pass);
    }

    public static void deleteUser(){
        SharedPreferences prefs = App.getContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

}
