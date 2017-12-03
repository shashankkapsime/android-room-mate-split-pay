package com.splitpay.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.splitpay.BaseActivity;
import com.splitpay.R;
import com.splitpay.User;
import com.splitpay.adapters.RoomiesListAdapter;
import com.splitpay.utils.SplitPaySession;

import java.util.ArrayList;
import java.util.List;

public class RoomiesListActivity extends BaseActivity {

    private RadioButton rbAll,rbIndividually;
    private RecyclerView rvRoomies;
    private RoomiesListAdapter adapter;
    private List<User> userList = new ArrayList<>();
   // private DatabaseReference mDatabase;
    private SplitPaySession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomies_list);
        setIDs();
        setListeners();
        rvRoomies.setLayoutManager(new LinearLayoutManager(RoomiesListActivity.this));
        adapter = new RoomiesListAdapter(RoomiesListActivity.this,userList);
        rvRoomies.setAdapter(adapter);
        session = SplitPaySession.getInstance(this);
     /*   mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                   userList.add(postSnapshot.getValue(User.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);*/

        rbIndividually.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    rvRoomies.setVisibility(View.VISIBLE);
                }else {
                    rvRoomies.setVisibility(View.GONE);
                }
            }
        });

        rbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    rvRoomies.setVisibility(View.GONE);
                }else {
                    rvRoomies.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void setIDs() {
        rvRoomies = (RecyclerView)findViewById(R.id.rvRoomies);
        rbIndividually = (RadioButton)findViewById(R.id.rbIndividually);
        rbAll = (RadioButton)findViewById(R.id.rbAll);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }
}
