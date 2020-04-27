package edu.uta.mavs.login_uta_mavlib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SearchBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);

        Log.i("Jacob", "Jacob searching for books");

        final String Jacob = "Jacob" ;


        final Button search = findViewById(R.id.button5);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SearchBooks.this,SearchResults.class);
                startActivity(i);

                TextInputEditText input_ISBN = (TextInputEditText) findViewById(R.id.TIET_ISBN);
                TextInputEditText input_title = (TextInputEditText) findViewById(R.id.TIET_Title);
                TextInputEditText input_author = (TextInputEditText) findViewById(R.id.TIET_Author);
                TextInputEditText input_category = (TextInputEditText) findViewById(R.id.TIET_Category);

                DBMgr searchBooksDBMgr = DBMgr.getInstance();

                String myISBN = input_ISBN.getText().toString();
                String myTitle = input_title.getText().toString();
                String myAuthor = input_author.getText().toString();
                String myCategory = input_category.getText().toString();

                searchBooksDBMgr.getBooks( myISBN, myTitle, myAuthor, myCategory, new OnGetBooksListener( ) {
                    @Override
                    public void onSuccess( ArrayList< Book > books ) {
                        Log.i( Jacob, "Num of books found = " + books.size( ) ) ;
                    }

                    @Override
                    public void onStart( ) {
                        Log.i( Jacob, "Attempting to search for books" ) ;
                    }

                    @Override
                    public void onFailure( ) {
                        Log.i( Jacob, "No books found based on search criteria" ) ;
                    }
                } ) ;


            }
        });
    }
}
