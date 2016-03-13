package ua.ukraine.ukrroad.ukrroad.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import ua.ukraine.ukrroad.ukrroad.R;
import ua.ukraine.ukrroad.ukrroad.database.table.Issue;
import ua.ukraine.ukrroad.ukrroad.helpers.HelperFactory;

public class MapsActivity extends FragmentActivity implements LocationListener {
    SupportMapFragment mapFragment;
    GoogleMap map;
    Location myCoordinates;
    Location location = null;
    static String nameAddress = null;
    EditText mapsAddress;
    ImageButton imageButton;
    Button button;
    String idIssue;
    Issue issue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapsAddress = (EditText)findViewById(R.id.mapsAddressEditText);
        imageButton = (ImageButton)findViewById(R.id.mapsAddressImageButton);
        button = (Button)findViewById(R.id.mapsAddressButton);
        idIssue = getIntent().getStringExtra(getResources().getString(R.string.IDISUE));
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        if (map == null) {
            finish();
            return;
        }
        init();

    }

    private void init() {
        myCoordinates = getLocation();
        showCoordinates(new LatLng(myCoordinates.getLatitude(), myCoordinates.getLongitude()));
        map.setMyLocationEnabled(true);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                showCoordinates(latLng);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = mapsAddress.getText().toString();
                if (address.isEmpty()) {
                    Toast.makeText(MapsActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                    myCoordinates = getLocation();
                    showCoordinates(new LatLng(myCoordinates.getLatitude(), myCoordinates.getLongitude()));
                } else {
                    showCoordinates(getCoordinatesFromAddress(address));
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = mapsAddress.getText().toString();
                if (address.isEmpty()) {
                    Toast.makeText(MapsActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        issue = HelperFactory.getHelper().getIssueDAO().getIssueById(Integer.parseInt(idIssue));
                        issue.setAddress(address);
                        HelperFactory.getHelper().getIssueDAO().updateIssue(issue);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    onBackPressed();
                }
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
        showCoordinates(new LatLng(location.getLatitude(), location.getLongitude()));
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

    public Location getLocation() {
        LocationManager mLocationManager;
        try {
            mLocationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
            boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(getApplicationContext(), "On GPS, please", Toast.LENGTH_SHORT).show();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (isNetworkEnabled) {
                            if (mLocationManager != null) {
                                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
                                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            }
                        } else if (isGPSEnabled) {
                            if (location == null) {
                                if (mLocationManager != null) {
                                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
                                    location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                }
                            }
                        }
                    }
                }
                else {
                    if (isNetworkEnabled) {
                        if (mLocationManager != null) {
                            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
                            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        }
                    } else if (isGPSEnabled) {
                        if (location == null) {
                            if (mLocationManager != null) {
                                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
                                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public void showCoordinates(LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(getAddressFromCoordinates(latLng));
        markerOptions.snippet(latLng.latitude + " " + latLng.longitude);
        map.clear();
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        map.addMarker(markerOptions).showInfoWindow();
    }

    public String getAddressFromCoordinates(LatLng latLng) {
        try {
            nameAddress = new AddressFromCoordinates() {}.execute(latLng).get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
        mapsAddress.setText(nameAddress);
        return nameAddress;

    }

    public LatLng getCoordinatesFromAddress(String address){

        try {
            return new CoordinatesFromAddress() {}.execute(address).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}