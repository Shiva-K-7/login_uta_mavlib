package edu.uta.mavs.login_uta_mavlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StudentMenu extends AppCompatActivity {

    private final DBMgr dbMgr = DBMgr.getInstance();

    @Override
    public void onBackPressed() {
        dbMgr.logout(StudentMenu.this);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_menu);

        final Button search_books = findViewById(R.id.search_books_librarian_menu);
        final Button view_res_or_cout_books = findViewById(R.id.view_reserved_or_checked_out_books_librarian_menu);
        final Button about = findViewById(R.id.about);

        search_books.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(StudentMenu.this, SearchController.class);
                startActivity(i2);
            }
        });

        view_res_or_cout_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent(StudentMenu.this,ReservedBooks.class);
                startActivity(i3);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4=new Intent(StudentMenu.this,AboutUs.class);
                startActivity(i4);
            }
        });
    }

}
