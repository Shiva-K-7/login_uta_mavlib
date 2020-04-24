package edu.uta.mavs.login_uta_mavlib;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class DeleteBookController extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);

        final Button delete = findViewById(R.id.button3);
        final TextInputEditText isbn = findViewById(R.id.isbn);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ISBN = isbn.getText().toString().trim();

                if(TextUtils.isEmpty(ISBN))
                {
                    isbn.setError("ISBN is required.");
                    return;
                }

                DBMgr dbMgr = DBMgr.getInstance();

                dbMgr.deleteBook(ISBN, DeleteBookController.this);

            }
        });


    }
}
