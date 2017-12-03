package com.splitpay.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.splitpay.BaseActivity;
import com.splitpay.R;
import com.splitpay.utils.CommonUtils;
import com.splitpay.utils.SplitPaySession;
import com.splitpay.utils.SpendMoney;

public class SpendMoneyActivity extends BaseActivity {

    private TextView tvSelectDate;
    private EditText etMoneyFor, etAmount;
    private Button btnSubmit;
    private Context mContext = SpendMoneyActivity.this;
    private SplitPaySession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_money);
        setIDs();
        setListeners();
        session = SplitPaySession.getInstance(this);
    }

    @Override
    public void setIDs() {
        tvSelectDate = (TextView) findViewById(R.id.tvSelectDate);
        etMoneyFor = (EditText) findViewById(R.id.etMoneyFor);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    @Override
    public void setListeners() {
        btnSubmit.setOnClickListener(this);
        tvSelectDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                if (isValid()) {
                    SpendMoney spendMoney = new SpendMoney(etMoneyFor.getText().toString(),etAmount.getText().toString(),true);
                    CommonUtils.showToast(mContext, getString(R.string.work_in_progress));
                }
                break;
            case R.id.tvSelectDate:

                break;
        }
    }

    private boolean isValid() {
        if (etMoneyFor.getText().toString().trim().length() == 0) {
            CommonUtils.showToast(mContext, getString(R.string.plz_enter_for_what_you_are_spending));
            etMoneyFor.requestFocus();
            return false;
        } else if (etAmount.getText().toString().trim().length() == 0) {
            CommonUtils.showToast(mContext, getString(R.string.plz_enter_amount));
            etAmount.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}
