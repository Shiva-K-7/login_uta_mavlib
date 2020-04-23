package edu.uta.mavs.login_uta_mavlib;

public class Book {
    private String mIsbn;
    private String mTitle;
    private String mAuthor;
    private String mCategory;
    private int mTotal;
    private int mNumIssued;
    private int mNumReserved;


    public Book(String aIsbn, String aTitle, String aAuthor, String aCategory, int aTotal){
        mIsbn = aIsbn;
        mTitle = aTitle;
        mAuthor = aAuthor;
        mCategory = aCategory;
        mTotal = aTotal;
        mNumIssued = 0;
        mNumReserved = 0;
    }

    public String getIsbn() {
        return mIsbn;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getCategory() {
        return mCategory;
    }

    public int getTotal() { return mTotal; }

    public int getIssued() { return mNumIssued; }

    public int getReserved() { return mNumReserved; }

    public boolean getAvailability() {
        if (mTotal - mNumReserved - mNumIssued > 0)
            return true;
        else
            return false;
    }

    public void increaseAvailabilityCount(boolean aCheckIn, boolean aUnReserve) {
        if (aCheckIn) {
            mNumIssued--;
            if (mNumIssued < 0)
                mNumIssued = 0;
        }
        if (aUnReserve) {
            mNumReserved --;
            if (mNumReserved < 0)
                mNumReserved = 0;
        }
    }

    public void reduceAvailabilityCount(boolean aCheckOut, boolean aReserve) {
        if (aCheckOut) {
            mNumIssued++;
        }
        if (aReserve) {
            mNumReserved ++;
        }
    }
}
