package com.myapps.ecowash.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myapps.ecowash.R;
import com.myapps.ecowash.bl.ParseClient;
import com.myapps.ecowash.model.User;
import com.myapps.ecowash.util.DialogManager;
import com.myapps.ecowash.util.LocalStorageHandler;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends BaseActivity{

    private EditText usernameEditText;
    private EditText passEditText;
    private TextView emptyFieldsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            User user = LocalStorageHandler.retrieveUser();
            login(user.getUsername(),user.getPassword());
        } catch (Exception e) {
            usernameEditText = (EditText)findViewById(R.id.username);
            passEditText = (EditText) findViewById(R.id.pass);
            emptyFieldsMessage = (TextView) findViewById(R.id.emptyFieldsMessage);

            findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = usernameEditText.getText().toString();
                    String pass = passEditText.getText().toString();
                    if (username.equals("")||pass.equals("")){
                        emptyFieldsMessage.setVisibility(View.VISIBLE);
                    } else {
                        emptyFieldsMessage.setVisibility(View.INVISIBLE);
                        LocalStorageHandler.saveUser(username,pass);
                        login(username,pass);
                    }
                }
            });
        }
    }

    private void login(String username, String pass){
        showProgress("Logging in...");
        ParseClient.getInstance().login(username, pass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                hideProgress();
                if (e == null) {
                    goToActivity(MainActivity.class,true);
                } else {
                    if (!isFinishing()) {
                        DialogManager.createSimpleDialog(LoginActivity.this, R.string.login_error).show();
                    }
                }
            }
        });
    }
}
