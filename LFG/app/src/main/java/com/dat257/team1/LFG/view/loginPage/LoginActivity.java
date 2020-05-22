package com.dat257.team1.LFG.view.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dat257.team1.LFG.MainActivity;
import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.view.CreateActFragment;
import com.dat257.team1.LFG.view.ForgetPasswordView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * A simple login on an account fragment
 *
 * @author : Jakobew, Oussama Anadani, Johan Ek, gabjav
 */
public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = CreateActFragment.class.getSimpleName();
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    private Button loginButton;
    private EditText passwordField, emailField;
    private TextView forgetPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);
        loginButton = findViewById(R.id.sign_in_button);
        forgetPassword = findViewById(R.id.lfForgotPass);
        //  googleButton = rootView.findViewById(R.id.quick_access_google);

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

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotPassword();
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }





    /**
     * Check for existing Google Sign In account, if the user is already signed in
     * the GoogleSignInAccount will be non-null.
     */

    /*public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();

    }*/


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
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  // Sign in success, update UI with the signed-in user's information
                            FireStoreHelper.getInstance();
                            Toast.makeText(getApplicationContext(), "Logged in successfully! ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            FireStoreHelper.getInstance().retrieveData(user);
                            //take the user to the main page after successfully retrieving the data
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else { // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Login failed! ", Toast.LENGTH_SHORT).show();
                            //   retrieveData(null);
                        }

                    }
                });
    }

    public void openForgotPassword() {
        Log.d(LOG_TAG, "Pwd forgotten");
        startActivity(new Intent(this.getApplicationContext(), ForgetPasswordView.class));
    }


}
