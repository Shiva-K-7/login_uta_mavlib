package edu.uta.mavs.login_uta_mavlib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResults extends AppCompatActivity {

    String SearchResultsLog = "SearchResults Log" ;

    //ListView bookResults;
    //ArrayList<String> returnedBookResults = new ArrayList<>();
    ArrayList<Book> returnedBookResults = new ArrayList<>();

    List<Map<String, String>> bookResultsTitleAndAuthor = new ArrayList<Map<String, String>>();
    DBMgr searchBooksDBManager ;

    //Book myBook = new Book("1234893234", "Design Patterns", "David Kung", "software", 4);
    //Book myBook2 = new Book( "12348933234", "Call of the Wild", "Jack London", "fiction", 5 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Book> returnedBookResults = (ArrayList<Book>) args.getSerializable("searchIntent");

        Log.i(SearchResultsLog, "Starting Display of Search Results");

        String TEXT1 = "text1" ;
        String TEXT2 = "text2" ;

        for( int i = 0; i < returnedBookResults.size( ); i++ )
        {
            Map< String, String > bookMap = new HashMap< String, String >( );
            Book thisBook = returnedBookResults.get( i ) ;

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
