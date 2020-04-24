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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class DBMgr {

    private static DBMgr single_instance = null;

    private FirebaseAuth fAuth;
    private FirebaseFirestore database;

    public DBMgr() {
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

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
            DocumentReference docIdRef = database.collection("Librarian").document(lUid);
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
                        Log.d("DBMgr", "Failed with: ", task.getException());
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
                    DocumentReference dr = database.collection("Student").document(userID);

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

    }


    public void storeBook(final Book aNewBook, final Context aContext){
        database.collection("Book").document(aNewBook.getIsbn()).set(aNewBook)
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
        DocumentReference docIdRef = database.collection("Book").document(aIsbn);
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
                    Log.d("DBMgr", "Failed with: ", task.getException());
                }
            }
        });
    }


    public void deleteBook(final String aIsbn, final Context aContext){
        //todo will also need to delete all reservation and checkout objects for isbn here

        database.collection("Book").document(aIsbn)
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


}
