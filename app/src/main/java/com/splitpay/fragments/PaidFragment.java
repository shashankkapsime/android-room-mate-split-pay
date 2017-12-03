package com.splitpay.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splitpay.BaseFragment;
import com.splitpay.R;

/**
 * Created by shashank on 29-01-2017.
 */

public class PaidFragment extends BaseFragment {

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_paid, null);
        return view;
    }

    @Override
    public void setIDs(View view) {

    }

}
