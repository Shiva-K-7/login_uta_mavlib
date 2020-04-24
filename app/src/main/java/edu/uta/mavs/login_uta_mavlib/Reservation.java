package edu.uta.mavs.login_uta_mavlib;

import java.time.LocalDate;

public class Reservation {

    private String mUserId;
    private String mIsbn;
    private LocalDate mAvailableDate;

    public Reservation(String aUserId, String aIsbn){
        mUserId = aUserId;
        mIsbn = aIsbn;
        mAvailableDate = LocalDate.now();
    }

    //todo: constructor from database

    public String getSid() { return mUserId; }

    public String getIsbn() { return mIsbn; }

    public String getAvailDate() { return mAvailableDate.toString(); }

}
