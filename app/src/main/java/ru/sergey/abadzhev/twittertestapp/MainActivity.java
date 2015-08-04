package ru.sergey.abadzhev.twittertestapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends FragmentActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "MTqfV9T8LCfwavNUaxG84Nmv7";
    private static final String TWITTER_SECRET = "DEODVPUy309zgLmBLaySG4msRk93CKEs2h8OJJEMtGtp6bsHpA";

    LoginFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        fragment = new LoginFragment();


        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {

            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.attention))
                    .setMessage(getResources().getString(R.string.exit))
                    .setPositiveButton(getString(R.string.quit),
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    finish();
                                }
                            })
                    .setNegativeButton(
                            getString(android.R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {

                                }
                            })
                    .setIcon(R.mipmap.ic_launcher).show();




    }


}
