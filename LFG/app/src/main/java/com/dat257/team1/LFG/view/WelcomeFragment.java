package com.dat257.team1.LFG.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dat257.team1.LFG.R;

public class WelcomeFragment extends Fragment {

    private Button loginButton;
    private Button registerButton;
    private Button goButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.welcome_page, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton = view.findViewById(R.id.loginButton);
        registerButton = view.findViewById(R.id.register_button);
        goButton = view.findViewById(R.id.goButton);
        loginButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_loginFragment));
        registerButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_registerFragment));
        goButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_act_feed));

    }
}
