package edu.uta.mavs.login_uta_mavlib;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class CheckoutController extends AppCompatActivity {

    private static final String TAG = "Checkout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_books);

        final Button checkout = findViewById(R.id.button3);
        final TextInputEditText isbn = findViewById(R.id.isbn);
        final TextInputEditText sid = findViewById(R.id.student_id);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ISBN = isbn.getText().toString().trim();
                final String SID = sid.getText().toString().trim();

                if(TextUtils.isEmpty(ISBN))
                {
                    isbn.setError("ISBN is required.");
                    return;
                }
                if(TextUtils.isEmpty(SID))
                {
                    sid.setError("Student ID is required.");
                    return;
                }

                final DBMgr dbMgr = DBMgr.getInstance();


                dbMgr.getBook(ISBN, new OnGetBookListener() {
                    @Override
                    public void onSuccess(Book book) {
                        dbMgr.getUser(SID, new OnGetUserListener() {
                            @Override
                            public void onSuccess(User user) {
                                Checkout newCheckout = new Checkout(ISBN, SID);
                                dbMgr.storeCheckout(newCheckout, CheckoutController.this);
                            }
                            @Override
                            public void onStart() {
                                Log.d(TAG, "onStart: getUser");
                            }
                            @Override
                            public void onFailure() {
                                Log.d(TAG, "onFailure: getUser");
                                Toast.makeText(CheckoutController.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onStart() {
                        Log.d(TAG, "onStart: getBook");
                    }
                    @Override
                    public void onFailure() {
                        Log.d(TAG, "onFailure: getBook");
                        Toast.makeText(CheckoutController.this, "Book doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
