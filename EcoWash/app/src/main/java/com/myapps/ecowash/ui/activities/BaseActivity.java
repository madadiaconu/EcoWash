package com.myapps.ecowash.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class BaseActivity extends Activity{

    private ProgressDialog progressDialog;

    public void goToActivity(Class activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }

    public void showProgress(String message){
        if (!isFinishing()) {
            progressDialog = ProgressDialog.show(BaseActivity.this, "Please wait.", message, true,false);
        }
    }

    public void hideProgress(){
        progressDialog.dismiss();
    }
}
