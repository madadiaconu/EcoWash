package com.myapps.ecowash.ui.activities;

import android.app.Activity;
import android.content.Intent;

public class BaseActivity extends Activity{

    protected void goToActivity(Class activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }
}
