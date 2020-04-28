package com.dat257.team1.LFG.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.view.ActivityDescription.ActivityDescriptionView;
import com.dat257.team1.LFG.viewmodel.ActivityCardViewModel;

public class ActivityCardFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = ActivityCardFragment.class.getSimpleName();

    private int mImageResource;
    private String mTitle;
    private String mDescription;
    ActivityCardViewModel activityCardViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final  View rootView = inflater.inflate(R.layout.activity_card_fragment, container, false);
        activityCardViewModel = new ViewModelProvider(this).get(ActivityCardViewModel.class);
        return rootView;
    }


    public ActivityCardFragment(Activity activity){
        mImageResource = R.drawable.ic_android_black_24dp; //TODO
        mTitle = activity.getTitle();
        mDescription = activity.getDescription();
        activityCardViewModel.setMutableActivity(activity);
    }

    //TODO add method that updates the card.

    public int getImageResource(){
        return mImageResource;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getDescription(){
        return mDescription;
    }


    @Override
    public void onClick(View view) {
        activityCardViewModel.onClick();
        Log.d(LOG_TAG, "Activity card clicked!");
        Intent intent = new Intent(getContext(), ActivityDescriptionView.class);
        startActivity(intent);
    }
}
