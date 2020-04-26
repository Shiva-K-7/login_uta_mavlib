package edu.uta.mavs.login_uta_mavlib;

import java.io.Serializable;

public class Book implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private String category;
    private int total;
    private int numAvailable;


    public Book() {
        //only for firestore
    }

    public Book(String aIsbn, String aTitle, String aAuthor, String aCategory, int aTotal){
        isbn = aIsbn;
        title = aTitle;
        author = aAuthor;
        category = aCategory;
        total = aTotal;
        numAvailable = aTotal;
    }

    public Book(String aIsbn, String aTitle, String aAuthor, String aCategory, int aTotal, int aAvailable){
        isbn = aIsbn;
        title = aTitle;
        author = aAuthor;
        category = aCategory;
        total = aTotal;
        numAvailable = aAvailable;
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

    public int getNumAvailable() { return numAvailable; }

    public boolean checkAvailability() {
        if (numAvailable > 0)
            return true;
        else
            return false;
    }

    public void increaseAvailabilityCount() { numAvailable++; }

    public void reduceAvailabilityCount() { numAvailable--; }
}
