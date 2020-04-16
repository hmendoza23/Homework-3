package com.example.maptest;

//import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

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

        search = findViewById(R.id.searchbar);
        searchbtn = findViewById(R.id.searchbtn);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText;
                searchText = search.getText().toString();


            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };

        CardView cardView = findViewById(R.id.card_view);
        cardView.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.previousSearches);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(getApplicationContext(), addresses, listener);
        recyclerView.setAdapter(myAdapter);

        addresses.add("shit1");
        addresses.add("shit2");
        addresses.add("shit3");
        addresses.add("shit4");
        addresses.add("shit5");
        addresses.add("shit6");
        addresses.add("shit");
        addresses.add("shit");
        addresses.add("shit");
        addresses.add("shit");
        addresses.add("shit");
        addresses.add("shit");




        myAdapter.notifyDataSetChanged();
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
}
