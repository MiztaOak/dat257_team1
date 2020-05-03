package com.dat257.team1.LFG.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.viewmodel.MapViewModel;

import java.util.List;

public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;

    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);

        mapViewModel = ViewModelProviders.of(requireActivity()).get(MapViewModel.class);
        mapViewModel.getActivities().observe(getActivity(), new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activity) {

            }
        });

        return rootView;
    }

}
