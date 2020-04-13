package com.dat257.team1.LFG.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dat257.team1.LFG.Events.RegisterEvent;
import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.viewmodel.FindActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The class representing the registration view that allows the user to register a account using a
 * email and password.
 *
 * Author: Johan Ek
 */
public class RegisterUserView extends AppCompatActivity {
    private Button createButton;
    private TextInputLayout emailField;
    private TextInputLayout nameField;
    private TextInputLayout passField;
    private TextInputLayout phoneField;
    private CheckBox termsBox;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        createButton = (Button) findViewById(R.id.reg_enterButton);
        emailField = (TextInputLayout) findViewById(R.id.regEmailField);
        nameField = (TextInputLayout) findViewById(R.id.regNameField);
        passField = (TextInputLayout) findViewById(R.id.regPassField);
        phoneField = (TextInputLayout) findViewById(R.id.regPhoneField);
        termsBox = (CheckBox) findViewById(R.id.reg_terms);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCreateUser();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
    private void registerUser(final String email,final String password, final String name){
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
                                            EventBus.getDefault().post(new RegisterEvent(true));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                EventBus.getDefault().post(new RegisterEvent(false));
                                            }
                                            //help edge case no data for user in the db but the user is still
                                            // registered what should we do???
                                        }
                                    });
                                }
                            });
                        }else{
                            EventBus.getDefault().post(new RegisterEvent(false));
                        }
                    }
                });
    }

    /**
     * Method that is called when the user clicks create account. It takes the data from the text
     * field and first checks if the data is correct and then if it is correct it calls the method
     * registerUser. If something is wrong with the data provided by the user a toast message
     * alerts the user of this.
     *
     * Author: Johan Ek
     */
    private void handleCreateUser(){
        String email = emailField.getEditText().getText().toString(),
                name = nameField.getEditText().getText().toString(),
        phone = phoneField.getEditText().getText().toString(), pass = passField.getEditText().getText().toString();
        String toastMessage;




        if(!email.contains("@")){
            toastMessage = "The email has a incorrct format";
        }else if(name.equals("")){
            toastMessage = "You have to specify a name to create a account";
        }else if(pass.length() <= 6){
            toastMessage = "Your password must contain more than 6 character";
        }else if(phone.equals("")){
            toastMessage = "You have to specify a phone number to create a account";
        }else if(!termsBox.isChecked()){
            toastMessage = "You have to accept the terms and conditions to create a account";
        }else{
            registerUser(email,pass,name);
            return;
        }

        Toast toast = Toast.makeText(getApplicationContext(),toastMessage,Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Method that handles the result of the registration of the user. If the registration fails
     * then a toast message is displayed to the user alerting them about this failure. However if
     * the creation was a success the view is changed to the feed of activities.
     *
     * Author: Johan Ek
     * @param event the register event containing information about the result of the registration
     */
    @Subscribe
    public void onRegisterEvent(RegisterEvent event){
        if(event.isSuccess()){
            Intent intent = new Intent(this, FindActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Something went wrong in the account creation",Toast.LENGTH_SHORT).show();
        }
    }
}
