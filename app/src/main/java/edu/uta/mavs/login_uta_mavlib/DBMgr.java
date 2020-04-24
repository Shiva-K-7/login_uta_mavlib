package edu.uta.mavs.login_uta_mavlib;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;




public class DBMgr {

    private static DBMgr single_instance = null;

    private final FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();



    public DBMgr() {}

    //Singleton Database Manager
    public static DBMgr getInstance(){
        if (single_instance == null)
            single_instance = new DBMgr();
        return single_instance;
    }

    public boolean getLoggedInStatus(final Context aContext){
        if(fAuth.getCurrentUser()!=null){
            Toast.makeText(aContext, "You are logged in!!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void storeStudent(final Student aNewStudent, final Context aContext){
        fAuth.createUserWithEmailAndPassword(aNewStudent.getEmail(),aNewStudent.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful()){
                    Toast.makeText(aContext, "User Created", Toast.LENGTH_SHORT).show();
                    final String userID = fAuth.getCurrentUser().getUid();
                    DocumentReference dr = database.collection("Student").document(userID);
                    Map<String,Object> user = new HashMap<>();

                    user.put("userId", aNewStudent.getId());
                    user.put("userFName", aNewStudent.getFName());
                    user.put("userLName", aNewStudent.getLName());
                    user.put("userEmail", aNewStudent.getEmail());
                    user.put("userPassword", aNewStudent.getPassword());

                    dr.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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




}
