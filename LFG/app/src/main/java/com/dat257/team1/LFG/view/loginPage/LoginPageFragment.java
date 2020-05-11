package com.dat257.team1.LFG.view.loginPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.MenuActivity;
import com.dat257.team1.LFG.viewmodel.LoginPageViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class is related to the login page that is responsible for
 * authenticating the user's data from the database.
 *
 * @author : Oussama Anadani, Johan Ek, Jakobew
 */
public class LoginPageFragment extends FragmentActivity {
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
        if (currentUser != null) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
        //retrieveData(currentUser);
    }
}