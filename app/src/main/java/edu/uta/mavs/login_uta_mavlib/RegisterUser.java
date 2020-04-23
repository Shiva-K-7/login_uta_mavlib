package edu.uta.mavs.login_uta_mavlib;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore database = FirebaseFirestore.getInstance();


        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String fn = FirstName.getText().toString().trim();
                final String ln = LastName.getText().toString().trim();
                final String sid = StudentID.getText().toString().trim();
                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String confirmPass = ConfirmPassword.getText().toString().trim();

                if(fAuth.getCurrentUser()!=null){
                    Toast.makeText(RegisterUser.this, "You are logged in already!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), StudentMainMenuActivity.class));
                    finish();
                }

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
                if(TextUtils.isEmpty(confirmPass))
                {
                    ConfirmPassword.setError("confirm password!");
                    return;
                }
                if(password.length()<8)
                {
                    Password.setError("Password length should be at least 8.");
                    return;
                }

                if( !confirmPass.equals( password ) )
                {
                    ConfirmPassword.setError("Passwords do not match.");
                    return;
                }

                loading.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterUser.this, "User Created", Toast.LENGTH_SHORT).show();
                            final String userID = fAuth.getCurrentUser().getUid();
                            DocumentReference dr = database.collection("Student").document(userID);
                            Map<String,Object> user = new HashMap<>();

                            user.put("userId", sid);
                            user.put("userFName", fn);
                            user.put("userLName", ln);
                            user.put("userEmail", email);
                            user.put("userPassword", password);

                            dr.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG" ,"onSuccess: User profile is created for "+userID);
                                }
                            });


                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterUser.this, "Error..!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
