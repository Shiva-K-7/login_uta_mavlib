package edu.uta.mavs.login_uta_mavlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LibrarianMenu extends AppCompatActivity {

    private final DBMgr dbMgr = DBMgr.getInstance();

    @Override
    public void onBackPressed() {
        dbMgr.logout(LibrarianMenu.this);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_menu);

        final Button search_books = findViewById(R.id.search_books_librarian_menu);
        final Button view_res_or_cout_books = findViewById(R.id.view_reserved_or_checked_out_books_librarian_menu);
        final Button manage_books = findViewById(R.id.manage_books_librarian_menu);
        final Button checkin = findViewById(R.id.checkin_books);
        final Button about = findViewById(R.id.about);


        search_books.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(LibrarianMenu.this, SearchController.class);
                startActivity(i1);
            }
        });

        view_res_or_cout_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(LibrarianMenu.this, CheckoutController.class);
                startActivity(i2);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(LibrarianMenu.this,AboutUs.class);
                startActivity(i2);
            }
        });

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(LibrarianMenu.this, CheckinController.class);
                startActivity(i2);
            }
        });

        manage_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(LibrarianMenu.this,ManageBooks.class);
                startActivity(i2);
            }
        });

    }
}
