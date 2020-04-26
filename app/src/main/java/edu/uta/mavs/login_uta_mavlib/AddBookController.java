package edu.uta.mavs.login_uta_mavlib;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class AddBookController extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);


        final Button addbook = findViewById(R.id.add_books);
        final TextInputEditText isbn = findViewById(R.id.isbn);
        final TextInputEditText title = findViewById(R.id.book_title);
        final TextInputEditText author = findViewById(R.id.author);
        final TextInputEditText category = findViewById(R.id.category);
        final TextInputEditText total = findViewById(R.id.total);
        total.setInputType(InputType.TYPE_CLASS_NUMBER);



        addbook.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String ISBN = isbn.getText().toString().trim().toLowerCase();
                final String TITLE = title.getText().toString().trim().toLowerCase();
                final String AUTHOR = author.getText().toString().trim().toLowerCase();
                final String CATEGORY = category.getText().toString().trim().toLowerCase();
                final String TOTAL = total.getText().toString().trim();
                int totalBooks = 0;

                DBMgr dbMgr = DBMgr.getInstance();

                if(TextUtils.isEmpty(ISBN))
                {
                    isbn.setError("ISBN is required.");
                    return;
                }
                if(TextUtils.isEmpty(TITLE))
                {
                     title.setError("BOOK TITLE is required.");
                     return;
                }
                if(TextUtils.isEmpty(AUTHOR))
                {
                    author.setError("AUTHOR NAME is required.");
                    return;
                }
                if(TextUtils.isEmpty(CATEGORY))
                {
                    category.setError("CATEGORY is required.");
                    return;
                }
                if(TextUtils.isEmpty(TOTAL))
                {
                    total.setError("Give the total number of books to be added.");
                    return;
                }
                else
                {
                    totalBooks = Integer.parseInt(TOTAL);
                }

                Book newBook = new Book(ISBN, TITLE, AUTHOR, CATEGORY, totalBooks);

                dbMgr.storeBook(newBook, "Book added to database",AddBookController.this);
            }
        });
    }
}