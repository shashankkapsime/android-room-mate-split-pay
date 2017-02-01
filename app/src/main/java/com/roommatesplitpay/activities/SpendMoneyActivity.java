package com.roommatesplitpay.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roommatesplitpay.BaseActivity;
import com.roommatesplitpay.R;
import com.roommatesplitpay.utils.CommonUtils;
import com.roommatesplitpay.utils.RoomMateSplitPaySession;
import com.roommatesplitpay.utils.SpendMoney;

public class SpendMoneyActivity extends BaseActivity {

    private TextView tvSelectDate;
    private EditText etMoneyFor, etAmount;
    private RadioButton rbYes, rbNo;
    private Button btnSubmit;
    private Context mContext = SpendMoneyActivity.this;
    private DatabaseReference mDatabase;
    private RoomMateSplitPaySession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_money);
        setIDs();
        setListeners();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        session = new RoomMateSplitPaySession(SpendMoneyActivity.this);
        rbYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    Intent intent = new Intent(SpendMoneyActivity.this,RoomiesListActivity.class);
                    startActivityForResult(intent,1000);
                }
            }
        });
    }

    @Override
    public void setIDs() {
        tvSelectDate = (TextView) findViewById(R.id.tvSelectDate);
        etMoneyFor = (EditText) findViewById(R.id.etMoneyFor);
        etAmount = (EditText) findViewById(R.id.etAmount);
        rbYes = (RadioButton) findViewById(R.id.rbYes);
        rbNo = (RadioButton) findViewById(R.id.rbNo);
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
                    mDatabase.child("spendMoney").child(session.getUserID()).push().setValue(spendMoney);

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
