package com.dat257.team1.LFG.view.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.MainActivity;
import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.events.RegisterEvent;
import com.dat257.team1.LFG.view.ActivityFeedView;
import com.dat257.team1.LFG.viewmodel.LoginPageViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is related to the login page that is responsible for
 * authenticating the user's data from the database.
 *
 * @author : Oussama Anadani, Johan Ek, Jakobew
 */
public class LoginPageView extends FragmentActivity {
    FirebaseAuth mAuth;
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

        mAuth = FirebaseAuth.getInstance();

        loginPageViewModel = new ViewModelProvider(this).get(LoginPageViewModel.class);
        getLifecycle().addObserver(loginPageViewModel);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is already registered (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, ActivityFeedView.class);
            //startActivity(intent);
        }
        //retrieveData(currentUser);
    }
}