package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Main;
import com.dat257.team1.LFG.view.ActivityFeedView;
import com.dat257.team1.LFG.viewmodel.ActivityFeedViewModel;
import com.dat257.team1.LFG.viewmodel.CreateActivityViewModel;

import java.util.List;

public class CreateActivityView extends AppCompatActivity {

    private static final String LOG_TAG = CreateActivityView.class.getSimpleName();

    private TextView titleTextView;
    private TextView descTextView;
    private TextView addressTextView;
    private TextView timeTextView;
    private TextView numberOfAttendeesTextView;
    private CreateActivityViewModel createActivityViewModel;

    private ActivityFeedViewModel createActivityFeedViewModel;

    private ActivityFeedView activityFeedView;
    private String st;
    private MutableLiveData<List<Activity>> mutableActivityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        createActivityViewModel = new ViewModelProvider(this).get(CreateActivityViewModel.class);
        titleTextView = ((EditText) findViewById(R.id.editTitle));
        descTextView = ((EditText) findViewById(R.id.editDesc));
        addressTextView = ((EditText) findViewById(R.id.editAddress));
        timeTextView = ((EditText) findViewById(R.id.editTime));
        numberOfAttendeesTextView = ((EditText) findViewById(R.id.editTitle));

        Button createActivityButton = (Button) findViewById(R.id.createActivityButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        createActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createActivityViewModel.createActivity(getActTitle(), getActDesc(), getActAddress(), getActTime());
                insertActivity();
                openFindActivity();
            }
        });

        final Observer<Activity> nameObserver = new Observer<Activity>() {
            @Override
            public void onChanged(final Activity newActivity) {
                // Update the UI, in this case, a TextView.

            }
        };

        createActivityViewModel.getCurrentActivity().observe(this, nameObserver);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindActivity();
            }
        });
    }

    public void insertActivity() {
        activityFeedView.getCardsList().add(new CardsView(R.drawable.ic_android_black_24dp, getActTitle(), getActDesc()));
        activityFeedView.getmAdapter().notifyItemInserted(0);
    }

    public void openFindActivity() {
        Log.d(LOG_TAG, "Activity created!");
        Intent intent = new Intent(this, ActivityFeedView.class);

        //Just test to display values in ActivityFeedView
        //st = titleTextView.getText().toString();
        //intent.putExtra("Value", st);

        startActivity(intent);
    }

    private String getActTitle() {
        return titleTextView.getText().toString();
    }

    private String getActDesc() {
        return descTextView.getText().toString();
    }

    private String getActAddress(){
        return addressTextView.getText().toString();
    }

    private String getActTime() { return timeTextView.getText().toString(); }

    public String getNumOfAttendees() { return numberOfAttendeesTextView.getText().toString(); }
}
