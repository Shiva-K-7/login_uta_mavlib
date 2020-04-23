package edu.uta.mavs.login_uta_mavlib;

public class User {

    protected String mUserId;
    protected String mUserFName;
    protected String mUserLName;
    protected String mUserEmail;
    protected String mUserPass;


    public User(String aUserId, String aUserFName, String aUserLName, String aUserEmail, String aUserPass) {
        mUserId = aUserId;
        mUserFName = aUserFName;
        mUserLName = aUserLName;
        mUserEmail = aUserEmail;
        mUserPass = aUserPass;
    }

    public String getId(){ return mUserId; }

    public String getFName(){ return mUserFName; }

    public String getLName(){ return mUserLName; }

    public String getEmail(){ return mUserEmail; }

    public boolean verifyPassword(String aPass){
        if (aPass == mUserPass)
            return true;
        else
            return false;
    }

}
