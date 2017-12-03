package com.splitpay.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.splitpay.AppConstants;
import com.splitpay.BaseFragment;
import com.splitpay.R;
import com.splitpay.User;
import com.splitpay.activities.DashboardActivity;
import com.splitpay.utils.CommonUtils;
import com.splitpay.utils.SplitPaySession;

/**
 * Created by Shashank on 03-12-2017.
 */

public class AddMemberFragment extends BaseFragment {

    private Context mContext;
    private EditText etPhone;
    private Button btnSearch;
    private FirebaseFirestore mFireStore;

    public static AddMemberFragment newInstance() {

        Bundle args = new Bundle();

        AddMemberFragment fragment = new AddMemberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFireStore = ((DashboardActivity)getActivity()).getFireStore();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_add_member, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOperation(etPhone.getText().toString());
            }
        });


    }

    @Override
    public void setIDs(View view) {
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        etPhone = (EditText) view.findViewById(R.id.etPhone);
    }

    private void doOperation(String phoneNumber) {
        if (mFireStore == null)
            return;
        if (phoneNumber.trim().length() < 10) {
            CommonUtils.showToast(mContext, "Please enter valid Phone number");
        } else {
            showProgressDialog();
            mFireStore.collection(AppConstants.USERS)
                    .whereEqualTo(AppConstants.USER_PHONE_NUMBER, phoneNumber)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            hideProgressDialog();
                            if (task.isSuccessful()) {
                                if (task.getResult() != null && task.getResult().size() > 0) {
                                    if (task.getResult().getDocuments().size() > 0) {
                                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                        User user = document.toObject(User.class);
                                        showDialog(user);
                                    }
                                } else {
                                    CommonUtils.showToast(mContext, "This Phone number doesn't exists");
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    private void showDialog(final User user) {
        new AlertDialog.Builder(mContext)
                .setMessage("This Phone number belongs to " + user.getFullName() + ". Do you want to add this user as your member?")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        addMemberToGroup(user);
                    }
                })
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void addMemberToGroup(User user) {
        if (mFireStore == null)
            return;
        showProgressDialog();
        mFireStore.collection(AppConstants.USER_FRIEND_LIST)
                .document(SplitPaySession.getInstance(mContext).getUserID())
                .collection(AppConstants.USER_FRIENDS)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        hideProgressDialog();
                        CommonUtils.showToast(mContext, "Member added successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        CommonUtils.showToast(mContext, "Member not added : " + e.getMessage());
                    }
                });
    }
}
