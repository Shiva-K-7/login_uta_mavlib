package edu.uta.mavs.login_uta_mavlib;

import java.time.LocalDate;

public class Checkout {

    private String mUserId;
    private String mIsbn;
    private LocalDate mIssueDate;
    private LocalDate mDueDate;


    public Checkout(String aUserId, String aIsbn) {
        mUserId = aUserId;
        mIsbn = aIsbn;
        mIssueDate = LocalDate.now();
        mDueDate = mIssueDate.plusWeeks(1);
    }

    public String getSId() { return mUserId; }

    public String getIsbn() { return mIsbn; }

    public String getIssueDate() { return mIssueDate.toString(); }

    public String getStrDueDate() { return mDueDate.toString(); }

    public LocalDate getDueDate() { return mDueDate; }

}
