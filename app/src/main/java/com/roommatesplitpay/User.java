package com.roommatesplitpay;

/**
 * Created by shashank on 16-01-2017.
 */
public class User {
    public String username;
    public String email;
    public boolean isSelected;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
