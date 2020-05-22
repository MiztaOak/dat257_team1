package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.loginPage.WelcomeActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Button signOut;
    private RelativeLayout menuProfileRelativeLayout;
    private ImageView menuProfilePic;
    private TextView menuProfileText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        View headerView = LayoutInflater.from(this).inflate(R.layout.menu_nav_header,navigationView,false);
        navigationView.addHeaderView(headerView);

        menuProfileRelativeLayout = headerView.findViewById(R.id.menu_profile_pic);
        menuProfilePic = headerView.findViewById(R.id.circleImage_profile);
        menuProfileText = headerView.findViewById(R.id.menu_icon_text);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_act_feed, R.id.nav_my_activities, R.id.nav_messages, R.id.nav_notifications, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (FirebaseAuth.getInstance().getCurrentUser() == null && id != R.id.nav_act_feed && id != R.id.nav_about) {
                    Toast.makeText(getApplicationContext(), "You must be signed in to access this feature", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    FragmentManager fm = getSupportFragmentManager();
                    fm.popBackStackImmediate();
                    boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
                    if (handled) {
                        ViewParent parent = navigationView.getParent();
                        if (parent instanceof DrawerLayout) {
                            ((DrawerLayout) parent).closeDrawer(navigationView);
                        }
                    }
                    return handled;
                }
            }
        });


        signOut = findViewById(R.id.btn_sign_out);
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            signOut.setText("Sign in");
        else
            signOut.setText("Sign out");
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                openSignOutPage();
            }
        });

        menuProfileRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_profile);
                drawer.closeDrawers();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void openSignOutPage() {
        startActivity(new Intent(this, WelcomeActivity.class));

    }

/*
    private void openProfile() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,YOUR_FRAGMENT_NAME,YOUR_FRAGMENT_STRING_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
     */
}
