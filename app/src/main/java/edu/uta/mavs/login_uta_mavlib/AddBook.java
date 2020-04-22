package edu.uta.mavs.login_uta_mavlib;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBook extends AppCompatActivity {

    Book book;
    DatabaseReference DR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);


        final Button addbook = findViewById(R.id.add_books);
        final TextInputEditText isbn = findViewById(R.id.isbn);
        final TextInputEditText title = findViewById(R.id.book_title);
        final TextInputEditText author = findViewById(R.id.author);
        final TextInputEditText category = findViewById(R.id.category);
        final TextInputEditText total = findViewById(R.id.total);
        total.setInputType(InputType.TYPE_CLASS_NUMBER);
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        DR= FirebaseDatabase.getInstance().getReference().child("Book");
        book = new Book();

        addbook.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String ISBN = isbn.getText().toString().trim();
                final String TITLE = title.getText().toString().trim();
                final String AUTHOR = author.getText().toString().trim();
                final String CATEGORY = category.getText().toString().trim();
                final String TOTAL = total.getText().toString().trim();

                if(TextUtils.isEmpty(ISBN))
                {
                    isbn.setError("ISBN is required.");
                    return;
                }
                if(TextUtils.isEmpty(TITLE))
                {
                     title.setError("BOOK TITLE is required.");
                     return;
                }
                if(TextUtils.isEmpty(AUTHOR))
                {
                    author.setError("AUTHOR NAME is required.");
                    return;
                }
                if(TextUtils.isEmpty(CATEGORY))
                {
                    category.setError("CATEGORY is required.");
                    return;
                }
                if(TextUtils.isEmpty(TOTAL))
                {
                    total.setError("Give the total number of books to be added.");
                    return;
                }

                /*(book.setIsbn(ISBN);
                book.setTitle(TITLE);
                book.setAuthor(AUTHOR);
                book.setCategory(CATEGORY);
                book.setTotal(TOTAL);

                DR.push().setValue(book);

                Toast.makeText(AddBook.this,"book added successfully",Toast.LENGTH_SHORT).show();
*/


                Map<String,Object> Book = new HashMap<>();

                Book.put("isbn", ISBN);
                Book.put("title", TITLE);
                Book.put("author", AUTHOR);
                Book.put("category", CATEGORY);
                Book.put("total", Integer.parseInt(TOTAL));
                Book.put("numIssued",0);
                Book.put("numReserved",0);

                database.collection("Book").add(Book).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddBook.this, "Book is added to the Database", Toast.LENGTH_SHORT).show();
                        Log.d("TAG" ,"onSuccess: Book is added to DB");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(AddBook.this,"Error"+error,Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}