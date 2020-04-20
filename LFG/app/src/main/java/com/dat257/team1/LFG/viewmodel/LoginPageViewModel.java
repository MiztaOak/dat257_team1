package com.dat257.team1.LFG.viewmodel;

import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.events.RegisterEvent;
import com.dat257.team1.LFG.view.ActivityFeedView;
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

/**
 * The class representing the registration view that allows the user to register a account using a
 * email and password.
 * <p>
 * Author: Johan Ek
 */
public class LoginPageViewModel extends ViewModel implements LifecycleObserver {

    private Button createButton;
    private TextInputLayout emailField;
    private TextInputLayout nameField;
    private TextInputLayout passField;
    private TextInputLayout phoneField;
    private CheckBox termsBox;


    public LoginPageViewModel() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
        String email = emailField.getEditText().getText().toString(),
                name = nameField.getEditText().getText().toString(),
                phone = phoneField.getEditText().getText().toString(), pass = passField.getEditText().getText().toString();

        if (correctData(email, name, pass, phone)) {
            //registerUser(email, pass, name, phone);
            //TODO
        }
    }

    private boolean correctData(String email, String name, String pass, String phone) {
        boolean status = true;
        emailField.setError(null);
        nameField.setError(null);
        passField.setError(null);
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
        if (pass.length() <= 6) {
            passField.setError("Your password must contain more than 6 character");
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
    public void onRegisterEvent(RegisterEvent event) {
        if (event.isSuccess()) {
            //Intent intent = new Intent(this, ActivityFeedView.class);
            //startActivity(intent);
        } else {
            //Toast.makeText(getApplicationContext(), "Something went wrong in the account creation", Toast.LENGTH_SHORT).show();
        }
    }
}
