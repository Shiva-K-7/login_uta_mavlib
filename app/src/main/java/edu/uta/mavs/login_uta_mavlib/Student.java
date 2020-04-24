package edu.uta.mavs.login_uta_mavlib;

public class Student extends User {

    public Student(){
        //for firestore
        super();
    }

    public Student(String aUserId, String aUserFName, String aUserLName, String aUserEmail, String aUserPass) {
        super(aUserId, aUserFName, aUserLName, aUserEmail, aUserPass);
    }

}
