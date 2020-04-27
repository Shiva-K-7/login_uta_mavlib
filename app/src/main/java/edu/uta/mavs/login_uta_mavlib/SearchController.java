package edu.uta.mavs.login_uta_mavlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchController extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);

        final String SearchControllerLog = "SearchController Log" ;

        Log.i( SearchControllerLog, "Searching for books" ) ;

        final Button search = findViewById(R.id.button5);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                TextInputEditText input_ISBN = (TextInputEditText) findViewById(R.id.TIET_ISBN);
                TextInputEditText input_title = (TextInputEditText) findViewById(R.id.TIET_Title);
                TextInputEditText input_author = (TextInputEditText) findViewById(R.id.TIET_Author);
                TextInputEditText input_category = (TextInputEditText) findViewById(R.id.TIET_Category);

                DBMgr searchBooksDBMgr = DBMgr.getInstance();

                String myISBN = input_ISBN.getText().toString().toLowerCase();
                String myTitle = input_title.getText().toString().toLowerCase();
                String myAuthor = input_author.getText().toString().toLowerCase();
                String myCategory = input_category.getText().toString().toLowerCase();

                String isbn = "isbn" ;
                String title = "title" ;
                String author = "author" ;
                String category = "category" ;

                ArrayList< String > search_fields = new ArrayList<>( );
                ArrayList< String > search_inputs = new ArrayList<>( ) ;

                if( !myISBN.isEmpty( ) )
                {
                    search_fields.add( isbn ) ;
                    search_inputs.add( myISBN ) ;
                }

                if( !myTitle.isEmpty( ) )
                {
                    search_fields.add( title ) ;
                    search_inputs.add( myTitle ) ;
                }

                if( !myAuthor.isEmpty( ) )
                {
                    search_fields.add( author ) ;
                    search_inputs.add( myAuthor ) ;

                }

                if( !myCategory.isEmpty( ) )
                {
                    search_fields.add( category ) ;
                    search_inputs.add( myCategory ) ;

                }

                Log.i( SearchControllerLog, "search_fields size = " + search_fields.size( ) ) ;

                if( search_fields.size( ) == 4 ) {
                    searchBooksDBMgr.getBooksAll(myISBN, myTitle, myAuthor, myCategory, new OnGetBooksListener() {
                        @Override
                        public void onSuccess(ArrayList<Book> books) {
                            Log.i(SearchControllerLog, "All Fields Filled Search: Num of books found = " + books.size());

                            if( books.size( ) > 0 ) {
                                Intent intent = new Intent(SearchController.this, SearchResults.class);
                                Bundle args = new Bundle();
                                args.putSerializable("searchIntent", (Serializable) books);
                                intent.putExtra("BUNDLE", args);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SearchController.this, "Search criteria did not return any results",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onStart() {
                            Log.i(SearchControllerLog, "Attempting to search for books");
                        }

                        @Override
                        public void onFailure() {
                            Log.i(SearchControllerLog, "No books found based on search criteria");
                        }

                    });
                }

                else if( search_fields.size( ) == 3 )
                {
                    searchBooksDBMgr.getBooks( search_fields.get( 0 ), search_fields.get( 1 ), search_fields.get( 2 ),
                            search_inputs.get( 0 ), search_inputs.get( 1 ), search_inputs.get( 2 ),  new OnGetBooksListener() {
                        @Override
                        public void onSuccess(ArrayList<Book> books) {
                            Log.i(SearchControllerLog, "Triple Field Search: Num of books found = " + books.size( ) ) ;

                            if( books.size( ) > 0 ) {
                                Intent intent = new Intent(SearchController.this, SearchResults.class);
                                Bundle args = new Bundle();
                                args.putSerializable("searchIntent", (Serializable) books);
                                intent.putExtra("BUNDLE", args);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SearchController.this, "Search criteria did not return any results",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onStart() {
                            Log.i(SearchControllerLog, "Attempting to search for books");
                        }

                        @Override
                        public void onFailure() {
                            Log.i(SearchControllerLog, "No books found based on search criteria");
                        }
                    });
                }

                else if( search_fields.size( ) == 2 )
                {
                    searchBooksDBMgr.getBooks( search_fields.get( 0 ), search_fields.get( 1 ),
                            search_inputs.get( 0 ), search_inputs.get( 1 ), new OnGetBooksListener() {
                        @Override
                        public void onSuccess(ArrayList<Book> books) {
                            Log.i(SearchControllerLog, "Double Field Search: Num of books found = " + books.size( ) ) ;

                            if( books.size( ) > 0 ) {
                                Intent intent = new Intent(SearchController.this, SearchResults.class);
                                Bundle args = new Bundle();
                                args.putSerializable("searchIntent", (Serializable) books);
                                intent.putExtra("BUNDLE", args);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SearchController.this, "Search criteria did not return any results",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onStart() {
                            Log.i(SearchControllerLog, "Attempting to search for books");
                        }

                        @Override
                        public void onFailure() {
                            Log.i(SearchControllerLog, "No books found based on search criteria");
                        }
                    });
                }

                else if( search_fields.size( ) == 1 )
                {
                    searchBooksDBMgr.getBooks( search_fields.get( 0 ), search_inputs.get( 0 ), new OnGetBooksListener() {
                        @Override
                        public void onSuccess(ArrayList<Book> books) {
                            Log.i(SearchControllerLog, "Single Field Search: Num of books found = " + books.size( ) ) ;

                            if( books.size( ) > 0 ) {
                                Intent intent = new Intent(SearchController.this, SearchResults.class);
                                Bundle args = new Bundle();
                                args.putSerializable("searchIntent", (Serializable) books);
                                intent.putExtra("BUNDLE", args);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SearchController.this, "Search criteria did not return any results",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onStart() {
                            Log.i(SearchControllerLog, "Attempting to search for books");
                        }

                        @Override
                        public void onFailure() {
                            Log.i(SearchControllerLog, "No books found based on search criteria");
                        }
                    });
                }
            }
        });
    }
}
