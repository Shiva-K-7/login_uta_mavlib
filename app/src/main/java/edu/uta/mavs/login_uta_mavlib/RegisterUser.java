package edu.uta.mavs.login_uta_mavlib;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

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
        StudentID.setInputType(InputType.TYPE_CLASS_NUMBER);
        final EditText Email=findViewById(R.id.Email);
        final EditText Password=findViewById(R.id.password);
        final EditText ConfirmPassword=findViewById(R.id.confirm_password);
        final ProgressBar loading = findViewById(R.id.loading);



        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String fn = FirstName.getText().toString().trim();
                final String ln = LastName.getText().toString().trim();
                final String sid = StudentID.getText().toString().trim();
                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String cp = ConfirmPassword.getText().toString().trim();

                DBMgr dbMgr = DBMgr.getInstance();

                //todo - is this check needed?
//                if (dbMgr.getLoggedInStatus(RegisterUser.this)) {
//                    startActivity(new Intent(getApplicationContext(), StudentMainMenuActivity.class));
//                    finish();
//                }

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
                if(sid.length()!=10){
                    StudentID.setError("The student id should be 10 numbers");
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
                if(!cp.equals(password))
                {
                    ConfirmPassword.setError("Password entered does not match with the above password.");
                    return;
                }

                Student newStudent = new Student(sid, fn, ln, email, password);


                loading.setVisibility(View.VISIBLE);

                dbMgr.storeStudent(newStudent, RegisterUser.this);

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });
    }
}
