package edu.uta.mavs.login_uta_mavlib;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class DBMgr {

    private static DBMgr single_instance = null;

    private static final String TAG = "DBMgr";

    private FirebaseAuth fAuth;
    private FirebaseFirestore database;
    private CollectionReference studentDb;
    private CollectionReference librarianDb;
    private CollectionReference bookDb;
    private CollectionReference checkoutDb;
    private CollectionReference reservationDb;

    public DBMgr() {
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        studentDb = database.collection("Student");
        librarianDb = database.collection("Librarian");
        bookDb = database.collection("Book");
        checkoutDb = database.collection("Checkout");
        reservationDb = database.collection("Reservation");
    }

    //Singleton Database Manager
    public static DBMgr getInstance(){
        if (single_instance == null)
            single_instance = new DBMgr();
        return single_instance;
    }

    public void getLoggedInStatus(final Context aContext){
        FirebaseUser fUser = fAuth.getCurrentUser();

        if(fUser!=null){
            String lUid = fUser.getUid();
            DocumentReference docIdRef = librarianDb.document(lUid);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            aContext.startActivity(new Intent(aContext, LibrarianMenu.class));
                        } else {
                            aContext.startActivity(new Intent(aContext, StudentMenu.class));
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });
            Toast.makeText(aContext, "You are logged in!!", Toast.LENGTH_SHORT).show();
        }
    }


    public void login(final String aEmail, final String aPass, final Context aContext){
        fAuth.signInWithEmailAndPassword(aEmail,aPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(aContext, "Login Successful!", Toast.LENGTH_SHORT).show();
                    getLoggedInStatus(aContext);
                }
                else{
                    Toast.makeText(aContext, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void storeStudent(final Student aNewStudent, final Context aContext){
        fAuth.createUserWithEmailAndPassword(aNewStudent.getUserEmail(),aNewStudent.getUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful()){
                    Toast.makeText(aContext, "User Created", Toast.LENGTH_SHORT).show();
                    final String userID = fAuth.getCurrentUser().getUid();
                    DocumentReference dr = studentDb.document(userID);

                    dr.set(aNewStudent).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG" ,"onSuccess: User profile is created for "+userID);
                        }
                    });
                }
                else{
                    Toast.makeText(aContext, "Error..!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void getUser(final String userId, final OnGetUserListener listener){
        listener.onStart();
        studentDb.whereEqualTo("userId", userId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int userCount = 0;
                        Student student = null;
                        for(QueryDocumentSnapshot docSnapshot : queryDocumentSnapshots){
                            student = docSnapshot.toObject(Student.class);
                            userCount ++;
                        }
                        if(userCount>1)
                            listener.onFailure();
                        else
                            listener.onSuccess(student);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());
                listener.onFailure();
            }
        });
    }


    public void storeBook(final Book aNewBook, final Context aContext){
        bookDb.document(aNewBook.getIsbn()).set(aNewBook)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(aContext, "Book is added to the Database", Toast.LENGTH_SHORT).show();
                Log.d("TAG" ,"onSuccess: Book is added to DB");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(aContext,"Error"+error,Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getBook(final String aIsbn, final OnGetBookListener listener){
        listener.onStart();
        DocumentReference docIdRef = bookDb.document(aIsbn);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Book book = document.toObject(Book.class);
                        listener.onSuccess(book);
                    } else {
                        listener.onFailure();
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    public void getBooks(String isbn, String title, String author, String category, final OnGetBooksListener listener){
        listener.onStart();
        bookDb.whereEqualTo("isbn", isbn).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Book> books = new ArrayList<Book>();
                        for(QueryDocumentSnapshot docSnapshot : queryDocumentSnapshots){
                            Book book = docSnapshot.toObject(Book.class); //Error
                            books.add(book);
                        }
                        listener.onSuccess(books);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());
            }
        });
    }


    public void deleteBook(final String aIsbn, final Context aContext){
        deleteCheckout(aIsbn);
        deleteReservation(aIsbn);

        bookDb.document(aIsbn)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(aContext, "Book is deleted from the Database", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "Book successfully deleted!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(aContext,"Error"+error,Toast.LENGTH_SHORT).show();
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }


    public void storeCheckout(final Checkout aCheckout, final Context aContext){
        deleteReservation(aCheckout.getIsbn());
        //todo - maybe not delete all reservations
        checkoutDb.add(aCheckout).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(aContext, "Book Checked Out", Toast.LENGTH_SHORT).show();
                Log.d("TAG" ,"onSuccess: Checkout created");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(aContext,"Error"+error,Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }


    public void getCheckout(String aIsbn, String aUserId, final OnGetCheckoutListener listener){
        listener.onStart();
        String searchField = "";
        String searchParam = "";
        if (aIsbn != null) {
            searchField = "isbn";
            searchParam = aIsbn;
        }
        else if (aUserId != null) {
            searchField = "userId";
            searchParam = aUserId;
        }

        checkoutDb.whereEqualTo(searchField, searchParam).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Checkout> checkouts = new ArrayList<Checkout>();
                        for(QueryDocumentSnapshot docSnapshot : queryDocumentSnapshots){
                            Checkout checkout = docSnapshot.toObject(Checkout.class);
                            checkouts.add(checkout);
                        }
                        listener.onSuccess(checkouts);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());
                listener.onFailure();
            }
        });
    }


    public void deleteCheckout(String aIsbn){

        checkoutDb.whereEqualTo("isbn", aIsbn).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot docSnapshot : queryDocumentSnapshots){
                            checkoutDb.document(docSnapshot.getId())
                                    .delete().addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, e.toString());
                                }
                            });

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());
            }
        });
    }

    public void storeReservation(final Reservation aReservation, final Context aContext){
        reservationDb.add(aReservation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(aContext, "Book Reserved", Toast.LENGTH_SHORT).show();
                Log.d("TAG" ,"onSuccess: Reservation created");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(aContext,"Error"+error,Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }


    public void getReservation(String aIsbn, String aUserId, final OnGetReservationListener listener){
        listener.onStart();
        String searchField = "";
        String searchParam = "";
        if (aIsbn != null) {
            searchField = "isbn";
            searchParam = aIsbn;
        }
        else if (aUserId != null) {
            searchField = "userId";
            searchParam = aUserId;
        }

        checkoutDb.whereEqualTo(searchField, searchParam).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
                        for(QueryDocumentSnapshot docSnapshot : queryDocumentSnapshots){
                            Reservation reservation = docSnapshot.toObject(Reservation.class);
                            reservations.add(reservation);
                        }
                        listener.onSuccess(reservations);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());
                listener.onFailure();
            }
        });
    }


    public void deleteReservation(String aIsbn){

        reservationDb.whereEqualTo("isbn", aIsbn).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot docSnapshot : queryDocumentSnapshots){
                            reservationDb.document(docSnapshot.getId())
                                    .delete().addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, e.toString());
                                }
                            });

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());
            }
        });
    }

}
