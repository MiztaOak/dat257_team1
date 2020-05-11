package com.dat257.team1.LFG.view;

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
import com.dat257.team1.LFG.model.JoinNotification;
import com.dat257.team1.LFG.viewmodel.NotificationViewModel;

import java.util.List;

public class NotificationFragment extends Fragment {
    private MutableLiveData<List<JoinNotification>> requests;
    private NotificationViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter reAdapter;
    private RecyclerView.LayoutManager reLayoutManager;

    public NotificationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_notification, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        getViewLifecycleOwner().getLifecycle().addObserver(viewModel);
        requests = viewModel.getRequests();
        requests.observe(getViewLifecycleOwner(), new Observer<List<JoinNotification>>() {
            @Override
            public void onChanged(List<JoinNotification> joinNotifications) {
                reAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.notification_feed);
        reLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(reLayoutManager);
        reAdapter = new NotificationCardAdapter(requests);
        recyclerView.setAdapter(reAdapter);
    }
}
