package com.cubicl.buzzclicker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lingyi on 2/26/16.
 */
public class UserLocalStore {
    private User currUser;
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;
    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }
    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("FirstName", user.getFirstName());
        spEditor.putString("LastName", user.getLastName());
        spEditor.putString("username", user.getUsername());
        spEditor.putString("password", user.getPassword());
        spEditor.commit();
    }

    public User getLoggedInUSer() {
        String FirstName = userLocalDatabase.getString("FirstName", "");
        String LastName = userLocalDatabase.getString("LastName", "");
        String username = userLocalDatabase.getString("username","");
        String password = userLocalDatabase.getString("password","");
        String email = userLocalDatabase.getString("email", "");
        String major = userLocalDatabase.getString("major", "");
        User storeUser = new User(username, password, FirstName, LastName);
        return storeUser;
    }
    public void setUserLoggedIn(boolean loggedin) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedin);
        spEditor.commit();
    }

    public boolean getUserLoggedIn() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == true) {
            return true;
        }
        return false;
    }
    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
