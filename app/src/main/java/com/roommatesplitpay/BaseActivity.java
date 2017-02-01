package com.roommatesplitpay;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by shashank on 16-01-2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public ProgressDialog mProgressDialog;

    public abstract void setIDs();
    public abstract void setListeners();

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
