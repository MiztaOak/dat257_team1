package com.dat257.team1.LFG.viewmodel;

import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.dat257.team1.LFG.events.RegisterEvent;
import com.dat257.team1.LFG.view.ActivityFeedView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * The class representing the registration view that allows the user to register a account using a
 * email and password.
 * <p>
 * Author: Johan Ek
 */
public class LoginPageViewModel extends ViewModel implements LifecycleObserver {


    public LoginPageViewModel() {
    }
}
