package com.dat257.team1.LFG.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.viewmodel.CreateActivityViewModel;

public class CreateActivityView extends AppCompatActivity {

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
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hello world");
            }
        });
    }

    private String getActivityTitle() {
        String title = ((EditText) findViewById(R.id.editTitle)).getText().toString();
        return title;
    }

    private String getActivityDesc() {
        String desc = ((EditText) findViewById(R.id.Add_Description)).getText().toString();
        return desc;
    }
}
