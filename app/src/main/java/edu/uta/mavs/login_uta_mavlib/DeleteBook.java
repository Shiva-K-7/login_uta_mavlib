package edu.uta.mavs.login_uta_mavlib;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteBook extends AppCompatActivity {

    Book b;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);

        b= new Book();
        final Button delete = findViewById(R.id.button3);
        final TextInputEditText isbn = findViewById(R.id.isbn);
        final TextInputEditText quantity = findViewById(R.id.quantity);
        final FirebaseFirestore database = FirebaseFirestore.getInstance();


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ISBN = isbn.getText().toString().trim();
                String q = quantity.getText().toString().trim();
                b.setIsbn(ISBN);

        database.collection("Book").document(b.getIsbn())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Book with "+ISBN+" successfully deleted!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
            }
        });


    }
}
