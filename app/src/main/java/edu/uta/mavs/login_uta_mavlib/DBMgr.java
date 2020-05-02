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
import java.util.List;

import edu.uta.mavs.login_uta_mavlib.ui.login.LoginController;


public class DBMgr {

    private static DBMgr single_instance = null;

    private static final String TAG = "DBMgr";

    private FirebaseAuth fAuth;
    private FirebaseFirestore database;
    private CollectionReference studentDb;
    private CollectionReference librarianDb;
    private CollectionReference bookDb;
    private CollectionReference checkoutDb;

    private DBMgr() {
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        studentDb = database.collection("Student");
        librarianDb = database.collection("Librarian");
        bookDb = database.collection("Book");
        checkoutDb = database.collection("Checkout");
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
                        Toast.makeText(aContext, "You are logged in!!", Toast.LENGTH_SHORT).show();
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
        }
        else{
            aContext.startActivity(new Intent(aContext, LoginController.class));
        }
    }


    public void login(String aEmail, String aPass, final Context aContext){
        fAuth.signInWithEmailAndPassword(aEmail,aPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    getLoggedInStatus(aContext);
                }
                else{
                    Toast.makeText(aContext, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void logout(final Context aContext){
        fAuth.signOut();
        aContext.startActivity(new Intent(aContext, LoginController.class));
        Toast.makeText(aContext, "You have been logged out", Toast.LENGTH_SHORT).show();
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


    public void getUser(String userId, final OnGetUserListener listener){
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


    public void getCurrentUser(final OnGetUserListener listener){
        listener.onStart();
        FirebaseUser fUser = fAuth.getCurrentUser();

        if(fUser!=null){
            String lUid = fUser.getUid();
            DocumentReference docIdRef = studentDb.document(lUid);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "onComplete: found user data");
                            Student student = document.toObject(Student.class);
                            listener.onSuccess(student);
                        } else {
                            Log.d(TAG, "onComplete: failed to get user data");
                            listener.onFailure();
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                        listener.onFailure();
                    }
                }
            });
            Log.d(TAG, "getCurrentUser: found User");
        }
    }


    public void storeBook(Book aNewBook, final String aMsg, final Context aContext){
        bookDb.document(aNewBook.getIsbn()).set(aNewBook)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (!aMsg.equals("")) {
                    Toast.makeText(aContext, aMsg, Toast.LENGTH_SHORT).show();
                }
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


    public void getBook(String aIsbn, final OnGetBookListener listener){
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


    public void getBooksAll(final String isbn, final String title, final String author, final String category, final OnGetBooksListener listener){
        listener.onStart();
        bookDb.whereEqualTo("isbn", isbn)
                .whereEqualTo( "title", title )
                .whereEqualTo( "author", author )
                .whereEqualTo( "category", category )
                .get()
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

    public void getBooks(String field1, String field2, String field3, String input1, String input2, String input3, final OnGetBooksListener listener){
        listener.onStart();
        bookDb.whereEqualTo( field1, input1 )
                .whereEqualTo( field2, input2 )
                .whereEqualTo( field3, input3 ).get()
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

    public void getBooks(String field1, String field2, String input1, String input2, final OnGetBooksListener listener){
        listener.onStart();
        bookDb.whereEqualTo( field1, input1)
                .whereEqualTo( field2, input2 ).get()
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

    public void getBooks(String field1,  String input1, final OnGetBooksListener listener){
        listener.onStart();
        bookDb.whereEqualTo( field1, input1 ).get()
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


    public void deleteBook(String aIsbn, final Context aContext){
        deleteBookCheckout(aIsbn);

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


    public void storeCheckout(Checkout aCheckout, final String aMsg, final Context aContext){
        checkoutDb.document("i"+aCheckout.getIsbn()+"u"+aCheckout.getUserId())
                .set(aCheckout).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(aContext, "Book "+aMsg, Toast.LENGTH_SHORT).show();
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


    public void getCheckouts(String aIsbn, String aUserId, final OnGetCheckoutsListener listener){
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


    private void buildCheckedoutBooksList( ArrayList< Checkout > inCheckouts, final OnBuildCheckedoutBooksList listener )
    {
        listener.onStart();

        List< String > ISBNs = new ArrayList<>();

        for ( int i = 0; i < inCheckouts.size( ); i++ )
        {
            ISBNs.add( inCheckouts.get(i).getIsbn( ) ) ;
        }

        bookDb.whereIn( "isbn", ISBNs )
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Book> books = new ArrayList<Book>();
                        for(QueryDocumentSnapshot docSnapshot : queryDocumentSnapshots){
                            Book book = docSnapshot.toObject(Book.class);
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

    public void getResAndCheckedoutBooks( final OnGetResAndCheckedoutBooks listener )
    {
        listener.onStart();

        getCurrentUser(new OnGetUserListener() {
            @Override
            public void onSuccess(final User user) {
                getCheckouts(null, user.getUserId(), new OnGetCheckoutsListener() {
                    @Override
                    public void onSuccess(final ArrayList<Checkout> checkouts) {
                        buildCheckedoutBooksList(checkouts, new OnBuildCheckedoutBooksList() {
                            @Override
                            public void onSuccess(ArrayList<Book> userBooks) {
                                listener.onSuccess( checkouts, userBooks );
                            }
                            @Override
                            public void onStart() {
                            }
                            @Override
                            public void onFailure() {
                            }
                        });
                    }
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onFailure() {
                    }
                });
            }
            @Override
            public void onStart() {
            }
            @Override
            public void onFailure() {
            }
        });
    }


    public void getCheckout(String aIsbn, String aUserId, final OnGetCheckoutListener listener){
        listener.onStart();

        DocumentReference docIdRef = checkoutDb.document("i"+aIsbn+"u"+aUserId);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Checkout checkout = document.toObject(Checkout.class);
                        listener.onSuccess(checkout);
                    } else {
                        listener.onFailure();
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                    listener.onFailure();
                }
            }
        });
    }


    private void deleteBookCheckout(String aIsbn){

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


    public void deleteCheckout(String aIsbn, String aUserId){

        checkoutDb.document("i"+aIsbn+"u"+aUserId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Checkout successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Log.w("TAG", "Error deleting checkout", e);
                    }
                });
    }


}
