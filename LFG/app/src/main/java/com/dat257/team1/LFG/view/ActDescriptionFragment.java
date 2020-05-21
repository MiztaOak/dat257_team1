package com.dat257.team1.LFG.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.events.CommentEvent;
import com.dat257.team1.LFG.events.JoinActivityEvent;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Comment;
import com.dat257.team1.LFG.view.commentFeed.CommentAdapter;
import com.dat257.team1.LFG.viewmodel.ActivityDescriptionViewModel;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ActDescriptionFragment extends Fragment {

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        activityDescriptionViewModel = new ViewModelProvider(this).get(ActivityDescriptionViewModel.class);
        getLifecycle().addObserver(activityDescriptionViewModel);
        activityDescriptionViewModel.onCreate();

        mutableActivity = activityDescriptionViewModel.getMutableActivity();
        activityDescriptionViewModel.getMutableActivity().observe(getViewLifecycleOwner(), new Observer<Activity>() {
            @Override
            public void onChanged(Activity activity) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(activity.getTitle());
                activityDescription.setText(activity.getDescription());
                activityTitle.setText(activity.getTitle());
                activitySchedule.setText(activity.getTimestamp().toDate().toString());
                //activityImage.setImageResource(R.drawable.dog_image_activity); //TODO
                userName.setText(FireStoreHelper.getInstance().getIdToNameDictionary().get(activity.getOwner()));
            }
        });

        comments = activityDescriptionViewModel.getMutableComments();
        activityDescriptionViewModel.getMutableComments().observe(getViewLifecycleOwner(), new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                reAdapter.notifyDataSetChanged();
                mapView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }
        });

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (!commentText.getText().toString().equals("")) {
                        activityDescriptionViewModel.addComment(commentText.getText().toString());
                        commentText.setText("");
                        commentText.clearFocus();
                    }
                } else {
                    Toast.makeText(getContext(), "You must be signed in to leave a comment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        joinActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    activityDescriptionViewModel.joinActivity();
                    //activityDescriptionViewModel.joinerStatus();
                }else {
                    Toast.makeText(getApplicationContext(), "You must be signed in to join an activity", Toast.LENGTH_SHORT).show();
                }

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.comment_feed);
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        reLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(reLayoutManager);
        reAdapter = new CommentAdapter(comments);
        recyclerView.setAdapter(reAdapter);

        //activityImage.setImageResource(R.drawable.ic); //sets the source to image
        activityTitle.setText("Activity Title");
        userName.setText("User Name");
        activitySchedule.setText("Time/Date");
        activityDescription.setText("Activity Description");

        EventBus.getDefault().register(this); //if you don't like it solve the toasts without this you nerd
    }

/*
    @Override
    protected void onStart() {
        super.onStart();
        activityDescriptionViewModel.onCreate();
    }
 */


    private void initViews(View view) {
        activityImage = view.findViewById(R.id.activity_image);
        mapView = view.findViewById(R.id.mapView);
        commentFeed = view.findViewById(R.id.comment_feed);
        activitySchedule = view.findViewById(R.id.activity_time);
        activityDescription = view.findViewById(R.id.activity_description);
        joinActivity = view.findViewById(R.id.join_activity);
        addComment = view.findViewById(R.id.write_comment);
        activityTitle = view.findViewById(R.id.activity_title);
        userName = view.findViewById(R.id.user_name);
        commentText = view.findViewById(R.id.description_commentTextField);
    }


    //TODO HIGHLY ILLEGAL! - MOVE TO VIEWMODEL!
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

    @Override
    public void onPause() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        EventBus.getDefault().unregister(this);
        activityDescriptionViewModel.cleanup();
    }


}
