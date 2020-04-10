package edu.uta.mavs.login_uta_mavlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        final Button search = findViewById(R.id.view_book1);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SearchResults.this,ViewBook.class);
                startActivity(i);
                //setContentView(R.layout.activity_add_book);
            }
        });

    }
}
