package com.dat257.team1.LFG.view.ActivityDescription;


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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.dat257.team1.LFG.view.loginPage.LoginPageView;
import com.google.android.gms.maps.MapView;

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
