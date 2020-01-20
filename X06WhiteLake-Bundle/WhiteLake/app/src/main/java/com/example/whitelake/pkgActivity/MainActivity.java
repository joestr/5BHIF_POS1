package com.example.whitelake.pkgActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitelake.R;
import com.example.whitelake.pkgData.Database;
import com.example.whitelake.pkgData.DistanceBetween;
import com.example.whitelake.pkgData.SpatialPosition;
import com.example.whitelake.pkgMisc.SpatialConstants;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLoadShips;
    private Button buttonShowDistance;
    private Spinner spinnerLocations;
    private Spinner spinnerShips;
    private TextView textViewMessages;
    private ArrayAdapter<String> shipsAdapter;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initializeViews();
        database = Database.newInstance("http://192.168.191.130:8080");
    }

    private void initializeViews() {
        buttonLoadShips.setOnClickListener(this);
        buttonShowDistance.setOnClickListener(this);
        shipsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        spinnerShips.setAdapter(shipsAdapter);
    }

    private void findViews() {
        buttonLoadShips = this.findViewById(R.id.buttonLoadShips);
        buttonShowDistance = this.findViewById(R.id.buttonShowDistance);
        spinnerLocations = this.findViewById(R.id.spinnerLocations);
        spinnerShips = this.findViewById(R.id.spinnerShips);
        textViewMessages = this.findViewById(R.id.textViewMessages);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        try {
            switch (id) {
                case R.id.buttonLoadShips:
                    ArrayList<String> availableShips = database.getAvailableShips();
                    shipsAdapter.clear();
                    shipsAdapter.addAll(availableShips);
                    textViewMessages.setText("Got " + availableShips.size() + " ships");
                    break;
                case R.id.buttonShowDistance:
                    String selectedLocation = spinnerLocations.getSelectedItem().toString();
                    String selectedShip = spinnerShips.getSelectedItem().toString();
                    SpatialPosition spatialPosition = null;
                    switch (selectedLocation) {
                        case "Techendorf":
                            spatialPosition = SpatialConstants.TECHENDORF;
                            break;
                        case "Paternzipf":
                            spatialPosition = SpatialConstants.PATENZIPF;
                            break;
                        case "Dolomitenblick":
                            spatialPosition = SpatialConstants.DOLOMITENBLICK;
                            break;
                        case "Ronacher Fels":
                            spatialPosition = SpatialConstants.RONACHER_FELS;
                            break;
                    }
                    DistanceBetween distanceBetween = database.getDistanceBetween(selectedShip, spatialPosition);
                    textViewMessages.setText("distance calculated:" + new Gson().toJson(distanceBetween));
                    break;
            }
        } catch (Exception ex) {
            textViewMessages.setText(ex.getMessage());
            Toast.makeText(this, "Exception in main activity: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
