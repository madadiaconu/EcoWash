package com.myapps.ecowash.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;

import com.myapps.ecowash.bl.ParseClient;
import com.myapps.ecowash.util.LocalStorageHandler;

public class BaseActivity extends Activity{

    private ProgressDialog progressDialog;

    public void goToActivity(Class activity, boolean clearStack){
        Intent intent = new Intent(this,activity);
        if (clearStack){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
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

    public void logout(View view) {
        ParseClient.getInstance().logout();
        LocalStorageHandler.deleteUser();
        goToActivity(LoginActivity.class,true);
    }
}
