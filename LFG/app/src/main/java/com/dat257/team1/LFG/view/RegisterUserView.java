package com.dat257.team1.LFG.view;

import android.content.Context;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterUserView extends AppCompatActivity {


    public RegisterUserView() {
        super();
    }

    /**
     * Method that creates a account for a user using a email and a password and then creates a
     * corresponding document in the db with the users email and name stored.
     *If the creation of the db document fails the users is deleted since that is a part of the account
     * creation and has to happen to prevent "ghost" users. (should have a better fix in the future)
     *
     * Author: Johan Ek
     * @param email the email address for the account
     * @param password the password to the account
     * @param name the name of the user registering the account
     */
    public void registerUser(final String email,final String password, final String name){
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Map<String,Object> userData = new HashMap<>();
                            final FirebaseUser user = mAuth.getCurrentUser();
                            userData.put("name",name);
                            userData.put("email",email);
                            userData.put("friends",null);
                            FirebaseFirestore.getInstance().collection("users").document(user.getUid()).set(userData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //should prob send success event
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                //send event to view telling it that the creation failed
                                            }
                                            //help
                                        }
                                    });
                                }
                            });
                        }else{
                            //handle failure could maybe be handled by sending a event to the waiting
                            // view telling it that there was a error
                        }
                    }
                });
    }
}
