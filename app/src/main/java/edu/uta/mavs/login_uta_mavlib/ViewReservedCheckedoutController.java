package edu.uta.mavs.login_uta_mavlib;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewReservedCheckedoutController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_books);

        final String viewResAndCheckedoutBooksControllerLog = "viewResAndCheckedoutBooksController Log" ;

        final ArrayList< Book > reservedBooks = new ArrayList<>();
        final ArrayList< Book > checkedoutBooks = new ArrayList<>();
        final ArrayList< String > dueDates = new ArrayList<>();
        final ArrayList< String > pickDates = new ArrayList<>();

        Log.i( viewResAndCheckedoutBooksControllerLog, "Building list of books");

        DBMgr dbMgr = DBMgr.getInstance();

        dbMgr.getResAndCheckedoutBooks(new OnGetResAndCheckedoutBooks() {
            @Override
            public void onSuccess(ArrayList<Checkout> checkouts, ArrayList<Book> userBooks ) {

                for( int i = 0; i < checkouts.size( ); i++ )
                {
                    if( checkouts.get(i).getDueDate().isEmpty())
                    {
                        reservedBooks.add( userBooks.get(i));
                        pickDates.add(checkouts.get(i).getAvailableDate());
                    }
                    else
                    {
                        checkedoutBooks.add( userBooks.get(i));
                        dueDates.add( checkouts.get(i).getDueDate());
                    }
                }

                String resTitleKey = "reservedTitleKey" ;
                String resAuthKey = "reservedAuthorKey" ;
                String chckoutTitleKey = "chckoutTitleKey" ;
                String chckoutAuthorKey = "chckoutAuthorKey" ;

                List<Map<String, String>> resTitleAndAuthor = new ArrayList<>();
                List<Map<String, String>> checkedoutTitleAndAuthor = new ArrayList<>();

                for( int i = 0; i < reservedBooks.size( ); i++ )
                {
                    Map< String, String > bookMap = new HashMap< >();
                    Book thisBook = reservedBooks.get( i ) ;

                    bookMap.put( resTitleKey, thisBook.getTitle( ).toUpperCase( ) + " - " + thisBook.getAuthor( ).toUpperCase( ) ) ;
                    bookMap.put( resAuthKey, "Book available: " + pickDates.get(i) ) ;
                    resTitleAndAuthor.add( bookMap ) ;
                }

                for( int i = 0; i < checkedoutBooks.size( ); i++ )
                {
                    Map< String, String > bookMap = new HashMap< >();
                    Book thisBook = checkedoutBooks.get( i ) ;

                    bookMap.put( chckoutTitleKey, thisBook.getTitle( ).toUpperCase( ) + " - " + thisBook.getAuthor( ).toUpperCase( ) ) ;
                    bookMap.put( chckoutAuthorKey, "Book due: " + dueDates.get( i ) ) ;
                    checkedoutTitleAndAuthor.add( bookMap ) ;
                }

                //Reserved Books
                String[ ] reservedMap = new String[ ]{ resTitleKey, resAuthKey } ;
                int[ ] layoutReserved = new int[ ] { android.R.id.text1, android.R.id.text2 } ;

                SimpleAdapter reservedAdapter = new SimpleAdapter( ViewReservedCheckedoutController.this, resTitleAndAuthor,
                        android.R.layout.simple_list_item_2, reservedMap, layoutReserved ) ;

                final ListView bookResults1 = findViewById(R.id.list_reserved_books);
                bookResults1.setAdapter(reservedAdapter);

                // Checkedout Books
                String[ ] checkedoutMap = new String[ ]{ chckoutTitleKey, chckoutAuthorKey } ;
                int[ ] layoutCheckedout = new int[ ] { android.R.id.text1, android.R.id.text2 } ;

                SimpleAdapter checkedoutAdapter = new SimpleAdapter( ViewReservedCheckedoutController.this, checkedoutTitleAndAuthor,
                        android.R.layout.simple_list_item_2, checkedoutMap, layoutCheckedout ) ;

                final ListView bookResults2 = findViewById(R.id.list_checkedout_books);
                bookResults2.setAdapter(checkedoutAdapter);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
    }
}
