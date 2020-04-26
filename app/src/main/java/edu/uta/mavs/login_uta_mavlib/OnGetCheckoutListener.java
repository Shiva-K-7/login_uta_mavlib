package edu.uta.mavs.login_uta_mavlib;

public interface OnGetCheckoutListener {

    void onSuccess(Checkout checkout);
    void onStart();
    void onFailure();
}
