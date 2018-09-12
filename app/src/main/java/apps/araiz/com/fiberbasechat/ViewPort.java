package apps.araiz.com.fiberbasechat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ViewPort extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClent;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    private Button msearchbtn;
    FirebaseUser mcurrentuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_port);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap= googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          return;
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);

        }

        protected synchronized void buildGoogleApiClient()
        {

            mGoogleApiClent = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClent.connect();

        }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mcurrentuser = FirebaseAuth.getInstance().getCurrentUser();


        String user_id = mcurrentuser.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Location");

        GeoFire geoFire = new GeoFire(databaseReference);
        geoFire.setLocation(user_id,new GeoLocation(location.getLatitude(),location.getLongitude()));
        if(!friedcircleStarted)


            friendcircle();








    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClent, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();



           //To remove the location when the user comes out of the application

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Location");

        GeoFire geoFire = new GeoFire(databaseReference);
        geoFire.removeLocation(user_id);


    }



    //make a list of marker
    boolean friedcircleStarted =false;
    List<Marker> markerList = new ArrayList<Marker>();



    private void friendcircle(){
        friedcircleStarted = true;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Location");
         //reference within database ref for geofie
        GeoFire geoFire = new GeoFire(databaseReference);
        //to set a radius arounf=d the current location i.e current user location
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude()),10000);
        //event listener
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {

               //ieration to look through the marker list
                for(Marker markerIt : markerList){

                    if(markerIt.getTag().equals(key))
                        return;


                }

                LatLng mfriendlocation = new LatLng(location.latitude,location.longitude);

                Marker  mFriendMarker = mMap.addMarker(new MarkerOptions().position(mfriendlocation));
                mFriendMarker.setTag(key);
                //add marker to list
                markerList.add(mFriendMarker);

            }

            @Override
            public void onKeyExited(String key) {
                //to remove the marker
                for(Marker markerIt : markerList){
                    if(markerIt.getTag().equals(key))
                    {
                        markerIt.remove();
                        markerList.remove(markerIt);
                        return;
                    }

                }


            }

            @Override
            public void onKeyMoved(String key, GeoLocation location)
            {

                for(Marker markerIt : markerList)
                {
                    if(markerIt.getTag().equals(key))
                    {
                       markerIt.setPosition(new LatLng(location.latitude,location.longitude));

                    }
                }
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }

}
