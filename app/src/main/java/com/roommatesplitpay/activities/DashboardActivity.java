package com.roommatesplitpay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roommatesplitpay.BaseActivity;
import com.roommatesplitpay.R;
import com.roommatesplitpay.User;
import com.roommatesplitpay.adapters.DashboardAdapter;
import com.roommatesplitpay.utils.RoomMateSplitPaySession;

public class DashboardActivity extends BaseActivity {

    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DatabaseReference mPostReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setIDs();
        setListeners();
       /* viewPager.setAdapter(new DashboardAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);*/

        RoomMateSplitPaySession session = new RoomMateSplitPaySession(DashboardActivity.this);
        mPostReference = FirebaseDatabase.getInstance().getReference().child("users").child(session.getUserID());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                Log.e("TAG",dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mPostReference.addValueEventListener(listener);
    }

    @Override
    public void setIDs() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void setListeners() {
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(DashboardActivity.this,SpendMoneyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
