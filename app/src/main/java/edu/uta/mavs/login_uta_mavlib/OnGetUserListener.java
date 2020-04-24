package edu.uta.mavs.login_uta_mavlib;

public interface OnGetUserListener {
    void onSuccess(User user);
    void onStart();
    void onFailure();
}
