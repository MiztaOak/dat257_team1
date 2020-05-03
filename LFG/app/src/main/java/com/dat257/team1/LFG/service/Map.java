package com.dat257.team1.LFG.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.dat257.team1.LFG.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for displaying the address on google maps
 *
 * @author : Oussama Anadani
 */
public class Map extends Fragment implements OnMapReadyCallback {

    private GoogleMap gm;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    public Map() {
    }
/*
    public Map(LatLng location) {
        gm.animateCamera(CameraUpdateFactory.newLatLng(location));
        gm.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
        gm.addMarker(new MarkerOptions().position(location).title("Activity here"));
    }

 */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        initGoogleMap(savedInstanceState);
        return rootView;
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
        //gm.setMyLocationEnabled(true);
        customStyle();
        onMarkerClick();
        //todo get activity locations from db. Now it's some test locations
        List<LatLng> locations = new ArrayList<>();
        locations.add(new LatLng(57.60, 11.97456000));
        locations.add(new LatLng(57.65, 11.97456000));
        locations.add(new LatLng(57.70, 11.97456000));
        locations.add(new LatLng(57.75, 11.97456000));
        locations.add(new LatLng(57.80, 11.97456000));
        for (LatLng testLocation : locations) {
            markLocation(testLocation);
        }
    }


    /**
     * A method that marks the location on the map
     *
     * @param location The location that will be marked on the map
     */
    private void markLocation(LatLng location) {
        gm.moveCamera(CameraUpdateFactory.newLatLng(location));
        gm.animateCamera(CameraUpdateFactory.newLatLng(location));
        gm.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        MarkerOptions markerOptions = new MarkerOptions().position(location).title("Activity here");
        // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.football));
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
