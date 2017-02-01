package com.roommatesplitpay;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roommatesplitpay.activities.DashboardActivity;
import com.roommatesplitpay.activities.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private Context mContext = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
            }
        },1500);
    }
}
