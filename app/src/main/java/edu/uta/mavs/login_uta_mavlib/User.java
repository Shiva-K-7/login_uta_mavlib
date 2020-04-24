package edu.uta.mavs.login_uta_mavlib;

public class User {

    protected String userId;
    protected String userFName;
    protected String userLName;
    protected String userEmail;
    protected String userPassword;


    public User(String aUserId, String aUserFName, String aUserLName, String aUserEmail, String aUserPass) {
        userId = aUserId;
        userFName = aUserFName;
        userLName = aUserLName;
        userEmail = aUserEmail;
        userPassword = aUserPass;
    }

    public String getId(){ return userId; }

    public String getFName(){ return userFName; }

    public String getLName(){ return userLName; }

    public String getEmail(){ return userEmail; }

    public boolean verifyPassword(String aPass){
        if (aPass == userPassword)
            return true;
        else
            return false;
    }

    public String getPassword(){ return userPassword; }

}
