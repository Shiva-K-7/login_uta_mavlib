package edu.uta.mavs.login_uta_mavlib.ui.login;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import edu.uta.mavs.login_uta_mavlib.DBMgr;
import edu.uta.mavs.login_uta_mavlib.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                DBMgr dbMgr = DBMgr.getInstance();
                dbMgr.getLoggedInStatus(SplashScreenActivity.this);
            }

        }, SPLASH_TIME_OUT);
    }
}
