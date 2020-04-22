package edu.uta.mavs.login_uta_mavlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.uta.mavs.login_uta_mavlib.ui.login.LoginActivity;

public class StudentMainMenuActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        Toast.makeText(getApplicationContext(), "you have been logged out.\n Login again.", Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_menu);

        final Button search_books = findViewById(R.id.search_books_librarian_menu);
        final Button view_res_or_cout_books = findViewById(R.id.view_reserved_or_checked_out_books_librarian_menu);
        final Button about = findViewById(R.id.about);
        final Button notification = findViewById(R.id.notification);



        search_books.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(StudentMainMenuActivity.this,SearchBooks.class);
                startActivity(i2);
            }
        });

        view_res_or_cout_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent(StudentMainMenuActivity.this,ReservedBooks.class);
                startActivity(i3);
                //setContentView(R.layout.about);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4=new Intent(StudentMainMenuActivity.this,AboutUs.class);
                startActivity(i4);
                //setContentView(R.layout.about);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationGenerator.openActivityNotification(getApplicationContext());

            }
        });

    }
}
