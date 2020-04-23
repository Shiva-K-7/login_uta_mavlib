package edu.uta.mavs.login_uta_mavlib;

import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteBook extends AppCompatActivity {

    Book1 b;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);

        b= new Book1();
        final Button delete = findViewById(R.id.button3);
        final TextInputEditText isbn = findViewById(R.id.isbn);
        final TextInputEditText quantity = findViewById(R.id.quantity);
        final FirebaseFirestore database = FirebaseFirestore.getInstance();



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ISBN = isbn.getText().toString().trim();
                String q = quantity.getText().toString().trim();

                if(TextUtils.isEmpty(ISBN))
                {
                    isbn.setError("ISBN is required.");
                    return;
                }

                b.setIsbn(ISBN);

        database.collection("Book").document(b.getIsbn())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DeleteBook.this, "Book is deleted from the Database", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "Book successfully deleted!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(DeleteBook.this,"Error"+error,Toast.LENGTH_SHORT).show();
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
            }
        });


    }
}
