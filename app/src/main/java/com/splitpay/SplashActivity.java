package com.splitpay;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.splitpay.activities.DashboardActivity;
import com.splitpay.activities.LoginActivity;
import com.splitpay.utils.SplitPaySession;

public class SplashActivity extends AppCompatActivity {

    private Context mContext = SplashActivity.this;
    private Class classToGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        classToGo = LoginActivity.class;
        SplitPaySession session = SplitPaySession.getInstance(this);
        if (session.getUserLoggedInStatus()
                && session.getUserID() != null) {
            classToGo = DashboardActivity.class;
        } else {
            classToGo = LoginActivity.class;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, classToGo));
                finish();
            }
        }, 1500);
    }
}
