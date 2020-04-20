package com.dat257.team1.LFG.view.loginPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dat257.team1.LFG.R;


/**
 * A simple login on an account fragment
 *
 * @author : Jakobew
 */
public class LoginFragment extends Fragment {

    private Button loginButton;
    private EditText passwordField, emailField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);

        loginButton = rootView.findViewById(R.id.sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailField = view.findViewById(R.id.sign_in_email);
                String email = emailField.getText().toString().trim();
                passwordField = view.findViewById(R.id.sign_in_pwd);
                String password = passwordField.getText().toString().trim();
                //checking if the user has entered a validate form of the email and password
                //if (validateForm(email, password))
                   // loginUser(email, password);
            }
        });

        return rootView;
}
}
