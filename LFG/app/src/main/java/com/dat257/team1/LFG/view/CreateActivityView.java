package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.viewmodel.CreateActivityViewModel;

public class CreateActivityView extends AppCompatActivity {

    private static final String LOG_TAG = CreateActivityView.class.getSimpleName();

    private TextView titleTextView;
    private TextView descTextView;
    private CreateActivityViewModel createActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        createActivityViewModel = new ViewModelProvider(this).get(CreateActivityViewModel.class);

        titleTextView = ((EditText) findViewById(R.id.edit_title));
        descTextView = ((EditText) findViewById(R.id.editDesc));

        Button createActivityButton = (Button) findViewById(R.id.createActivityButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        createActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createActivityViewModel.createActivity(getActTitle(), getActDesc());
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

    public void openFindActivity() {
        Intent intent = new Intent(this, ActivityFeedView.class
        );
        startActivity(intent);
    }

    private String getActTitle() {
        return titleTextView.getText().toString();
    }

    private String getActDesc() {
        return descTextView.getText().toString();
    }
}
