package com.example.tripper.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    //Session names
    public static final String SESSION_USERSESSION="userLoginSession";
    public static final String SESSION_REMEMBERME="rememberMe";
    public static final String SESSION_LOCATION="Munger";

    //User Session variables
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USERID="userID";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE = "date";
    public static final String KEY_GENDER = "gender";

    //Remember Me Variables
    public static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSSIONPHONENUMBER = "phoneNumber";
    public static final String KEY_SESSIONPASSWORD = "password";

    public SessionManager(Context _context,String  sessionName) {
        context = _context;
        userSession = _context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String userId,String fullName, String username, String email, String password, String age, String gender, String phoneNo) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_USERID,userId);
        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_DATE, age);
        editor.putString(KEY_PHONENUMBER, phoneNo);

        editor.commit();
    }

    public void LocationSession(String location){
        editor.putString(SESSION_LOCATION,location);
    }
    public boolean getLocationSession(){
        if(SESSION_LOCATION.equals("noLocation")){
            return false;
        }else {
            return true;
        }
    }

    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_USERID,userSession.getString(KEY_USERID,null));
        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));
        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER, null));
        return userData;
    }

    public boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }


    public void logoutUserFromSession() {
        editor.remove(IS_LOGIN);
        editor.commit();
    }


    //Remember Me Session
    public void createRememberMeSession(String phoneNo, String password) {

        editor.putBoolean(IS_REMEMBERME, true);

        editor.putString(KEY_SESSSIONPHONENUMBER, phoneNo);
        editor.putString(KEY_SESSIONPASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();
        userData.put(KEY_SESSSIONPHONENUMBER, userSession.getString(KEY_SESSSIONPHONENUMBER, null));
        userData.put(KEY_SESSIONPASSWORD, userSession.getString(KEY_SESSIONPASSWORD, null));
        return userData;
    }

    public boolean checkRememberMe() {
        if (userSession.getBoolean(IS_REMEMBERME, false)) {
            return true;
        } else {
            return false;
        }
    }

}
