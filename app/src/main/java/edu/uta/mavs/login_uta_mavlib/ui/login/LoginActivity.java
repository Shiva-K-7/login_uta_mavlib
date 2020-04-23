package  edu.uta.mavs.login_uta_mavlib.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.uta.mavs.login_uta_mavlib.LibrarianMenu;
import edu.uta.mavs.login_uta_mavlib.R;
import edu.uta.mavs.login_uta_mavlib.RegisterUser;
import edu.uta.mavs.login_uta_mavlib.StudentMainMenuActivity;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        final EditText email = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button new_user = findViewById(R.id.new_user);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);


        if(mAuth.getCurrentUser()!=null){
            Toast.makeText(LoginActivity.this, "You are logged in already!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), StudentMainMenuActivity.class));
            finish();
        }




        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterUser.class);
                startActivity(i);
                //setContentView(R.layout.activity_registration);
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

                mAuth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LibrarianMenu.class));
                        }
                    }
                });
                //Intent i = new Intent(LoginActivity.this, LibrarianMenu.class);
                //startActivity(i);
                //setContentView(R.layout.activity_librarian_menu);
                //setContentView(R.layout.activity_student_main_menu);
                /*loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());*/
            }
        });
    }
}
