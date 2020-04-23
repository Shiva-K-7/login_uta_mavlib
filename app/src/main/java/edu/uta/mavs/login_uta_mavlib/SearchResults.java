package edu.uta.mavs.login_uta_mavlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchResults extends AppCompatActivity {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference cr = database.collection("Book");
    private ListView book_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        book_list = findViewById(R.id.list_books);

        /*cr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot DocumentSnapshots) {
                    if(DocumentSnapshots.exists()){


                    }else{
                        Toast.makeText(SearchResults.this, "Book does not exist", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "Book does not exist!");
                    }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });*/

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
