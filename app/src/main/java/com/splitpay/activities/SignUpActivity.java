package com.splitpay.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.splitpay.AppConstants;
import com.splitpay.BaseActivity;
import com.splitpay.R;
import com.splitpay.User;
import com.splitpay.utils.CommonUtils;
import com.splitpay.utils.SplitPaySession;

public class SignUpActivity extends BaseActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private EditText etEmail, etPassword, etPhone, etFullName;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mContext = this;
        setIDs();
        setListeners();
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
    }

    @Override
    public void setIDs() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etFullName = (EditText) findViewById(R.id.etFullName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }

    @Override
    public void setListeners() {
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                if (!validateForm()) {
                    return;
                }
                validatePhoneFromServer(etPhone.getText().toString());
                break;

        }
    }

    private void validatePhoneFromServer(String phoneNumber) {
        showProgressDialog();
        mFireStore.collection(AppConstants.USERS)
                .whereEqualTo(AppConstants.USER_PHONE_NUMBER, phoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //Phone number doesn't exists
                            if (task.getResult() == null || task.getResult().size() == 0) {
                                createAccount(etFullName.getText().toString(),
                                        etEmail.getText().toString(),
                                        etPassword.getText().toString(), etPhone.getText().toString());
                            } else {
                                hideProgressDialog();
                                CommonUtils.showToast(mContext, "Phone number is already taken, Try another.");
                            }
                        } else {
                            hideProgressDialog();
                            Log.d(TAG, "Error getting documents: ", task.getException());

                        }
                    }
                });
    }

    private void createAccount(final String fullName, String email, String password, final String phoneNumber) {
        Log.d(TAG, "createAccount:" + email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null)
                                writeNewUser(fullName, user.getUid(), user.getEmail(), phoneNumber);
                        } else {
                            Toast.makeText(SignUpActivity.this, task.getException() != null
                                            ? task.getException().getMessage() : getString(R.string.auth_failed),
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    private boolean validateForm() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String fullName = etFullName.getText().toString();
        if (TextUtils.isEmpty(fullName)) {
            CommonUtils.showToast(mContext, "Please enter valid Full Name");
            return false;
        } else if (TextUtils.isEmpty(email)) {
            CommonUtils.showToast(mContext, "Please enter valid email");
            return false;
        } else if (!CommonUtils.isValidEmail(email)) {
            CommonUtils.showToast(mContext, "Please enter valid email");
            return false;
        } else if (TextUtils.isEmpty(phone)) {
            CommonUtils.showToast(mContext, "Please enter valid Phone Number");
            return false;
        } else if (phone.length() < 10) {
            CommonUtils.showToast(mContext, "Please enter valid Phone Number");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            CommonUtils.showToast(mContext, "Please enter valid Password");
            return false;
        }
        return true;
    }

    private void writeNewUser(String fullName, final String userId, String email, final String phoneNumber) {
        User user = new User(userId, fullName, email, phoneNumber);
        mFireStore.collection(AppConstants.USERS)
                .document(userId)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        SplitPaySession session = SplitPaySession.getInstance(mContext);
                        session.setUserId(userId);
                        session.setUserLoggedIn(true);
                        session.setUserPhoneNumber(phoneNumber);
                        goToDashBoard();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void goToDashBoard() {
        Intent intent = new Intent(mContext, DashboardActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
