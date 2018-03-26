package com.mccarty.cloudcam.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.amazonaws.auth.AWSIdentityProvider;
import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
//import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
//import com.amazonaws.mobile.auth.ui.SignInUI;
//import com.amazonaws.mobile.client.AWSMobileClient;
//import com.amazonaws.mobile.client.AWSStartupHandler;
//import com.amazonaws.mobile.client.AWSStartupResult;
import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.awsutils.AWSProvider;

public class AuthenticatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_authenticator);

      /*  final IdentityManager identityManager = AWSProvider.getInstance().getIdentityManager();
        identityManager.setUpToAuthenticate(this, new DefaultSignInResultHandler() {
            @Override
            public void onSuccess(Activity activity, IdentityProvider identityProvider) {
                Toast.makeText(AuthenticatorActivity.this,
                        String.format("Logged in as %s", identityManager.getCachedUserID()),
                        Toast.LENGTH_LONG).show();
                // Go to the main activity
                final Intent intent = new Intent(activity, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public boolean onCancel(Activity activity) {
                return false;
            }
        });

        // Add a call to initialize AWSMobileClient
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                AuthUIConfiguration config =
                        new AuthUIConfiguration.Builder()
                                .userPools(true)
                                .canCancel(true)
                                .build();
                SignInUI signin = (SignInUI)
                        AWSMobileClient.getInstance().getClient(AuthenticatorActivity.this,
                                SignInUI.class);
                signin.login(AuthenticatorActivity.this, MainActivity.class)
                        .authUIConfiguration(config).execute();
            }
        }).execute();*/

        // Add a call to initialize AWSMobileClient
//        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
//            @Override
//            public void onComplete(AWSStartupResult awsStartupResult) {
//                SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(AuthenticatorActivity.this, SignInUI.class);
//                signin.login(AuthenticatorActivity.this, MainActivity.class).execute();
//            }
//        }).execute();
    }
}

