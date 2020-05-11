package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.view.activityFeed.ActFeedPageFragment;
import com.dat257.team1.LFG.view.chatList.ChatListFragment;
import com.dat257.team1.LFG.view.loginPage.LoginPageFragment;
import com.dat257.team1.LFG.view.myActivities.MyActPageFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //CircularImageView imageView = new CircularImageView(this, findViewById(R.id.circleImage_profile)); todo

        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_act_feed, R.id.nav_my_activities, R.id.nav_messages, R.id.nav_notifications, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        signOut = findViewById(R.id.btn_sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                openSignOutPage();
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_act_feed:
                startActivity(new Intent(this, ActFeedPageFragment.class));
                ;
                break;
            case R.id.nav_my_activities:
                startActivity(new Intent(this, MyActPageFragment.class));
                ;
                break;
            case R.id.nav_friends:
                startActivity(new Intent(this, ActFeedPageFragment.class));
                ;
                break;
            case R.id.nav_messages:
                startActivity(new Intent(this, ChatListFragment.class));
                ;
                break;
            case R.id.nav_notifications:
                startActivity(new Intent(this, ChatListFragment.class));
                ;
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, ChatListFragment.class));
                ;
                break;
            default:
                startActivity(new Intent(this, ActFeedPageFragment.class));
                ;
                break;
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openSignOutPage(){
        startActivity(new Intent(this, LoginPageFragment.class));
    }
}
