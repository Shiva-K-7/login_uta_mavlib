package edu.uta.mavs.login_uta_mavlib;

import java.time.LocalDate;

public class Checkout {

    private String userId;
    private String isbn;
    private String availableDate;
    private String issueDate;
    private String dueDate;

    public Checkout(){
        //for firestore
    }

    public Checkout(String aUserId, String aIsbn, boolean isCheckout) {
        userId = aUserId;
        isbn = aIsbn;
        availableDate = LocalDate.now().toString();
        if (isCheckout){
            setDates();
        }
        else {
            issueDate = "";
            dueDate = "";
        }
    }

    public Checkout(String aUserId, String aIsbn, String aAvailableDate, String aIssueDate, String aDueDate) {
        userId = aUserId;
        isbn = aIsbn;
        availableDate = aAvailableDate;
        issueDate = aIssueDate;
        dueDate = aDueDate;
    }

    public String getUserId() { return userId; }

    public String getIsbn() { return isbn; }

    public String getAvailableDate() { return availableDate; }

    public String getIssueDate() { return issueDate; }

    public String getDueDate() { return dueDate; }

    public void setDates(){
        issueDate = LocalDate.now().toString();
        dueDate = LocalDate.now().plusWeeks(1).toString();
    }

}
