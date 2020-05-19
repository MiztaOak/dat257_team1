package com.dat257.team1.LFG.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dat257.team1.LFG.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPasswordView extends AppCompatActivity {

    EditText userEmail;
    Button sendPassword;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);


        userEmail = findViewById(R.id.userEmail);
        sendPassword = findViewById(R.id.resetPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        sendPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgetPasswordView.this,
                                    "Reset Link sent to your email", Toast.LENGTH_LONG).show();
                        }

                        else Toast.makeText(ForgetPasswordView.this, task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }


}
