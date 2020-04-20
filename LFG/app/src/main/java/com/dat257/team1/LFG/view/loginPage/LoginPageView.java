package com.dat257.team1.LFG.view.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.MainActivity;
import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.events.RegisterEvent;
import com.dat257.team1.LFG.viewmodel.LoginPageViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is related to the login page that is responsible for
 * authenticating the user's data from the database.
 *
 * @author : Oussama Anadani, Johan Ek, Jakobew
 */
public class LoginPageView extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button loginButton;
    private EditText passwordField, emailField;

    LoginPageViewModel loginPageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        LoginPagerAdapter loginPagerAdapter = new LoginPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(loginPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);

        loginPageViewModel = new ViewModelProvider(this).get(LoginPageViewModel.class);
        getLifecycle().addObserver(loginPageViewModel);
        loginPageViewModel.onCreate();
        //TODO
        initiateComponents();
        //loginButtonListener();
    }



    /**
     * A method that has the loginButtonListener that calls the loginUser method
     * after checking if the user has entered a validate email and password.
     */
    private void loginButtonListener() {
        loginButton = findViewById(R.id.sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailField = findViewById(R.id.sign_in_email);
                String email = emailField.getText().toString().trim();
                passwordField = findViewById(R.id.sign_in_pwd);
                String password = passwordField.getText().toString().trim();
                //checking if the user has entered a validate form of the email and password
                if (validateForm(email, password))
                    loginUser(email, password);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is already registered (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        retrieveData(currentUser);
    }

    /**
     * A method that initiates all the view components
     */
    private void initiateComponents() {

        //TODO
        mAuth = FirebaseAuth.getInstance();
        //loginButton = findViewById(R.id.LLoginButton);
        //passwordField = findViewById(R.id.LPasswordField);
        //emailField = findViewById(R.id.LEmailField);
            /*
        createButton = (Button) findViewById(R.id.reg_enterButton);
        emailField = (TextInputLayout) findViewById(R.id.regEmailField);
        nameField = (TextInputLayout) findViewById(R.id.regNameField);
        passField = (TextInputLayout) findViewById(R.id.regPassField);
        phoneField = (TextInputLayout) findViewById(R.id.regPhoneField);
        termsBox = (CheckBox) findViewById(R.id.reg_terms);
    }*/
    }

    /**
     * Method that creates a account for a user using a email and a password and then creates a
     * corresponding document in the db with the users email and name stored.
     * If the creation of the db document fails the users is deleted since that is a part of the account
     * creation and has to happen to prevent "ghost" users. (should have a better fix in the future)
     * <p>
     * Author: Johan Ek
     *
     * @param email    the email address for the account
     * @param password the password to the account
     * @param name     the name of the user registering the account
     */
    private void registerUser(final String email, final String password, final String name,
                              final String phone) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> userData = new HashMap<>();
                            final FirebaseUser user = mAuth.getCurrentUser();
                            userData.put("name", name);
                            userData.put("email", email);
                            userData.put("friends", null);
                            userData.put("phoneNumber", phone);
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
                                            if (task.isSuccessful()) {
                                                EventBus.getDefault().post(new RegisterEvent(false));
                                            }
                                            //help edge case no data for user in the db but the user is still
                                            // registered what should we do???
                                        }
                                    });
                                }
                            });
                        } else {
                            EventBus.getDefault().post(new RegisterEvent(false));
                        }
                    }
                });
    }

    /**
     * A method to check the validation of the password and the email.
     *
     * @param email    The email that the user has entered.
     * @param password The password that the user has entered.
     * @return True if the form is validate and false otherwise.
     */
    private Boolean validateForm(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            emailField.setError("Email is required!");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Password is required!");
            return false;
        }
        if (password.length() < 6) {
            passwordField.setError("Password is too short!");
            return false;
        }
        return true;
    }


    /**
     * The login method that is connected to the LoginButton which authenticates
     * with the database and gets the user's corresponding data depending on
     * the user's password and email.
     *
     * @param email    The user's inserted email
     * @param password The user's inserted password
     */
    public void loginUser(final String email, final String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginPageView.this, "Logged in successfully! ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            retrieveData(user);
                            //take the user to the main page after successfully retrieving the data
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else { // If sign in fails, display a message to the user.
                            Toast.makeText(LoginPageView.this, "Login failed! ", Toast.LENGTH_SHORT).show();
                            //   retrieveData(null);
                        }

                    }
                });
    }

    /**
     * A method to retrieve the exciting user's data from the database
     *
     * @param currentUser The current user who's successfully logged in.
     */
    private void retrieveData(FirebaseUser currentUser) {
        if (currentUser != null) {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            DocumentReference documentReference = firestore.collection("users").document(currentUser.getUid());
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    //todo retrieving the data
                    /*
                    fullName.setText(documentSnapshot.getString("name"));
                    email.setText(documentSnapshot.getString("email"));
                   // get the friendList, this should be in a recyclerView form
                    */

                }
            });

        }
    }
}