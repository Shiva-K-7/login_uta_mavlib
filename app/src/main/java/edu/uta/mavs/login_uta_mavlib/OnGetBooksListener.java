package edu.uta.mavs.login_uta_mavlib;

import java.util.ArrayList;

public interface OnGetBooksListener {

    void onSuccess(ArrayList<Book> books);
    void onStart();
    void onFailure();
}
