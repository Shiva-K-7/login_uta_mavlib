package edu.uta.mavs.login_uta_mavlib;

import java.util.ArrayList;

public interface OnGetResAndCheckedoutBooks {
    void onSuccess( ArrayList< Checkout > checkouts, ArrayList<Book > userBooks );
    void onStart();
    void onFailure();
}
