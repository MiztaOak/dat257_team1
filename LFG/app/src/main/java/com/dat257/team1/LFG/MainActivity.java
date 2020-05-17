package com.dat257.team1.LFG;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.view.MenuActivity;
import com.dat257.team1.LFG.view.loginPage.LoginPageFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FireStoreHelper.getInstance();
            openFindActivity();
        } else {
            openLoginPage();
        }
        //  openFindActivity();
        //Intent intent = new Intent(this, CurrentActivityFragment.class);
        //startActivity(intent);

    }

    public void openFindActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }


    public void openLoginPage() {
        Intent intent = new Intent(this, LoginPageFragment.class);
        startActivity(intent);

    }
}
