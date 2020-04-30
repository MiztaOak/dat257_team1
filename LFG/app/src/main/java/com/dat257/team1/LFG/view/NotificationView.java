package com.dat257.team1.LFG.view;

import android.os.Bundle;
import android.util.Pair;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.view.commentFeed.CommentAdapter;
import com.dat257.team1.LFG.viewmodel.ActivityDescriptionViewModel;
import com.dat257.team1.LFG.viewmodel.NotificationViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationView extends AppCompatActivity {
    private NotificationViewModel viewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter reAdapter;
    private RecyclerView.LayoutManager reLayoutManager;

    MutableLiveData<List<Pair<String, Activity>>> requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        recyclerView = (RecyclerView) findViewById(R.id.notification_feed);
        reLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(reLayoutManager);
        reAdapter = new NotificationCardAdapter(requests);
        recyclerView.setAdapter(reAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.startup();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.cleanup();
    }
}
