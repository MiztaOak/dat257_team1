package com.dat257.team1.LFG.view.ActivityDescription;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.events.CommentEvent;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Comment;
import com.dat257.team1.LFG.view.commentFeed.CommentAdapter;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Observable;

public class ActivityDescriptionView extends AppCompatActivity {

    private ActivityDescriptionViewModel activityDescriptionViewModel;
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

    private MutableLiveData<List<Comment>> comments;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        initViews();

        activityDescriptionViewModel = new ViewModelProvider(this).get(ActivityDescriptionViewModel.class);
        activityDescriptionViewModel.getActivity().observe(this, new Observer<Activity>() {
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
                if(!commentText.getText().toString().equals("")) {
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
                System.out.println("Joined activity");
            }
        });

        activityDescriptionViewModel = new ViewModelProvider(this).get(ActivityDescriptionViewModel.class);
        comments = activityDescriptionViewModel.getComments();
        comments.observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                reAdapter.notifyDataSetChanged();
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

        EventBus.getDefault().register(this);
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
        commentText = findViewById(R.id.description_commentTextField);
    }

    @Subscribe
    public void handleCommentEvent(CommentEvent event){
        if(!event.isSuccess()){
            Toast.makeText(getApplicationContext(),"Something went wrong when trying to post your comment",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateActivityDescriptionMap(LatLng location) {
    // display activity
        GoogleMaps gm = new GoogleMaps();
        gm.markLocation(location);
    }
}
