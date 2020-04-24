package edu.uta.mavs.login_uta_mavlib;

import java.time.LocalDate;

public class Reservation {

    private String userId;
    private String isbn;
    private String availableDate;

    public Reservation(){
        //for firestore
    }

    public Reservation(String aUserId, String aIsbn){
        userId = aUserId;
        isbn = aIsbn;
        availableDate = LocalDate.now().toString();
    }

    public Reservation(String aUserId, String aIsbn, String aAvailableDate){
        userId = aUserId;
        isbn = aIsbn;
        availableDate = aAvailableDate;
    }

    public String getUserId() { return userId; }

    public String getIsbn() { return isbn; }

    public String getAvailableDate() { return availableDate; }

}
