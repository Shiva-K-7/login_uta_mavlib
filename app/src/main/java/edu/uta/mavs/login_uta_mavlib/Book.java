package edu.uta.mavs.login_uta_mavlib;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String category;
    private int total;
    private int numIssued;
    private int numReserved;


    public Book(){
        //only for firestore
    }

    public Book(String aIsbn, String aTitle, String aAuthor, String aCategory, int aTotal){
        isbn = aIsbn;
        title = aTitle;
        author = aAuthor;
        category = aCategory;
        total = aTotal;
        numIssued = 0;
        numReserved = 0;
    }

    public Book(String aIsbn, String aTitle, String aAuthor, String aCategory, int aTotal, int aIssued, int aReserved){
        isbn = aIsbn;
        title = aTitle;
        author = aAuthor;
        category = aCategory;
        total = aTotal;
        numIssued = aIssued;
        numReserved = aReserved;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getTotal() { return total; }

    public int getIssued() { return numIssued; }

    public int getReserved() { return numReserved; }

    public boolean checkAvailability() {
        if (total - numReserved - numIssued > 0)
            return true;
        else
            return false;
    }

    public void increaseAvailabilityCount(boolean aCheckIn, boolean aUnReserve) {
        if (aCheckIn) {
            numIssued--;
            if (numIssued < 0)
                numIssued = 0;
        }
        if (aUnReserve) {
            numReserved--;
            if (numReserved < 0)
                numReserved = 0;
        }
    }

    public void reduceAvailabilityCount(boolean aCheckOut, boolean aReserve) {
        if (aCheckOut) {
            numIssued++;
        }
        if (aReserve) {
            numReserved++;
        }
    }
}
