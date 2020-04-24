package edu.uta.mavs.login_uta_mavlib;

import java.util.ArrayList;

public interface OnGetReservationListener {

    void onSuccess(ArrayList<Reservation> reservations);
    void onStart();
    void onFailure();
}
