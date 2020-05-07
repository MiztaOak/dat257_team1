package com.dat257.team1.LFG.view.activityDescription;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dat257.team1.LFG.R;

public class ActivityDescriptionFragment extends Fragment {

    private Button addComment;
    private Button joinActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_description, container, false);

        joinActivity = rootView.findViewById(R.id.join_activity);
        joinActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //TODO Create method that sends join Request pending.
            }
        });

        addComment = rootView.findViewById(R.id.write_comment);
        addComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //TODO Create method that writes a comment.

            }
        });

        return rootView;
    }
}
