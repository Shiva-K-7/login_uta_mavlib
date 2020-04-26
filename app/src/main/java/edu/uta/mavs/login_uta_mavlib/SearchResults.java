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

    List<Map<String, String>> bookResultsTitleAndAuthor = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        final ArrayList<Book> returnedBookResults = (ArrayList<Book>) args.getSerializable("searchIntent");

        Log.i(SearchResultsLog, "Starting Display of Search Results");

        String TEXT1 = "text1" ;
        String TEXT2 = "text2" ;

        for( int i = 0; i < returnedBookResults.size( ); i++ )
        {
            Map< String, String > bookMap = new HashMap< >();
            Book thisBook = returnedBookResults.get( i ) ;

            bookMap.put( TEXT1, thisBook.getTitle( ).toUpperCase( ) ) ;
            bookMap.put( TEXT2, thisBook.getAuthor( ).toUpperCase( ) ) ;
            bookResultsTitleAndAuthor.add( bookMap ) ;
        }

        String[ ] mapKey = new String[ ]{ TEXT1, TEXT2 } ;
        int[ ] layoutID = new int[ ] { android.R.id.text1, android.R.id.text2 } ;

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, bookResultsTitleAndAuthor, android.R.layout.simple_list_item_2,
                mapKey, layoutID ) ;

        final ListView bookResults = findViewById(R.id.list_searched_books);
        bookResults.setAdapter(simpleAdapter);

        bookResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);

                Book myBook = returnedBookResults.get( index ) ;

                Intent intent = new Intent(SearchResults.this, ViewBook.class);
                Bundle args = new Bundle();
                args.putSerializable("resultsIntent", myBook );
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });
    }
}
