package edu.uta.mavs.login_uta_mavlib;

import com.google.firebase.database.Exclude;

import java.time.LocalDate;

public class Checkout {

    private String userId;
    private String isbn;
    private String issueDate;
    private LocalDate dueDate;

    public Checkout(){
        //for firestore
    }

    public Checkout(String aUserId, String aIsbn) {
        userId = aUserId;
        isbn = aIsbn;
        issueDate = LocalDate.now().toString();
        dueDate = LocalDate.now().plusWeeks(1);
    }

    public Checkout(String aUserId, String aIsbn, String aIssueDate, LocalDate aDueDate) {
        userId = aUserId;
        isbn = aIsbn;
        issueDate = aIssueDate;
        dueDate = aDueDate;
    }

    public String getUserId() { return userId; }

    public String getIsbn() { return isbn; }

    public String getIssueDate() { return issueDate; }

    @Exclude
    public String getStrDueDate() { return dueDate.toString(); }

    public LocalDate getDueDate() { return dueDate; }

}
