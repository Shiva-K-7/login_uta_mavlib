package edu.uta.mavs.login_uta_mavlib;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class CheckinController extends AppCompatActivity {

    private static final String TAG = "CheckinController";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_books);

        final Button checkIn = findViewById(R.id.checkin_books);
        final TextInputEditText isbn = findViewById(R.id.isbn);
        final TextInputEditText sid = findViewById(R.id.student_id);

        checkIn.setOnClickListener(new View.OnClickListener() {
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
                                dbMgr.deleteCheckout(ISBN,SID);
                                book.increaseAvailabilityCount(true, false);
                                dbMgr.storeBook(book, CheckinController.this);
                            }
                            @Override
                            public void onStart() {
                                Log.d(TAG, "onStart: getBook");
                            }
                            @Override
                            public void onFailure() {
                                Log.d(TAG, "onFailure: getBook");
                                Toast.makeText(CheckinController.this, "Book doesn't exist", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(CheckinController.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
