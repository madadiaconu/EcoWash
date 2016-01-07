package com.myapps.ecowash;

import android.app.Application;
import android.content.Context;

import com.myapps.ecowash.bl.ParseClient;

public class App extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ParseClient.init(context);
    }

    public static Context getContext(){
        return context;
    }
}
