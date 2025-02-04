package com.example.qr_ticket.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.qr_ticket.ui.login.LoginActivity;

import java.util.HashMap;

public class UserSessionManager {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "UserSessionManager";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String KEY_USERID = "tblUserID";

    public static final String KEY_ISADMIN = "0";

    public static final String KEY_FIREBASETOKEN = "FIREBASETOKEN";

    private static UserSessionManager jInstance;

    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public static synchronized UserSessionManager getInstance(Context context) {
        if (jInstance != null) {
            return jInstance;
        } else {
            jInstance = new UserSessionManager(context);
            return jInstance;
        }
    }
    //Create login session
    public void createUserLoginSession(String name, String email,String tblUserID, String IsAdmin, String firebaseToken){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        editor.putString(KEY_USERID, tblUserID);
        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_ISADMIN, IsAdmin);

        editor.putString(KEY_FIREBASETOKEN,firebaseToken );

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_USERID, pref.getString(KEY_USERID, null));

        user.put(KEY_ISADMIN, pref.getString(KEY_ISADMIN, null));

        user.put(KEY_FIREBASETOKEN, pref.getString(KEY_FIREBASETOKEN, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
