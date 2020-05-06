package com.dat257.team1.LFG.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Category;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * This class is responsible for displaying the address on google maps
 *
 * @author : Oussama Anadani
 */
public class MapService extends Fragment implements OnMapReadyCallback {

    private GoogleMap gm;
    private MapView mMapView;
    private StringBuilder stringBuilder;
    private Context context;

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    private List<Activity> activityList;
    private LatLng currentLocation;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";


    public MapService() {
    }

    public MapService(LatLng currentLocation, Context context) {
        this.context = context;
        this.currentLocation = currentLocation;
    }

    public MapService(LatLng currentLocation, List<Activity> activitiesLocations, Context context) {
        this.context = context;
        this.activityList = activitiesLocations;
        this.currentLocation = currentLocation;
    }

    /**
     * A method that adds a new activity to the map
     *
     * @param activity the new activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        initGoogleMap(savedInstanceState);
        return rootView;
    }

    /**
     * A method that fetches the int value of the category name. This helps to sort the
     * categories on the an depending on the name
     */
    private int fetchImageRecourse(Category category) {
        String id = category.getName().trim();
        return this.getResources().getIdentifier(id, "id", context.getPackageName());
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
        mMapView.getMapAsync(this);
    }

    /**
     * A method that marks the location on the map
     *
     * @param map Google map
     */
    @Override
    public void onMapReady(GoogleMap map) {
        gm = map;
        customStyle();
        onMarkerClick();
        markCurrentLocation();
        markActivities();
    }
    /*
    *    //todo get activity locations from db. Now it's some test locations
        List<LatLng> locations = new ArrayList<>();
        locations.add(new LatLng(57.60, 11.97456000));
        locations.add(new LatLng(57.65, 11.97456000));
        locations.add(new LatLng(57.70, 11.97456000));
        locations.add(new LatLng(57.75, 11.97456000));
        locations.add(new LatLng(57.80, 11.97456000));
        * */


    /**
     * A method that marks the activities locations on the map
     */
    public void markActivities() {
        for (int index = 0; index < activityList.size(); index++) {
            LatLng location = new LatLng(activityList.get(index).getLocation().getLatitude(), activityList.get(index).getLocation().getLongitude());
            int imageID = fetchImageRecourse(activityList.get(index).getCategory());
            MarkerOptions markerOptions = new MarkerOptions().position(location).title("Activity here");
            markerOptions.icon(BitmapDescriptorFactory.fromResource(imageID));
            Marker marker = gm.addMarker(markerOptions);
            animateMarker(marker);
        }
    }

    /**
     * A method that marks the current location on the map
     */
    private void markCurrentLocation() {
        gm.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        gm.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));
        gm.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
        MarkerOptions markerOptions = new MarkerOptions().position(currentLocation).title("You are here!");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.here));
        Marker marker = gm.addMarker(markerOptions);
        animateMarker(marker);
    }

    /**
     * A method to add some animation to the marker
     *
     * @param marker The wanted marker to be animated
     */
    private void animateMarker(final Marker marker) {

    }

    /**
     * A method that adds a custom style to the map
     */
    private void customStyle() {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            @SuppressLint("ResourceType") boolean success = gm.setMapStyle(new MapStyleOptions(getResources()
                    .getString(R.raw.style_grey)));

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }

    }

    /**
     * A method that has a listener when marker clicked
     */
    private void onMarkerClick() {
        gm.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
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
