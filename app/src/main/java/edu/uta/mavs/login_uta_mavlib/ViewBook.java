package edu.uta.mavs.login_uta_mavlib;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        final Book viewedBook  = ( Book ) args.getSerializable("resultsIntent");

        TextView tvTitle = findViewById( R.id.tvTitle );
        TextView tvAuthor = findViewById( R.id.tvAuthor );
        TextView tvISBN = findViewById( R.id.tvISBN );
        TextView tvCategory = findViewById( R.id.tvCategory );

        tvTitle.setText( "Title: " + viewedBook.getTitle( ).toUpperCase( ) );
        tvAuthor.setText( "Author: " + viewedBook.getAuthor( ).toUpperCase( ) );
        tvISBN.setText( "ISBN: " + viewedBook.getIsbn( ) );
        tvCategory.setText( "Category: " + viewedBook.getCategory( ).toUpperCase( ) );

    }
}
