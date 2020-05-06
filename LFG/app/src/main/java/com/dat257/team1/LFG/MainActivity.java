package com.dat257.team1.LFG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.dat257.team1.LFG.model.Message;
import androidx.appcompat.app.AppCompatActivity;

import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.view.ActivityFeedView;
import com.dat257.team1.LFG.view.loginPage.LoginPageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireStoreHelper.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            openFindActivity();
        } else {
            openLoginPage();
        }
        //openFindActivity();
       // Intent intent = new Intent(this, MenuActivity.class);
       // startActivity(intent);

    }

    public void openFindActivity() {
        Intent intent = new Intent(this, ActivityFeedView.class);
        startActivity(intent);
    }


    public void openLoginPage() {
        Intent intent = new Intent(this, LoginPageView.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.nav_home){
            startActivity(new Intent(MainActivity.this, Message.class));
        }else if (id == R.id.nav_gallery){
            startActivity(new Intent(MainActivity.this, Message.class));
        }else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, Message.class));
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
