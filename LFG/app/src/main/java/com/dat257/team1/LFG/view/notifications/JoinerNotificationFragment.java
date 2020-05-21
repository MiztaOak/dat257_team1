package com.dat257.team1.LFG.view.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.NotificationForJoiner;
import com.dat257.team1.LFG.view.notifications.JoinerNotificationCardAdapter;
import com.dat257.team1.LFG.viewmodel.NotificationStatusViewModel;
import com.dat257.team1.LFG.viewmodel.NotificationViewModel;

import java.util.List;

public class JoinerNotificationFragment extends Fragment {

    private MutableLiveData<List<NotificationForJoiner>> status;
    private NotificationStatusViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter reAdapter;
    private RecyclerView.LayoutManager reLayoutManager;


    public JoinerNotificationFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_joiner_notification, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotificationStatusViewModel.class);
        getViewLifecycleOwner().getLifecycle().addObserver(viewModel);
        status = viewModel.getStatus();
        status.observe(getViewLifecycleOwner(), new Observer<List<NotificationForJoiner>>() {
            @Override
            public void onChanged(List<NotificationForJoiner> notificationForJoiners) {

                reAdapter.notifyDataSetChanged();
            }


        });



        recyclerView = (RecyclerView) view.findViewById(R.id.notification_for_joiner_feed);
        reLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(reLayoutManager);
        reAdapter = new JoinerNotificationCardAdapter(status);
        recyclerView.setAdapter(reAdapter);

    }

    /*

    private void updateNotifications(String uID) {
        viewModel.loadStatus(uID);
    }

     */

}
