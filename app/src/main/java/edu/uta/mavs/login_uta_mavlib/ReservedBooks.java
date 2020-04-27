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

public class ReservedBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_books);

        final String viewResAndCheckedoutBooksControllerLog = "viewResAndCheckedoutBooksController Log" ;

        final ArrayList< Book > reservedBooks = new ArrayList<>();
        final ArrayList< Book > checkedoutBooks = new ArrayList<>();

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
                    }
                    else
                    {
                        checkedoutBooks.add( userBooks.get(i));
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

                    bookMap.put( resTitleKey, thisBook.getTitle( ).toUpperCase( ) ) ;
                    bookMap.put( resAuthKey, thisBook.getAuthor( ).toUpperCase( ) ) ;
                    resTitleAndAuthor.add( bookMap ) ;
                }

                for( int i = 0; i < checkedoutBooks.size( ); i++ )
                {
                    Map< String, String > bookMap = new HashMap< >();
                    Book thisBook = checkedoutBooks.get( i ) ;

                    bookMap.put( chckoutTitleKey, thisBook.getTitle( ).toUpperCase( ) ) ;
                    bookMap.put( chckoutAuthorKey, thisBook.getAuthor( ).toUpperCase( ) ) ;
                    checkedoutTitleAndAuthor.add( bookMap ) ;
                }

                //Reserved Books
                String[ ] reservedMap = new String[ ]{ resTitleKey, resAuthKey } ;
                int[ ] layoutReserved = new int[ ] { android.R.id.text1, android.R.id.text2 } ;

                SimpleAdapter reservedAdapter = new SimpleAdapter( ReservedBooks.this, resTitleAndAuthor,
                        android.R.layout.simple_list_item_2, reservedMap, layoutReserved ) ;

                final ListView bookResults1 = findViewById(R.id.list_reserved_books);
                bookResults1.setAdapter(reservedAdapter);

                // Checkedout Books
                String[ ] checkedoutMap = new String[ ]{ chckoutTitleKey, chckoutAuthorKey } ;
                int[ ] layoutCheckedout = new int[ ] { android.R.id.text1, android.R.id.text2 } ;

                SimpleAdapter checkedoutAdapter = new SimpleAdapter( ReservedBooks.this, checkedoutTitleAndAuthor,
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

        /*

        final ListView LVreservedBooks = findViewById(R.id.list_reserved_books);
        final ListView LVcheckedoutBooks = findViewById(R.id.list_checkedout_books);

        final DBMgr dbMgr = DBMgr.getInstance();

        final ArrayList< Checkout > myCheckouts = new ArrayList<>();


        dbMgr.getCurrentUser(new OnGetUserListener() {
            @Override
            public void onSuccess(User user) {
                dbMgr.getCheckouts(null, user.getUserId(), new OnGetCheckoutsListener() {
                    @Override
                    public void onSuccess(ArrayList<Checkout> checkouts) {
                        myCheckouts.addAll(checkouts);
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

        for( Checkout thisCheckout : myCheckouts )
        {
            thisCheckout.getIsbn() ;
        }

        /*
        final ArrayList< Book > ALreservedBooks = new ArrayList<>();
        final ArrayList< Book > ALcheckedoutBooks = new ArrayList<>();

        final DBMgr dbMgr = DBMgr.getInstance( ) ;
        dbMgr.getCurrentUser(new OnGetUserListener() {
            @Override
            public void onSuccess(User user) {
                dbMgr.getCheckouts(null, user.getUserId(), new OnGetCheckoutsListener() {
                    @Override
                    public void onSuccess(ArrayList<Checkout> checkouts) {
                        for( int i = 0; i < checkouts.size(); i++)
                        {
                            boolean checkedOut = false ;
                            String bookISBN = checkouts.get( i ).getIsbn( ) ;
                            if( checkouts.get( i ).getDueDate() != null )
                            {
                                Log.i(viewResAndCheckedoutBooksControllerLog, "Book has a due date - adding to checkedoutBooks list");
                                checkedOut = true ;
                            }
                            else
                            {
                                Log.i(viewResAndCheckedoutBooksControllerLog, "Book does not have due date - adding to reservedBooks list");
                            }

                            final boolean finalCheckedOut = checkedOut;
                            dbMgr.getBook(bookISBN, new OnGetBookListener() {
                                @Override
                                public void onSuccess(Book book) {
                                    if( finalCheckedOut)
                                    {
                                        ALcheckedoutBooks.add( book ) ;
                                    }
                                    else
                                    {
                                        ALreservedBooks.add( book ) ;
                                    }

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

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });






        /*

        ListView lsitView1 = (ListView) findViewById(R.id.list_reserved_books);

        ArrayList<String> arrayList = new ArrayList<>();


        arrayList.add("Design Patterns");
        arrayList.add("Artificial Intelligence");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        lsitView1.setAdapter(arrayAdapter);

        ListView lsitView = (ListView) findViewById(R.id.list_checkedout_books);

        ArrayList<String> arrayList1 = new ArrayList<>();

        arrayList1.add("Software Engineering Due Date: 04/11/2020");
        arrayList1.add("Theory of Computation Due Date: 04/15/2020");


        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList1);

        lsitView.setAdapter(arrayAdapter1);

        */
    }
}
