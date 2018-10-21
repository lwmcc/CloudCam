package com.mccarty.cloudcam.ui.login;

import android.app.Activity;
import android.os.Bundle;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.mccarty.cloudcam.R;

import com.mccarty.cloudcam.ui.main.MainActivity;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AWSMobileClient.getInstance().initialize(this, awsStartupResult -> {
            SignInUI signInUI = (SignInUI) AWSMobileClient.getInstance().getClient(
                LoginActivity.this, SignInUI.class);
            signInUI.login(LoginActivity.this, MainActivity.class).execute();
        }).execute();
    }
}
