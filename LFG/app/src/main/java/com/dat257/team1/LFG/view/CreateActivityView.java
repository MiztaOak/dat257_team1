package com.dat257.team1.LFG.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.viewmodel.ActivityFeedViewModel;
import com.dat257.team1.LFG.viewmodel.CreateActivityViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CreateActivityView extends AppCompatActivity {

    private static final String LOG_TAG = CreateActivityView.class.getSimpleName();

    int PERMISSION_ID = 12;

    FusedLocationProviderClient fusedLocationProviderClient;
    Geocoder geocoder;
    String address;
    String city;
    String country;
    Location currentLocation;
    private TextView titleTextView;
    private TextView descTextView;
    private TextView addressTextView;
    private TextView timeTextView;
    private TextView numberOfAttendeesTextView;
    private CreateActivityViewModel createActivityViewModel;
    private ActivityFeedViewModel createActivityFeedViewModel;
    private ActivityFeedView activityFeedView;
    private String st;
    private MutableLiveData<List<Activity>> mutableActivityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        geocoder = new Geocoder(this, Locale.getDefault());
        PlacesClient placesClient = Places.createClient(this);

        createActivityViewModel = new ViewModelProvider(this).get(CreateActivityViewModel.class);
        titleTextView = ((EditText) findViewById(R.id.editTitle));
        descTextView = ((EditText) findViewById(R.id.editDesc));
        addressTextView = ((EditText) findViewById(R.id.editAddress));
        timeTextView = ((EditText) findViewById(R.id.editTime));
        numberOfAttendeesTextView = ((EditText) findViewById(R.id.editTitle));

        Button createActivityButton = (Button) findViewById(R.id.createActivityButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        createActivityButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                createActivityViewModel.createActivity(getActTitle(), getActDesc(), getActTime(), getActAddress());
                openActivityFeed();
            }
        });

        final Observer<Activity> nameObserver = new Observer<Activity>() {
            @Override
            public void onChanged(final Activity newActivity) {
                // Update the UI, in this case, a TextView.

            }
        };

        createActivityViewModel.getCurrentActivity().observe(this, nameObserver);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityFeed();
            }
        });

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        //autocompleteFragment.setTypeFilter()
        //autocompleteFragment.setCountries("SE");
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));


        // Set up a PlaceSelectionListener to handle the response.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                currentLocation.setLatitude(place.getLatLng().latitude);
                currentLocation.setLatitude(place.getLatLng().longitude);
                // TODO
                addressTextView.setText(String.format("%s,%s", place.getName(), place.getId()));
            }

            @Override
            public void onError(Status status) {
                // TODO
            }
        });
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
            addressTextView.setText(extrapolateLocation(lastLocation));
            //TODO
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private void requestPermissions() {

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }

    private void getUserLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    currentLocation = location;
                                    addressTextView.setText(extrapolateLocation(location));
                                    //latTextView.setText(location.getLatitude()+"");
                                    //lonTextView.setText(location.getLongitude()+"");
                                    //TODO
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private void requestNewLocationData() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback,
                Looper.myLooper()
        );

    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getUserLocation();
        }

    }


    public void openActivityFeed() {
        Log.d(LOG_TAG, "Activity created!");
        Intent intent = new Intent(this, ActivityFeedView.class);

        startActivity(intent);
    }

    private String extrapolateLocation(Location location) {
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            address = addresses.get(0).getAddressLine(0);
            //city = addresses.get(0).getLocality();
            //country = addresses.get(0).getCountryName(); //TODO
            return address;// + ", " + city;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "null"; //TODO
    }

    public void getLocationOnClick(View view) {
        getUserLocation();
    }

    private String getActTitle() {
        return titleTextView.getText().toString();
    }

    private String getActDesc() {
        return descTextView.getText().toString();
    }

    private String getActAddress() {
        return addressTextView.getText().toString();
    }

    private String getActTime() {
        return timeTextView.getText().toString();
    }

    public String getNumOfAttendees() {
        return numberOfAttendeesTextView.getText().toString();
    }
}
