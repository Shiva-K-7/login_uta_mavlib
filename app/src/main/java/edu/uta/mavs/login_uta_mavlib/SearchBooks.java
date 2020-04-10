package edu.uta.mavs.login_uta_mavlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);

        final Button search = findViewById(R.id.button5);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SearchBooks.this,SearchResults.class);
                startActivity(i);
                //setContentView(R.layout.activity_add_book);
            }
        });

       /* ListView lsitView = (ListView) findViewById(R.id.list_view_search_books);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Software Engineering");
        arrayList.add("Theory of Computation");
        arrayList.add("Design Patterns");
        arrayList.add("Artificial Intelligence");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        lsitView.setAdapter(arrayAdapter);*/
    }
}
