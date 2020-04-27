package edu.uta.mavs.login_uta_mavlib;

import java.util.ArrayList;

public interface OnBuildCheckedoutBooksList {
    void onSuccess( ArrayList<Book> userBooks );
    void onStart();
    void onFailure();
}
