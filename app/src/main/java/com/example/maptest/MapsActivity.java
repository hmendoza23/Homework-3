package com.example.maptest;

//import android.support.v4.app.FragmentActivity;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText search;
    private Button searchbtn;

    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private ArrayList<String> addresses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                LatLng latLng = getLocationFromAddress(addresses.get(position));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(addresses.get(position));
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                markerOptions.position(latLng);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            }
        };

        final CardView cardView = findViewById(R.id.card_view);
        recyclerView = findViewById(R.id.previousSearches);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(getApplicationContext(), addresses, listener);
        recyclerView.setAdapter(myAdapter);



        search = findViewById(R.id.searchbar);
        searchbtn = findViewById(R.id.searchbtn);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText;
                searchText = search.getText().toString();

                LatLng latLng = getLocationFromAddress(searchText);

                if(latLng != null) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.title(searchText);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    markerOptions.position(latLng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                    addresses.add(searchText);
                    myAdapter.notifyDataSetChanged();
                    cardView.setVisibility(View.VISIBLE);
                }
                else{
                    /*  ALERT DIALOG SHIT  */
                }

            }
        });





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
        LatLng sydney = new LatLng(34.0522, 118.2437);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /* call back interface */
    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }

    public LatLng getLocationFromAddress(String strAddress){
        Geocoder coder = new Geocoder(getBaseContext());
        List<Address> addresses;

        try {
            addresses = coder.getFromLocationName(strAddress, 1);
            if (addresses == null) {
                return null;
            }
            Address location = addresses.get(0);
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            LatLng latLng = new LatLng(lat, lng);
            return latLng;
        } catch (Exception e) {
            return null;
        }
    }


}
