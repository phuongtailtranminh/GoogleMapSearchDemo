package com.example.phuon.googlemapsearchdemo;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "PhuongTM";
    private GoogleMap mMap;
    private Marker marker;
    private DataContext dbContext = null;
    private TextView txtName;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtOrganizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbContext = new DataContext(this);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnHistory = (Button) findViewById(R.id.btnHistory);
        Button btnClear = (Button) findViewById(R.id.btnClear);

        txtName = (TextView) findViewById(R.id.txtName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtOrganizer = (TextView) findViewById(R.id.txtOrganizer);


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbContext.clearData()) {
                    new AlertDialog.Builder(MainActivity.this).setMessage("All data has been deleted").show();
                } else {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Delete error").show();
                }
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Event> listEvents = dbContext.getAllEvents();
                int size = listEvents.size();
                if (size == 0) {
                    new AlertDialog.Builder(MainActivity.this).setMessage("No Record Found").show();
                } else {
                    int lastIndex = size - 1;

                    String name = listEvents.get(lastIndex).getName();
                    String date = listEvents.get(lastIndex).getDate();
                    String time = listEvents.get(lastIndex).getTime();
                    String organizer = listEvents.get(lastIndex).getOrganizer();
                    String lat = listEvents.get(lastIndex).getLat();
                    String lng = listEvents.get(lastIndex).getLng();

                    String msg = String.format("Name = [%s]\nDate = [%s]\nTime = [%s]\nOrganizer = [%s\n(lat, long) = [%s, %s]",
                            name, date, time, organizer, lat, lng);

                    new AlertDialog.Builder(MainActivity.this).setMessage(msg).show();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marker != null) {

                    LatLng pos = marker.getPosition();
                    new AlertDialog.Builder(MainActivity.this).setMessage(pos.toString()).show();

                    String name = txtName.getText().toString();
                    String date = txtDate.getText().toString();
                    String time = txtTime.getText().toString();
                    String organizer = txtOrganizer.getText().toString();
                    String lat = String.valueOf(pos.latitude);
                    String lng = String.valueOf(pos.longitude);

                    boolean rs = dbContext.insertEvent(new Event(name, date, time, organizer, lat, lng));
                    if (rs) {
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Load map
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Load suggestion box
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                LatLng latLng = place.getLatLng();
                CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);

                mMap.clear();
                marker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
    }
}
