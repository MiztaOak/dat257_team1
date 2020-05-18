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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Category;
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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateActivityView extends AppCompatActivity {

    private static final String LOG_TAG = CreateActivityView.class.getSimpleName();

    int PERMISSION_ID = 12;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    private Geocoder geocoder;
    private AutocompleteSupportFragment autocompleteFragment;
    private Location location;
    private int numOfAttendees;
    private EditText numAttendees;
    private TextView titleTextView;
    private TextView descTextView;
    private TimePicker timePicker;
    private CalendarView calendarView;
    private Spinner categorySpinner;
    private CheckBox privateEvent;
    private CreateActivityViewModel createActivityViewModel;

    private final int MIN_TITLE_LENGTH = 4;
    private final long MAX_ATTENDEES = 999999999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_act);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        createActivityViewModel = new ViewModelProvider(this).get(CreateActivityViewModel.class);
        initViews();

        Button createActivityButton = (Button) findViewById(R.id.createActivityButton);
        createActivityButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                Timestamp timestamp = new Timestamp((getActTime() / 1000), 0);
                if (checkFields(getActTitle(), getActDesc(), timestamp, geoPoint, getCategory())) {
                    createActivityViewModel.createActivity(getActTitle(), getActDesc(), timestamp, geoPoint, isPrivateEvent(), getNumOfAttendees(), getCategory());
                    openActivityFeed();
                }
            }
        });
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityFeed();
            }
        });

        ImageButton incNumAttendees = findViewById(R.id.increaseNumAttendees);
        incNumAttendees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numOfAttendees < 999999) {
                    numOfAttendees++;
                    numAttendees.setText(String.valueOf(numOfAttendees));
                }
            }
        });

        ImageButton decNumAttendees = findViewById(R.id.decreaseNumAttendees);
        decNumAttendees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numOfAttendees > 0) {
                    numOfAttendees--;
                    numAttendees.setText(String.valueOf(numOfAttendees));
                }
            }
        });
        titleTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (titleTextView.getText().toString().length() <= 0) {
                    titleTextView.setError("Enter title", getResources().getDrawable(R.drawable.ic_error_red_24dp));
                }
            }
        });

        descTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (descTextView.getText().toString().length() <= 0) {
                    descTextView.setError("Enter description", getResources().getDrawable(R.drawable.ic_error_red_24dp));
                }
            }
        });

        numAttendees.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //if(!charSequence.toString().equals("")) {
                //    numOfAttendees = Integer.parseInt(charSequence.toString().trim());
               // }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.chars().allMatch(Character::isDigit) && editable.toString().length() > 0) {
                    numOfAttendees = Integer.parseInt(editable.toString());
                } else if(editable.toString().length() == 0) {
                    numOfAttendees = 0;
                } else {
                    numAttendees.setError("Only numbers allowed", getResources().getDrawable(R.drawable.ic_error_red_24dp));
                    numAttendees.setText(String.valueOf(numOfAttendees));

                }
            }
        });

        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        // Set up a PlaceSelectionListener to handle the response.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                location.setLatitude(place.getLatLng().latitude);
                location.setLatitude(place.getLatLng().longitude);
                // TODO
                //editAddress.setText(place.getAddress());
            }

            @Override
            public void onError(Status status) {
                // TODO
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                //TODO maybe
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //TODO maybe
            }
        });
    }

    private boolean checkFields(String title, String description, Timestamp time, GeoPoint geoPoint, Category category) { //TODO more checks
        boolean status = true;
        if (!(title.length() >= MIN_TITLE_LENGTH)) {
            status = false;
            titleTextView.setError("Your title must at least be " + MIN_TITLE_LENGTH + " characters long");
        }
        if (description.length() == 0) {
            status = false;
            descTextView.setError("You must specify a description");
        }
        Date currentTime = Calendar.getInstance().getTime();
        Date date = new Date(time.getSeconds());
        if (time.toDate().before(currentTime)) {
            status = false;
            //time picker error
        }
        if (geoPoint == null) {
            status = false;
            Toast.makeText(getApplicationContext(), "You must specify a location for your activity", Toast.LENGTH_SHORT).show();
        }
        if (category == null) {
            status = false;
            ((TextView) categorySpinner.getSelectedView()).setError("You must choose a category for your activity");
        }
        return status;
    }

    private void initViews() {
        categorySpinner = findViewById(R.id.categoryspinner); //Category
        addCategoriesToSpinner();

        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key)); //Location
        placesClient = Places.createClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        titleTextView = ((EditText) findViewById(R.id.editTitle)); //Title

        descTextView = ((EditText) findViewById(R.id.editDesc)); //Description

        privateEvent = findViewById(R.id.privateCheckBox); //private event

        numOfAttendees = 0;
        numAttendees = findViewById(R.id.numAttendeesEditTxtView); //Number of attendees

        timePicker = findViewById(R.id.timePicker); //time && date
        timePicker.setIs24HourView(true);
        timePicker.setHour(0);
        timePicker.setMinute(0);
        calendarView = findViewById(R.id.calendarView);
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
            autocompleteFragment.setText(extrapolateLocation(lastLocation));
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
                                    CreateActivityView.this.location = location;
                                    autocompleteFragment.setText(extrapolateLocation(location));
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

    private String extrapolateLocation(Location location) {
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            return addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null"; //TODO
    }

    private void addCategoriesToSpinner() {
        categorySpinner.setAdapter(new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, Category.values()));
    }

    public void openActivityFeed() {
        Log.d(LOG_TAG, "Activity created!");
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
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

    private Location getActLocation() {
        return location;
    }

    private Long getActTime() {
        return (calendarView.getDate() + (timePicker.getMinute() * 60000) + (timePicker.getHour() * 3600000));
    }

    private int getNumOfAttendees() {
        return numOfAttendees;
    }

    private boolean isPrivateEvent() {
        return privateEvent.isChecked();
    }

    private Category getCategory() {
        return (Category) categorySpinner.getSelectedItem();
    }

}
