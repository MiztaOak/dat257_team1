package com.dat257.team1.LFG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dat257.team1.LFG.firebase.ActivityDataHolder;
import com.dat257.team1.LFG.util.DataBase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.firebase.FireStoreHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FireStoreHelper fireStoreHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireStoreHelper = new FireStoreHelper();
        //fireStoreHelper.addActivity("wZCl4ABLmASoWSyy9un7sWFNHG22");

    }


}
