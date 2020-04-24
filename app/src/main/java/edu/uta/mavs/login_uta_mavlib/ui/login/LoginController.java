package  edu.uta.mavs.login_uta_mavlib.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import edu.uta.mavs.login_uta_mavlib.DBMgr;
import edu.uta.mavs.login_uta_mavlib.R;
import edu.uta.mavs.login_uta_mavlib.RegisterController;

public class LoginController extends AppCompatActivity {

    private DBMgr dbMgr;

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
        dbMgr.getLoggedInStatus(LoginController.this);

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

                dbMgr.login(em, pass, LoginController.this);

            }
        });
    }
}
