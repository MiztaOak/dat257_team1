package com.dat257.team1.LFG.view.activityFeed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Activity;
import com.dat257.team1.LFG.model.Category;
import com.dat257.team1.LFG.service.LocationService;
import com.dat257.team1.LFG.viewmodel.ActFeedViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
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
public class ActFeedMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private GoogleMap gm;
    private MapView mMapView;
    private StringBuilder stringBuilder;
    private LatLng currentLocation;
    private LocationService locationService;
    private ActFeedViewModel actFeedViewModel;
    private MutableLiveData<List<Activity>> mutableActivityList;

    public ActFeedMapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);
        mMapView = rootView.findViewById(R.id.mapView);
        initGoogleMap(savedInstanceState);
        actFeedLiveData();

        return rootView;
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
                //markCurrentLocation(); //TODO
                markActivities(activityList);
            }
        });
        actFeedViewModel.updateFeed(); //TODO
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
    }

    /**
     * A method that fetches the int value of the category name. This helps to sort the
     * categories on the an depending on the name
     */
    private int fetchImageRecourse(Category category) {
        String id = category.getName().trim();
        int resID = getResources().getIdentifier(id, "drawable", getContext().getPackageName());
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
     * A method that marks the activities locations on the map
     */
    public void markActivities(List<Activity> activityList) {
        for (int index = 0; index < activityList.size(); index++) {
            LatLng location = new LatLng(activityList.get(index).getLocation().getLatitude(), activityList.get(index).getLocation().getLongitude());
            int imageID = fetchImageRecourse(activityList.get(index).getCategory());
            MarkerOptions markerOptions = new MarkerOptions().position(location).title("Activity here");
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.here));
            Marker marker = gm.addMarker(markerOptions);
            animateMarker(marker);
        }
    }

    public static BitmapDescriptor generateBitmapDescriptorFromRes(Context context, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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
