package com.example.gps.gps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.logging.Handler;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private TextView salida, no;
    private EditText nombreT;
    private Button bregistrar, bstart, bstop, bcapturas,bhigh,bmedium;
    private LocationManager mLocationManager;
    private String nombre = "";
    private bdManager bd;
    Cursor cursor;
    private int entro = 0;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.;
    Location myLocation;
    LocationManager locationManager;

    // Creating a criteria object to retrieve provider
    Criteria criteria = new Criteria();

    // Getting the name of the best provider
    String provider;
    /*LocationRequest mLocationRequest = new LocationRequest();
    GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addApi(Drive.API)
            .addScope(Drive.SCOPE_FILE)
            .build();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        salida = (TextView) findViewById(R.id.textView2);
        salida.setText("");
        nombreT = (EditText) findViewById(R.id.editText);
        no = (TextView) findViewById(R.id.textView3);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        bregistrar = (Button) findViewById(R.id.button4);
        bstart = (Button) findViewById(R.id.button);
        bstop = (Button) findViewById(R.id.button2);
        bcapturas = (Button) findViewById(R.id.button3);
        bhigh =(Button)findViewById(R.id.button6);
        bmedium =(Button)findViewById(R.id.button5);
        bd = new bdManager(this);
        cursor = bd.cargardb();
        setUpMapIfNeeded();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void registrar(View view) {
        nombre = nombreT.getText().toString();
        nombreT.setVisibility(View.GONE);
        no.setVisibility(View.GONE);
        bregistrar.setVisibility(View.GONE);
        bstart.setVisibility(View.VISIBLE);
        bstop.setVisibility(View.VISIBLE);
        bcapturas.setVisibility(View.VISIBLE);
        bhigh.setVisibility(View.VISIBLE);
        bmedium.setVisibility(View.VISIBLE);

    }

    public void capturas(View view) {
        Intent intent = new Intent(MainActivity.this, MainActivity2Activity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void onLocationChanged(Location location) {
        salida.setText("Latitud : "+location.getLatitude()+"\r\n Longitud : "+location.getLongitude()+"");
       // bd.insertarvalor(nombre,""+location.getLatitude(),""+location.getLongitude());
    }*/


    public void StartGPS(View view) {
        setUpMapIfNeeded();
        //     mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,this);
        entro = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        locationManager.requestLocationUpdates(provider, 0L, 1f, (LocationListener) this);
        onLocationChanged(myLocation);
    }


    public void Startmedium(View view) {
        setUpMapIfNeededM();
        //     mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,this);
        entro = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        locationManager.requestLocationUpdates(provider, 0L, 1f, (LocationListener) this);
        onLocationChanged(myLocation);
    }


    public void Starthigh(View view) {
        setUpMapIfNeededh();
        //     mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,this);
        entro = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        locationManager.requestLocationUpdates(provider, 0L, 1f, (LocationListener) this);
        onLocationChanged(myLocation);
    }




    public void StopGPS(View view) {
        // mLocationManager.removeUpdates(this);
        salida.setText("");
        nombre = "";
        nombreT.setVisibility(View.VISIBLE);
        no.setVisibility(View.VISIBLE);
        bregistrar.setVisibility(View.VISIBLE);
        bstart.setVisibility(View.GONE);
        bstop.setVisibility(View.GONE);
        bcapturas.setVisibility(View.GONE);
        bhigh.setVisibility(View.GONE);
        bmedium.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        onLocationChanged(myLocation);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMapIfNeededM() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMapM();
            }
        }
    }

    private void setUpMapIfNeededh() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMaph();
            }
        }
    }
    private void setUpMap() {

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Get the name of the best provider
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            else
            {

            }
        }
        myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Get latitude of the current location
        if(myLocation!=null ) {
            double latitude = myLocation.getLatitude();
            // Get longitude of the current location
            double longitude = myLocation.getLongitude();
            // Create a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);
            // Show the current location in Google Map
            Toast.makeText(getApplicationContext(), "" + latitude + " " + longitude, Toast.LENGTH_LONG).show();
            salida.setText("Latitud : " + latitude + "\r\n Longitud : " + longitude + "");

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            // Zoom in the Google Map
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
            startLocationUpdates();
        }
    }



    private void setUpMapM() {

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Get the name of the best provider
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            else
            {

            }
        }
        myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Get latitude of the current location
        if(myLocation!=null ) {
            double latitude = myLocation.getLatitude();
            // Get longitude of the current location
            double longitude = myLocation.getLongitude();
            // Create a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);
            // Show the current location in Google Map
            Toast.makeText(getApplicationContext(), "" + latitude + " " + longitude, Toast.LENGTH_LONG).show();
            salida.setText("Latitud : " + latitude + "\r\n Longitud : " + longitude + "");

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            // Zoom in the Google Map
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
            startLocationUpdates();
        }
    }


    private void setUpMaph() {

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Get the name of the best provider
        criteria.setAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            else
            {

            }
        }
        myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Get latitude of the current location
        if(myLocation!=null ) {
            double latitude = myLocation.getLatitude();
            // Get longitude of the current location
            double longitude = myLocation.getLongitude();
            // Create a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);
            // Show the current location in Google Map
            Toast.makeText(getApplicationContext(), "" + latitude + " " + longitude, Toast.LENGTH_LONG).show();
            salida.setText("Latitud : " + latitude + "\r\n Longitud : " + longitude + "");

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            // Zoom in the Google Map
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
            startLocationUpdates();
        }
    }




    private void centerMap(LatLng mapCenter) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mapCenter));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        // mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located"));
    }

    /* @Override
     public void onLocationChanged(Location location){
         Toast.makeText(MainActivity.this, "onLocationChanged", Toast.LENGTH_LONG).show();
         centerMap(new LatLng(location.getLatitude(),location.getLongitude()));
         salida.setText("Latitud : "+location.getLatitude()+"\r\n Longitud : "+location.getLongitude()+"");
         bd.insertarvalor(nombre,""+location.getLatitude(),""+location.getLongitude());
     }*/
    @Override
    public void onLocationChanged(Location location) {
       if(location!=null) {
           Toast.makeText(MainActivity.this, "onLocationChanged", Toast.LENGTH_LONG).show();
           centerMap(new LatLng(location.getLatitude(), location.getLongitude()));
           salida.setText("Latitud : " + location.getLatitude() + "\r\n Longitud : " + location.getLongitude() + "");
           bd.insertarvalor(nombre, "" + location.getLatitude(), "" + location.getLongitude());
           //  onLocationChanged(location);
       }

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

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(MainActivity.this, "1", Toast.LENGTH_LONG).show();
            startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(MainActivity.this, "2", Toast.LENGTH_LONG).show();
        startLocationUpdates();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    protected void startLocationUpdates() {
       // LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        createLocationRequest();
    }
    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
}
