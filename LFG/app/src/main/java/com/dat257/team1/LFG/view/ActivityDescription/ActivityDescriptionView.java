package com.dat257.team1.LFG.view.ActivityDescription;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.service.GoogleMaps;
import com.dat257.team1.LFG.viewmodel.ActivityDescriptionViewModel;
import com.dat257.team1.LFG.viewmodel.ActivityFeedViewModel;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class ActivityDescriptionView extends AppCompatActivity {

    ActivityDescriptionViewModel activityDescriptionViewModel;
    private ImageView activityImage;
    private TextView activityTitle;
    private TextView userName;
    private TextView activitySchedule;
    private TextView activityDescription;
    private MapView mapView;
    private RecyclerView commentFeed;
    private Button addComment;
    private Button joinActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        activityDescriptionViewModel = new ViewModelProvider(this).get(ActivityDescriptionViewModel.class);
        activityDescriptionViewModel.getMutableActivityList().observe(this, new Observer<Activity>() {
            @Override
            public void onChanged(Activity activity) {
             //  GeoPoint location = new GeoPoint(activity.getLocation().getLatitude(), activity.getLocation().getLongitude());
              LatLng locationTest = new LatLng(57.708870, 11.974560);
             //   updateActivityDescriptionMap(locationTest);
            }
        });

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Added a comment");
            }
        });

        joinActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Joined activity");
            }
        });

        //activityImage.setImageResource(R.drawable.SRC); //sets the source to image
        activityTitle.setText("Activity Title");
        userName.setText("User Name");
        activitySchedule.setText("Time/Date");
        activityDescription.setText("Activity Description");

        initViews();
    }

    private void initViews() {
        activityImage = findViewById(R.id.activity_image);
        mapView = findViewById(R.id.activity_map);
        commentFeed = findViewById(R.id.comment_feed);
        activitySchedule = findViewById(R.id.activity_time);
        activityDescription = findViewById(R.id.activity_description);
        joinActivity = findViewById(R.id.join_activity);
        addComment = findViewById(R.id.write_comment);
        activityTitle = findViewById(R.id.activity_title);
        userName = findViewById(R.id.user_name);
    }

    private void updateActivityDescriptionMap(LatLng location) {
    // display activity
        GoogleMaps gm = new GoogleMaps();
        gm.markLocation(location);
    }
}
