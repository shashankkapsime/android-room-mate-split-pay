package com.splitpay.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.splitpay.AppConstants;
import com.splitpay.BaseFragment;
import com.splitpay.R;
import com.splitpay.RecyclerItemSelectedListener;
import com.splitpay.User;
import com.splitpay.adapters.FriendListAdapter;
import com.splitpay.utils.SplitPaySession;

import java.util.ArrayList;
import java.util.List;

public class FragmentFriendList extends BaseFragment implements RecyclerItemSelectedListener {

    private RecyclerView rvRoomies;
    private FriendListAdapter mAdapter;
    private List<User> mUserList = new ArrayList<>();
    private SplitPaySession session;
    private Context mContext;
    private FirebaseFirestore mFireStore;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = SplitPaySession.getInstance(mContext);
        mFireStore = ((DashboardActivity) getActivity()).getFireStore();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_friend_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRoomies.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new FriendListAdapter(mContext, mUserList, this);
        rvRoomies.setAdapter(mAdapter);

        if (mFireStore != null) {
            mFireStore.collection(AppConstants.USER_FRIEND_LIST)
                    .document(session.getUserID())
                    .collection(AppConstants.USER_FRIENDS)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    mUserList.add(user);
                                }
                                mAdapter.notifyDataSetChanged();
                                Log.d(TAG, "size >>> " + mUserList.size());
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }

    }


    @Override
    public void setIDs(View view) {
        rvRoomies = (RecyclerView) view.findViewById(R.id.rvRoomies);
    }

    @Override
    public void onItemClick(Object o, int position) {
        User user = (User) o;
        if (user != null) {
            //TODO handle click events
        }
    }
}
