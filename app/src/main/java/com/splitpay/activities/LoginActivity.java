package com.splitpay.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.splitpay.AppConstants;
import com.splitpay.BaseActivity;
import com.splitpay.R;
import com.splitpay.User;
import com.splitpay.utils.SplitPaySession;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText etEmail, etPassword;
    private TextView tvSignUp;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private Context mContext = LoginActivity.this;
    private FirebaseFirestore mFireStore;
    private SplitPaySession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFireStore = FirebaseFirestore.getInstance();
        setIDs();
        setListeners();
        mAuth = FirebaseAuth.getInstance();
        mSession =   SplitPaySession.getInstance(mContext);
    }


    @Override
    public void setIDs() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    @Override
    public void setListeners() {
        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                signIn(etEmail.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.tvSignUp:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                Log.e(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                                getUserInfo(user.getUid());
                            }else {
                                hideProgressDialog();
                            }
                        } else {
                            hideProgressDialog();
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getUserInfo(final String userID){
        mFireStore.collection(AppConstants.USERS)
                .document(userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        hideProgressDialog();
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                            User user = task.getResult().toObject(User.class);
                            mSession.setUserPhoneNumber(user.getPhoneNumber());
                            mSession.setUserId(userID);
                            mSession.setUserLoggedIn(true);
                            goToDashBoard();
                        }else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        Log.d(TAG, "get failed with "+ e.getMessage());
                    }
                });
    }

    private void goToDashBoard() {
        Intent intent = new Intent(mContext, DashboardActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = etEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Required.");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Required.");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }
}
