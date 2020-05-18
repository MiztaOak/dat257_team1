package com.dat257.team1.LFG.view.activityFeed;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Category;
import com.dat257.team1.LFG.model.Main;
import com.dat257.team1.LFG.view.ActDescriptionFragment;
import com.dat257.team1.LFG.viewmodel.ActFeedViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for displaying the address on google maps
 *
 * @author : Oussama Anadani, Johan Ek, Jakob Ew
 */
public class ActFeedMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private GoogleMap gm;
    private MapView mMapView;
    private ActFeedViewModel actFeedViewModel;
    private MutableLiveData<List<Activity>> mutableActivityList;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private boolean firstTimeFlag = true;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    private Context context;
    Map<Marker, Activity> markerClick;


    public ActFeedMapFragment() {
    }

    /**
     * A method that marks the location on the map
     *
     * @param map Google map
     */
    @Override
    public void onMapReady(GoogleMap map) {
        gm = map;
        gm.setMyLocationEnabled(true);
        attachMarker();
        customStyle();

    }

    /**
     * A method that marks the activities locations on the map
     */
    public void markActivities(List<Activity> activityList) {
        markerClick = new HashMap<>();
        for (int index = 0; index < activityList.size(); index++) {
            LatLng location = new LatLng(activityList.get(index).getLocation().getLatitude(), activityList.get(index).getLocation().getLongitude());
            int imageID = fetchImageRecourse(activityList.get(index).getCategory());
            MarkerOptions markerOptions = new MarkerOptions().position(location).title(activityList.get(index).getTitle());
            if (imageID != 0)
                markerOptions.icon(BitmapDescriptorFactory.fromResource(imageID));
            else {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.other));
            }

            Marker marker = gm.addMarker(markerOptions);
            markerClick.put(marker, activityList.get(index));
            marker.setTag(activityList.get(index).getTitle());
            animateMarker(marker);
        }
    }

    /**
     * A method that marks the current location on the map
     */
    private void markCurrentLocation(LatLng currentLocation) {
        gm.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        gm.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));
        gm.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
        MarkerOptions markerOptions = new MarkerOptions().position(currentLocation).title("You are here!");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation));
        Marker marker = gm.addMarker(markerOptions);
        marker.setTag("currentLocation");
        animateMarker(marker);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_act_feed_maps, container, false);
        mMapView = rootView.findViewById(R.id.mapView);
        initGoogleMap(savedInstanceState);
        actFeedLiveData();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
    }


    /**
     * Requesting location permission
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(getContext(), "Permission denied by uses", Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startCurrentLocationUpdates();
        }
    }

    /**
     * Updating the location
     */
    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    /**
     * Location callback to get last location
     */
    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation() == null)
                return;
            currentLocation = locationResult.getLastLocation();
            if (firstTimeFlag && gm != null) {
                LatLng loc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                markCurrentLocation(loc);
                firstTimeFlag = false;
            }
        }
    };

    /**
     * Checking if google service is available
     *
     * @return true if it's available, false otherwise
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(getContext());
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(getContext(), "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    /**
     * A liveData method for activity feed ViewModel
     */
    private void actFeedLiveData() {
        actFeedViewModel = new ViewModelProvider(this).get(ActFeedViewModel.class);
        getLifecycle().addObserver(actFeedViewModel); //TODO
        actFeedViewModel.onCreate();
        mutableActivityList = actFeedViewModel.getMutableActivityList();
        mutableActivityList.observe(getViewLifecycleOwner(), new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activityList) {
                markActivities(activityList);
                // onMarkerClick();
            }
        });
        actFeedViewModel.updateFeed(); //TODO
    }

    /**
     * A method that fetches the int value of the category name. This helps to sort the
     * categories on the an depending on the name
     */
    private int fetchImageRecourse(Category category) {
        String id = category.getName().trim().toLowerCase();
        int resID = getResources().getIdentifier(id, "drawable", context.getPackageName());
        return resID;
    }


    /**
     * A method that initiates google map
     *
     * @param savedInstanceState
     */
    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }


    /**
     * A method to add some animation to the marker
     *
     * @param marker The wanted marker to be animated
     */
    private void animateMarker(final Marker marker) {
        //todo
    }

    /**
     * A method that adds a custom style to the map
     */
    private void customStyle() {
        gm.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        context, R.raw.style_grey));
    }

    /**
     * A method that has a listener when marker clicked
     */

    private void attachMarker() { //todo
        gm.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (!marker.getTag().equals("currentLocation")) {
                    Main.getInstance().setFocusedActivity(markerClick.get(marker));
                    Intent intent = new Intent(getContext(), ActDescriptionFragment.class);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            startCurrentLocationUpdates();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }


    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
