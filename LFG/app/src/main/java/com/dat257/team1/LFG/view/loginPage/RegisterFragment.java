package com.dat257.team1.LFG.view.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.events.RegisterEvent;
import com.dat257.team1.LFG.view.ActivityFeedView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple register account fragment
 *
 * @author : Jakobew & Johan Ek
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private Button createButton;
    private EditText emailField;
    private EditText nameField;
    private EditText passField1;
    private EditText passField2;
    private EditText phoneField;
    private CheckBox termsBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.register_fragment, container, false);

        super.onCreate(savedInstanceState);
        createButton = (Button) rootView.findViewById(R.id.sign_up_button);
        emailField = (EditText) rootView.findViewById(R.id.sign_up_email);
        nameField = (EditText) rootView.findViewById(R.id.sign_up_name);
        passField1 = (EditText) rootView.findViewById(R.id.sign_up_pwd1);
        passField2 = (EditText) rootView.findViewById(R.id.sign_up_pwd2);
        phoneField = (EditText) rootView.findViewById(R.id.sign_up_phone);
        termsBox = (CheckBox) rootView.findViewById(R.id.terms_conditions_checkbox);

        createButton.setOnClickListener(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        return rootView;
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
    void registerUser(final String email, final String password, final String name,
                      final String phone) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
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
     * Method that is called when the user clicks create account. It takes the data from the text
     * field and first checks if the data is correct and then if it is correct it calls the method
     * registerUser. If something is wrong with the data provided by the user a toast message
     * alerts the user of this.
     * <p>
     * Author: Johan Ek
     */
    private void handleCreateUser() {
        String email = emailField.getText().toString(),
                name = nameField.getText().toString(),
                phone = phoneField.getText().toString(), pass1 = passField1.getText().toString(),
                pass2 = passField1.getText().toString();

        if (correctData(email, name, pass1, pass2, phone)) {
            registerUser(email, pass1, name, phone);
        }
    }

    private boolean correctData(String email, String name, String pass1, String pass2, String phone) {
        boolean status = true;
        emailField.setError(null);
        nameField.setError(null);
        passField1.setError(null);
        phoneField.setError(null);
        termsBox.setError(null);

        if (!email.contains("@")) {
            emailField.setError("The email has a incorrect format");
            status = false;
        }
        if (name.equals("")) {
            nameField.setError("You have to specify a name to create a account");
            status = false;
        }
        if (pass1.length() <= 6) {
            passField1.setError("Your password must contain more than 6 character");
            status = false;
        }
        if (!pass1.equals(pass2)) {
            passField1.setError("Your passwords most match");
            status = false;
        }
        if (phone.equals("")) {
            phoneField.setError("You have to specify a phone number to create a account");
            status = false;
        }
        if (!termsBox.isChecked()) {
            termsBox.setError("You have to accept the terms and conditions to create a account");
            status = false;
        }
        return status;
    }

    /**
     * Method that handles the result of the registration of the user. If the registration fails
     * then a toast message is displayed to the user alerting them about this failure. However if
     * the creation was a success the view is changed to the feed of activities.
     * <p>
     * Author: Johan Ek
     *
     * @param event the register event containing information about the result of the registration
     */
    @Subscribe
    public void onRegisterEvent(com.dat257.team1.LFG.events.RegisterEvent event) {
        if (event.isSuccess()) {
            Intent intent = new Intent(getActivity(), ActivityFeedView.class);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong in the account creation", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        handleCreateUser();
    }
}
