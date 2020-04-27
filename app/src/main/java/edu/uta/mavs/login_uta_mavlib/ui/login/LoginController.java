package  edu.uta.mavs.login_uta_mavlib.ui.login;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import edu.uta.mavs.login_uta_mavlib.DBMgr;
import edu.uta.mavs.login_uta_mavlib.NotificationController;
import edu.uta.mavs.login_uta_mavlib.R;
import edu.uta.mavs.login_uta_mavlib.RegisterController;
import edu.uta.mavs.login_uta_mavlib.password_security;

public class LoginController extends AppCompatActivity {

    private static final String TAG = "LoginController";

    private DBMgr dbMgr;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button new_user = findViewById(R.id.new_user);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);


        dbMgr = DBMgr.getInstance();

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginController.this, RegisterController.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if(TextUtils.isEmpty(em))
                {
                    email.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    password.setError("password is required.");
                    return;
                }
                if(pass.length()<8)
                {
                    password.setError("Password length should be at least 8.");
                    return;
                }

                final String password_encrypted = password_security.getMD5EncryptedValue(pass);

                dbMgr.login(em, password_encrypted, LoginController.this);

                scheduleJob();

            }
        });
    }


    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, NotificationController.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

}
