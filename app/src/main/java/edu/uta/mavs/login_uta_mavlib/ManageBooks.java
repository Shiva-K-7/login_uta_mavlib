package edu.uta.mavs.login_uta_mavlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_books);

        final Button add_book = findViewById(R.id.add_books);
        final Button remove_book = findViewById(R.id.delete_book);

        add_book.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(ManageBooks.this, AddBookController.class);
                startActivity(i2);
                //setContentView(R.layout.activity_add_book);
            }
        });

        remove_book.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ManageBooks.this, DeleteBookController.class);
                startActivity(i);
                //setContentView(R.layout.activity_search_books);
            }
        });
    }
}
