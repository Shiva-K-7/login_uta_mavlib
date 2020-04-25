package edu.uta.mavs.login_uta_mavlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResults extends AppCompatActivity {

    //ListView bookResults;
    //ArrayList<String> returnedBookResults = new ArrayList<>();
    ArrayList<Book> returnedBookResults = new ArrayList<>();
    ArrayList< String > authors = new ArrayList< >( ) ;
    ArrayList< String > titles = new ArrayList< >( ) ;

    List<Map<String, String>> bookResultsTitleAndAuthor = new ArrayList<Map<String, String>>();
    DBMgr searchBooksDBManager ;

    Book myBook = new Book( "Design Patterns", "David Kung" );
    Book myBook2 = new Book( "Call of the Wild", "Jack London" );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //TODO: Add DB functionality to return book results
        returnedBookResults.add( myBook ) ;
        returnedBookResults.add( myBook2 ) ;

        String TEXT1 = "text1" ;
        String TEXT2 = "text2" ;

        for( int i = 0; i < returnedBookResults.size( ); i++ )
        {
            Map< String, String > bookMap = new HashMap< String, String >( );
            Book thisBook = returnedBookResults.get( i ) ;

            Log.i("Jacob", "thisBook [ " + i + " ] Title = " + thisBook.getTitle( ) ) ;

            bookMap.put( TEXT1, thisBook.getTitle( ) ) ;
            bookMap.put( TEXT2, thisBook.getAuthor( ) ) ;
            bookResultsTitleAndAuthor.add( bookMap ) ;
        }

        String[ ] mapKey = new String[ ]{ TEXT1, TEXT2 } ;
        int[ ] layoutID = new int[ ] { android.R.id.text1, android.R.id.text2 } ;

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, bookResultsTitleAndAuthor, android.R.layout.simple_list_item_2,
                mapKey, layoutID ) ;

        ListView bookResults = (ListView) findViewById(R.id.list_searched_books);
        bookResults.setAdapter(simpleAdapter);

        bookResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                Toast.makeText(SearchResults.this, "You clicked " + clickItemObj.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        /*
        final Button search = findViewById(R.id.view_book1);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SearchResults.this,ViewBook.class);
                startActivity(i);
                //setContentView(R.layout.activity_add_book);
            }
        });
        */

    }
}
