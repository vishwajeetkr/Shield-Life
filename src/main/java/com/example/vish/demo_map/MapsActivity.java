package com.example.vish.demo_map;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

//import com.example.vish.demo_fucking_map_map.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MobileServiceClient mClient;
    private LocationManager locationManage;
    private String address = null;
    private Geocoder coder;
    //private MobileServiceClient mClient;
    private MobileServiceTable<TodoItem> mTable;
    private MobileServiceTable<EarthquakeData> qTable;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    //The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        try {
            mClient = new MobileServiceClient("https://shieldlife3.azurewebsites.net",this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        address = intent.getStringExtra("addString");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManage = (LocationManager) getSystemService(LOCATION_SERVICE);
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            Log.e("perm err","permission err");
            //return;
            //ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_EXTRA_COMMANDS);
        }*/
        /*mMap.setMyLocationEnabled(true);
        //Location myLocation = googleMap.getMyLocation();  //Nullpointer exception.........
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location myLocation = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));


        LatLng myLatLng = new LatLng(myLocation.getLatitude(),
                myLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(myLatLng).title("It's Me!"));
        //else{
        //    Log.e("location_err","location access not granted!");
        //}

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location arg0) {
                // TODO Auto-generated method stub

                mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
            }
        });
        Log.e("perm err","network provider");
        if(locationManage.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManage.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng myLatLng = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(myLatLng).title("it's me"));

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        else if(locationManage.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManage.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng myLatLng = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(myLatLng).title("it's me"));

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        else {// Add a marker in Sydney and move the camera
            Log.e("perm err", "gps provider");
        }*/
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //getLocationPermission();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(-34, 151)).title("Marker in Sydney"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(4, 151)).title("Marker in Sydney"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(-34, 51)).title("Marker in Sydney"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(-78, 151)).title("Marker in Sydney"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(41, 151)).title("Marker in Sydney"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(134, 51)).title("Marker in Sydney"));
        //getLocationPermission();

        if(address.compareTo("mylocation") != 0)
            markLocation(address);
        else
            markMyLocation();

        refreshItemsFromTable();
    }

    private void markMyLocation() {
        /*Log.d("in_func","func- my location");
        if (mMap == null) {
            Log.d("in_func","map null");
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                Location myLocation = mMap.getMyLocation();  //Nullpointer exception.........



                LatLng myLatLng = new LatLng(myLocation.getLatitude(),
                        myLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(myLatLng).title("It's Me!"));
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                //mLastKnownLocation = null;
                //getLocationPermission();
            }
        } catch (SecurityException e)  {
            //Log.e("Exception: %s", e.getMessage());
            Log.e("error_mark","mark location error");
        }
        Log.d("in_func","function ended ");*/
    }

    private void markLocation(String address){
        List<Address> addressCode = null;
        Address locationAdd = null;
        try
        {
            coder = new Geocoder(this, Locale.getDefault());

            addressCode = coder.getFromLocationName(address, 1);
            if (!addressCode.isEmpty() || address.compareTo("mylocation") != 0)
            {
                locationAdd = addressCode.get(0);
                locationAdd.getLatitude();
                locationAdd.getLongitude();

                mMap.addMarker(new MarkerOptions().position(new LatLng(locationAdd.getLatitude(), locationAdd.getLongitude()))
                                .title("Searched Location"));
                //Log.d("Latlng : ", temp + "");
                //Log.e("err_1 : ", " fine ");
            }
        } catch (IOException e)
        {
            //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("err_1 : ", " io ");
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.e("err_1 : ", " exception ");
        }
    }

    private void markCircle(String address, double mag){
        List<Address> addressCode = null;
        Address locationAdd = null;
        try
        {
            coder = new Geocoder(this, Locale.getDefault());

            addressCode = coder.getFromLocationName(address, 1);
            if (!addressCode.isEmpty() || address.compareTo("mylocation") != 0)
            {
                locationAdd = addressCode.get(0);
                locationAdd.getLatitude();
                locationAdd.getLongitude();
                Circle circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(locationAdd.getLatitude(), locationAdd.getLongitude()))
                        .radius((long)mag*10000)
                        .strokeColor(Color.RED)
                        .fillColor(Color.BLUE));
                //mMap.addMarker(new MarkerOptions().position(new LatLng(locationAdd.getLatitude(), locationAdd.getLongitude()))
                //                .title("Searched Location"));
                //Log.d("Latlng : ", temp + "");
                Log.e("err_1 : ", " fine ");
            }
        } catch (IOException e)
        {
            //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("err_1 : ", " io ");
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.e("err_1 : ", " exception ");
        }
    }
    private void refreshItemsFromTable() {
        Log.d("database","refresh");
//		TODO Uncomment the the following code when using a mobile service
//		// Get the items that weren't marked as completed and add them in the adapter
	    new AsyncTask<Void, Void, Void>() {


        @Override
        protected Void doInBackground(Void... params) {

            try {
                MobileServiceTable qTable=qTable = mClient.getTable(EarthquakeData.class);
                final MobileServiceList<EarthquakeData> result = (MobileServiceList<EarthquakeData>) qTable.execute().get(); //where().field("complete").eq(false).
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //mAdapter.clear();

                        for (EarthquakeData item : result) {
                            //mAdapter.add(item)
                            String strReg = item.Region;
                            long mag = (long) item.Magnitude;
                            markCircle(strReg, mag);
                        }
                    }
                });
            } catch (Exception exception) {
                //createAndShowDialog(exception, "Error");
                Log.e("error retrieve", "error in blah blah");
            }
            return null;
        }
        }.execute();

        Log.d("database","finish");
//
    }

    /*private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        //updateLocationUI();

    }*/

}
