package com.dat257.team1.LFG.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.viewmodel.CreateActivityViewModel;
import com.dat257.team1.LFG.viewmodel.FeedViewModel;
import com.dat257.team1.LFG.viewmodel.FindActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dat257.team1.LFG.R;

public class CreateActivityView extends AppCompatActivity {

    private TextView titleTextView;
    private TextView descTextView;
    private CreateActivityViewModel createActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        createActivityViewModel = new ViewModelProvider(this).get(CreateActivityViewModel.class);

        Button createActivityButton = (Button) findViewById(R.id.createActivityButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        createActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createActivityViewModel.createActivity(getActivityTitle(), getActivityDesc());
                openFindActivity();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindActivity();
            }
        });
    }
    public void openFindActivity(){

        Intent intent = new Intent(this, FindActivity.class
        );
        startActivity(intent);
    }

    private String getActTitle() {
        String title = ((EditText) findViewById(R.id.editTitle)).getText().toString();
        return title;
    }

    private String getActDesc() {
        String desc = ((EditText) findViewById(R.id.editDesc)).getText().toString(); //Change to editDesc
        return desc;
    }

    public void createActivity(View view) {
        createActivityViewModel.createActivity(getActTitle(), getActDesc());
        Intent intent = new Intent(this, FeedViewModel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
