package edu.uta.mavs.login_uta_mavlib;

import java.util.ArrayList;

public interface OnGetCheckoutListener {

    void onSuccess(ArrayList<Checkout> checkouts);
    void onStart();
    void onFailure();
}
