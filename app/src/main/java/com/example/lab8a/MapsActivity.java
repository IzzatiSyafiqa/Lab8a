package com.example.lab8a;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng tamWorth = new LatLng(-31.083332, 150.916672);
        LatLng newCastle = new LatLng(-32.916668, 151.750000);
        LatLng brisbane = new LatLng(-27.470125, 153.021072);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(tamWorth).title("Marker in Tamworth"));
        mMap.addMarker(new MarkerOptions().position(newCastle).title("Marker in Newcastle"));
        mMap.addMarker(new MarkerOptions().position(brisbane).title("Marker in Brisbane"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 6f));

        googleMap.setMapType(
                GoogleMap.MAP_TYPE_HYBRID);

        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        sydney,10));
    }

    public void onMapSearch(View view){

        EditText locationSearch =
                findViewById(R.id.editText);

        String location =
                locationSearch.getText().toString();

        List<Address> addressList = null;

        if(location != null && !location.isEmpty()){

            Geocoder geocoder =
                    new Geocoder(this);

            try{
                addressList =
                        geocoder.getFromLocationName(
                                location,1);

            }catch(IOException e){
                e.printStackTrace();
            }

            if (addressList == null || addressList.isEmpty()) {
                return;
            }

            Address address = addressList.get(0);

            LatLng latLng =
                    new LatLng(
                            address.getLatitude(),
                            address.getLongitude());

            mMap.addMarker(
                    new MarkerOptions()
                            .position(latLng)
                            .title("Marker"));

            mMap.animateCamera(
                    CameraUpdateFactory
                            .newLatLngZoom(latLng,18));

        }
    }
}