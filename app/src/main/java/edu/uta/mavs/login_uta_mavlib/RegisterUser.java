package edu.uta.mavs.login_uta_mavlib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.uta.mavs.login_uta_mavlib.ui.login.LoginActivity;

public class RegisterUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        final Button register = findViewById(R.id.register);
        final EditText FirstName=findViewById(R.id.FirsName);
        final EditText LastName=findViewById(R.id.LastName);
        final EditText StudentID=findViewById(R.id.StudentID);
        final EditText Email=findViewById(R.id.Email);
        final EditText Password=findViewById(R.id.password);
        final EditText ConfirmPassword=findViewById(R.id.confirm_password);
        final ProgressBar loading = findViewById(R.id.loading);
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String fn = FirstName.getText().toString().trim();
                String ln = LastName.getText().toString().trim();
                String sid = StudentID.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String cp = Password.getText().toString().trim();

                if(TextUtils.isEmpty(fn))
                {
                    FirstName.setError("First Name is required.");
                    return;
                }if(TextUtils.isEmpty(ln))
                {
                    LastName.setError("Last Name is required.");
                    return;
                }if(TextUtils.isEmpty(sid))
                {
                    StudentID.setError("Student ID is required.");
                    return;
                }
                if(TextUtils.isEmpty(email))
                {
                    Email.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Password.setError("password is required.");
                    return;
                }
                if(TextUtils.isEmpty(cp))
                {
                    ConfirmPassword.setError("confirm password!");
                    return;
                }
                if(password.length()<8)
                {
                    Password.setError("Password length should be at least 8.");
                    return;
                }
             /*   if(cp.equals(password)){}
                else
                {
                    ConfirmPassword.setError("Password entered does not match with the above password.");
                    return;
                }*/

                loading.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterUser.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterUser.this, "Error..!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                //Intent i2=new Intent(RegisterUser.this, StudentMainMenuActivity.class);
                //startActivity(i2);
                //setContentView(R.layout.activity_search_books);
            }
        });
    }
}
