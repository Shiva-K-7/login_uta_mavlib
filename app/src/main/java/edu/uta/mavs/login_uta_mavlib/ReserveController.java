package edu.uta.mavs.login_uta_mavlib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReserveController extends AppCompatActivity {

    private static final String TAG = "ReserveController";

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
        final Button reserve = findViewById(R.id.reserve_button);

        tvTitle.setText( "Title: " + viewedBook.getTitle( ).toUpperCase( ) );
        tvAuthor.setText( "Author: " + viewedBook.getAuthor( ).toUpperCase( ) );
        tvISBN.setText( "ISBN: " + viewedBook.getIsbn( ) );
        tvCategory.setText( "Category: " + viewedBook.getCategory( ).toUpperCase( ) );


        reserve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                final String ISBN = viewedBook.getIsbn();
                final DBMgr dbMgr = DBMgr.getInstance();

                dbMgr.getCurrentUser(new OnGetUserListener() {
                    @Override
                    public void onSuccess(User user) {
                        final String SID = user.getUserId();

                        dbMgr.getCheckout(ISBN, SID, new OnGetCheckoutListener() {
                            @Override
                            public void onSuccess(Checkout checkout) { //already reserved
                                Log.d(TAG, "already reserved book");
                                Toast.makeText(ReserveController.this, "Book already reserved", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onStart() {
                                Log.d(TAG, "onStart: getCheckout");
                            }
                            @Override
                            public void onFailure() { //new reservation
                                if (viewedBook.checkAvailability()) {
                                    Checkout newReservation = new Checkout(SID, ISBN, false);
                                    dbMgr.storeCheckout(newReservation,"Reserved", ReserveController.this);
                                    viewedBook.reduceAvailabilityCount();
                                    dbMgr.storeBook(viewedBook, "", ReserveController.this);
                                }
                                else{
                                    Toast.makeText(ReserveController.this, "No Copies available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    @Override
                    public void onStart() {
                        Log.d(TAG, "onStart: getUser");
                    }
                    @Override
                    public void onFailure() {
                        Log.d(TAG, "onFailure: couldn't retrieve user");
                    }
                });
            }
        });

    }
}
