package edu.uta.mavs.login_uta_mavlib;

public interface OnGetBookListener {

    void onSuccess(Book book);
    void onStart();
    void onFailure();
}
