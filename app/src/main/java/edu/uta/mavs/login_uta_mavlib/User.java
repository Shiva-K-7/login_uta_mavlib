package edu.uta.mavs.login_uta_mavlib;

public class User {

    protected String userId;
    protected String userFName;
    protected String userLName;
    protected String userEmail;
    protected String userPassword;


    public User () {
        //only for firestore database
    }

    public User(String aUserId, String aUserFName, String aUserLName, String aUserEmail, String aUserPass) {
        userId = aUserId;
        userFName = aUserFName;
        userLName = aUserLName;
        userEmail = aUserEmail;
        userPassword = aUserPass;
    }

    public String getUserId(){ return userId; }

    public String getUserFName(){ return userFName; }

    public String getUserLName(){ return userLName; }

    public String getUserEmail(){ return userEmail; }

    public boolean verifyPassword(String aPass){
        if (aPass == userPassword)
            return true;
        else
            return false;
    }

    public String getUserPassword(){ return userPassword; }

}
