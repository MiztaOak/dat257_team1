package com.dat257.team1.LFG.view.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dat257.team1.LFG.MainActivity;
import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.ForgetPasswordView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;


/**
 * A simple login on an account fragment
 *
 * @author : Jakobew, Oussama Anadani, Johan Ek
 */
public class LoginFragment extends Fragment {

    private Button loginButton;
    private EditText passwordField, emailField;

    private TextView forgetPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.login_fragment, container, false);

        loginButton = rootView.findViewById(R.id.sign_in_button);
        forgetPassword = rootView.findViewById(R.id.forgot_pwd_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailField = rootView.findViewById(R.id.sign_in_email);
                String email = emailField.getText().toString().trim();
                passwordField = rootView.findViewById(R.id.sign_in_pwd);
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
      
        return rootView;
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
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getActivity(), "Logged in successfully! ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            retrieveData(user);
                            //take the user to the main page after successfully retrieving the data
                            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                        } else { // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), "Login failed! ", Toast.LENGTH_SHORT).show();
                            //   retrieveData(null);
                        }

                    }
                });
    }

    public void openForgotPassword(){

        startActivity(new Intent(getActivity().getApplicationContext(), ForgetPasswordView.class));
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
            documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
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
