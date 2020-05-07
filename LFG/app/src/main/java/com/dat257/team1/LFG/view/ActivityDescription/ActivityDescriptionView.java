package com.dat257.team1.LFG.view.activityDescription;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.events.CommentEvent;
import com.dat257.team1.LFG.events.JoinActivityEvent;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Comment;
import com.dat257.team1.LFG.view.commentFeed.CommentAdapter;
import com.dat257.team1.LFG.viewmodel.ActivityDescriptionViewModel;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ActivityDescriptionView extends AppCompatActivity {

    private ActivityDescriptionViewModel activityDescriptionViewModel;
    private MutableLiveData<Activity> mutableActivity;
    private MutableLiveData<List<Comment>> comments;

    private ImageView activityImage;
    private TextView activityTitle;
    private TextView userName;
    private TextView activitySchedule;
    private TextView activityDescription;
    private MapView mapView;
    private RecyclerView commentFeed;
    private Button addComment;
    private Button joinActivity;
    private EditText commentText;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter reAdapter;
    private RecyclerView.LayoutManager reLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        initViews();
        fetchActivityLocation();

        activityDescriptionViewModel = new ViewModelProvider(this).get(ActivityDescriptionViewModel.class);
        getLifecycle().addObserver(activityDescriptionViewModel);
        activityDescriptionViewModel.onCreate();

        mutableActivity = activityDescriptionViewModel.getMutableActivity();
        activityDescriptionViewModel.getMutableActivity().observe(this, new Observer<Activity>() {
            @Override
            public void onChanged(Activity activity) {

                activityDescription.setText(activity.getDescription());
                activityTitle.setText(activity.getTitle());
                activitySchedule.setText(activity.getTimestamp().toDate().toString());
                activityImage.setImageResource(R.drawable.dog_image_activity);
            }
        });

        comments = activityDescriptionViewModel.getMutableComments();
        activityDescriptionViewModel.getMutableComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                reAdapter.notifyDataSetChanged();
                mapView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getSupportFragmentManager();
                        LatLng locationTest = new LatLng(57.708870, 11.974560);
                        //    Map gm = new Map();
                        // gm.markLocation(locationTest);
                        // fm.beginTransaction().replace(R.id.mapView, gm).commit();
                        //  mapView.getMapAsync(this);

                    }
                });
            }
        });


        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!commentText.getText().toString().equals("")) {
                    activityDescriptionViewModel.addComment(commentText.getText().toString());
                    commentText.setText("");
                    commentText.clearFocus();
                }
                System.out.println("Added a comment");
            }
        });

        joinActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityDescriptionViewModel.joinActivity();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.comment_feed);
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        reLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(reLayoutManager);
        reAdapter = new CommentAdapter(comments);
        recyclerView.setAdapter(reAdapter);

        //activityImage.setImageResource(R.drawable.SRC); //sets the source to image
        activityTitle.setText("Activity Title");
        userName.setText("User Name");
        activitySchedule.setText("Time/Date");
        activityDescription.setText("Activity Description");

        EventBus.getDefault().register(this); //if you don't like it solve the toasts without this you nerd
    }


    private void fetchActivityLocation() {

    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        activityDescriptionViewModel.onCreate();
    }
 */

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        activityDescriptionViewModel.cleanup();
    }

    private void initViews() {
        activityImage = findViewById(R.id.activity_image);
        mapView = findViewById(R.id.mapView);
        commentFeed = findViewById(R.id.comment_feed);
        activitySchedule = findViewById(R.id.activity_time);
        activityDescription = findViewById(R.id.activity_description);
        joinActivity = findViewById(R.id.join_activity);
        addComment = findViewById(R.id.write_comment);
        activityTitle = findViewById(R.id.activity_title);
        userName = findViewById(R.id.user_name);
        commentText = findViewById(R.id.description_commentTextField);
    }


    //TODO HIGHLY ILLEGAL!
    @Subscribe
    public void handleCommentEvent(CommentEvent event) {
        if (!event.isSuccess()) {
            Toast.makeText(getApplicationContext(), "Something went wrong when trying to post your comment", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void handleJoinEvent(JoinActivityEvent event) {
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
        if (event.isSuccess()) {
            //leave view
        }
    }

    private void updateActivityDescriptionMap(LatLng location) {
        // display activity
        // GoogleMaps gm = new GoogleMaps();
        // gm.markLocation(location);
    }
}
