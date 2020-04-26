package edu.uta.mavs.login_uta_mavlib;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CheckoutController extends AppCompatActivity {

    private static final String TAG = "Checkout";
    private Book checkoutBook;

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

                dbMgr.getUser(SID, new OnGetUserListener() {
                    @Override
                    public void onSuccess(User user) {
                        dbMgr.getBook(ISBN, new OnGetBookListener() {
                            @Override
                            public void onSuccess(Book book) {
                                checkoutBook = book;
                                dbMgr.getCheckout(ISBN, SID, new OnGetCheckoutListener() {
                                    @Override
                                    public void onSuccess(Checkout checkout) { //checkout reserved book
                                        Log.d(TAG, "onSuccess: checkouts got");
                                        checkout.setDates();
                                        dbMgr.storeCheckout(checkout,"Checked Out", CheckoutController.this);
                                    }
                                    @Override
                                    public void onStart() {
                                        Log.d(TAG, "onStart: getCheckout");
                                    }
                                    @Override
                                    public void onFailure() { //book not reserved
                                        dbMgr.getCheckouts(null, SID, new OnGetCheckoutsListener() {
                                            @Override
                                            public void onSuccess(ArrayList<Checkout> checkouts) {
                                                if (checkouts.size() < 5) {
                                                    if (checkoutBook.checkAvailability()) {
                                                        Checkout newCheckout = new Checkout(SID, ISBN, true);
                                                        dbMgr.storeCheckout(newCheckout, "Checked Out", CheckoutController.this);
                                                        checkoutBook.reduceAvailabilityCount();
                                                        dbMgr.storeBook(checkoutBook, "", CheckoutController.this);
                                                    } else {
                                                        Toast.makeText(CheckoutController.this, "No Copies available", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(CheckoutController.this, "Student cannot checkout any more books", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            @Override
                                            public void onStart() {
                                                Log.d(TAG, "onStart: Checking number of user checkouts");
                                            }
                                            @Override
                                            public void onFailure() {
                                                Log.d(TAG, "onFailure: failed to get checkouts");
                                            }
                                        });
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
        });

    }
}
