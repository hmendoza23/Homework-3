package com.example.maptest;

//import android.support.v4.app.FragmentActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Objects;

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


        //Function to click on card view and plot a previously searched address
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                LatLng latLng = getLocationFromAddress(addresses.get(position));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(addresses.get(position));
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                markerOptions.position(latLng);
                mMap.clear();
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
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

        //Function to search address based on searchbar
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText;
                searchText = search.getText().toString();

                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);

                LatLng latLng = getLocationFromAddress(searchText);

                if(latLng != null) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.title(searchText);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    markerOptions.position(latLng);

                    mMap.clear();
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    addresses.add(searchText);
                    myAdapter.notifyDataSetChanged();
                    cardView.setVisibility(View.VISIBLE);
                }
                else{
                    search.clearComposingText();
                    showAlertDialog(view);
                }

            }
        });





    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    /* call back interface */
    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }

    //Function to convert address into longitude and latitude for plotting map and marker
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

    //Alert dialog for when address is invalid, or not entered
    public void showAlertDialog(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error");
        alert.setMessage("Invalid Address!");
        alert.setNeutralButton("okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MapsActivity.this,"Re-enter address", Toast.LENGTH_SHORT);
            }
        });
        alert.create().show();
    }

}
