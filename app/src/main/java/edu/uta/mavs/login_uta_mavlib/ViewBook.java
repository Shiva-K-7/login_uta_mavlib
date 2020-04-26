package edu.uta.mavs.login_uta_mavlib;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ViewBook extends AppCompatActivity {

    private TextView book_author, book_isbn, book_description, book_title;
    private Button reserve_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        Book rbook = (Book) getIntent().getSerializableExtra("returned_book");


        book_title = findViewById(R.id.titile_of_the_book);
        book_author = findViewById(R.id.author_of_the_book);
        book_isbn = findViewById(R.id.isbn_of_the_book);
        book_description = findViewById(R.id.description_of_the_book);

        book_title.setText("Tile: " + rbook.getTitle());
        book_author.setText("Author: " + rbook.getAuthor());
        book_isbn.setText("ISBN: " + rbook.getIsbn());
        book_description.setText("Category: " + rbook.getCategory());



    }


}
