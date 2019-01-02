package com.mccarty.cloudcam.ui.main;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.ui.base.BaseActivity;
import com.mccarty.cloudcam.ui.camera.CameraActivity;
import com.mccarty.cloudcam.ui.login.LoginActivity;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Lazy;
import dagger.android.AndroidInjection;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainPresenterImpl presenter;

    @Inject
    Lazy<MainFragment> fragment;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainFragment mainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.main_fragment);

        if (mainFragment == null) {
            mainFragment = fragment.get();

            FragmentManager manager = getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.add(R.id.main_fragment, mainFragment);
            trans.commit();
        }
    }

    @Override
    public void onStart() {
        AWSMobileClient.getInstance().initialize(this, awsStartupResult -> {
            IdentityManager.getDefaultIdentityManager().getUserID(new IdentityHandler() {
                @Override
                public void onIdentityId(String identityId) {
                    Log.d(TAG, "***** INIT AWS");
                }

                @Override
                public void handleError(Exception exception) {
                    Log.d(TAG, "***** INIT AWS ERROR: ", exception);
                }
            });
        }).execute();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        menu.getItem(0).setVisible(!IdentityManager.getDefaultIdentityManager().isUserSignedIn());
        menu.getItem(1).setVisible(IdentityManager.getDefaultIdentityManager().isUserSignedIn());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_in:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
            case R.id.action_sign_out:
                IdentityManager.getDefaultIdentityManager().signOut();
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(IdentityManager.getDefaultIdentityManager().isUserSignedIn());
                return true;
                default:
                    // TODO: do something
            return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.fab)
    public void navigateToCameraFragmen() {
        startActivity(new Intent(MainActivity.this, CameraActivity.class));
    }
}