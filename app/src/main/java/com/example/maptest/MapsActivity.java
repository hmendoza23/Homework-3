package com.example.maptest;

//import android.support.v4.app.FragmentActivity;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText search;
    private Button searchbtn;

    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private ArrayList<String> addresses = new ArrayList<>();
    MapsViewModel mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);


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

        mapsViewModel.getAddressData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

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
        recyclerView = findViewById(R.id.previousSearches);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(getApplicationContext(), addresses, listener);
        recyclerView.setAdapter(myAdapter);


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

    private class FindAddress extends AsyncTask<Object, String, String> {

        String googleAddressData;
        String url;

        @Override
        protected String doInBackground(Object... objects) {

            url = (String) objects[1];

            DownloadUrl downloadUrl = new DownloadUrl();
            try{
                googleAddressData = downloadUrl.readUrl(url);
            }catch(IOException e){
                e.printStackTrace();
            }

            return googleAddressData;
        }


        @Override
        protected void onPostExecute(String s){
            if(s != null){
                mapsViewModel.postAddressData(s);
            }
            else{
                System.out.println("sorry did find it");
            }

        }


    }

}
