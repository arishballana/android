package com.markwebtechnologies.lupus;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class display_locations extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Map_Data> map_locations = new ArrayList<Map_Data>();
    boolean loadingMore;
    private Handler mHandler = null;
    private ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_locations);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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


//        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
//        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.blog_activity_layout);
//        relativeLayout.setAnimation(animation);

        setLocations();

//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(Marker marker) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                return null;
//            }
//        });
//
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            Marker currentshown=null;
            @Override
            public boolean onMarkerClick(Marker marker) {
             System.out.println("in listener");
                if(marker.equals(currentshown)){
                    //marker.hideInfoWindow();
                    marker.showInfoWindow();
                    currentshown=null;
                }else{
                    marker.showInfoWindow();
                    currentshown=marker;
                }
                return true;
            }
        });
    }


    private void setLocations() {
        try {

            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Json_Data_Fetch h = new Json_Data_Fetch(display_locations.this);
                    try {
                        System.out.println("i am in try");
                        if (Utility.isOnline(display_locations.this)) {
                            System.out.println("I AM WITH UTILITY");
                            map_locations = h.getMapLocations("map-locations");

                            if (map_locations == null) {

                                System.out.println("I AM NOT IN RUNNABLE");
                                Log.d("albumlist is null", "-111");
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(display_locations.this, "No Data Found", Toast.LENGTH_LONG).show();
                                        loadingMore = false;
                                    }
                                });

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        ArrayList<holder_view> holders = new ArrayList<holder_view>();
                                        Map_Adapter m = new Map_Adapter();
                                        holders = m.Map_Adapter(map_locations);
                                        if (holders != null && holders.size() != 0) {
                                            for (int i = 0; i < holders.size(); i++) {
                                                holder_view ho = holders.get(i);

                                                System.out.println(ho.latitude+"  "+ho.longitude);
                                                LatLng malout = new LatLng(ho.latitude, ho.longitude);
                                                mMap.addMarker(new MarkerOptions().position(malout).title("LUPUS").snippet(Html.fromHtml(ho.desc).toString()));
                                                mMap.moveCamera(CameraUpdateFactory.newLatLng(malout));

                                            }
                                        }
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(display_locations.this,
                                            "No Network Detected",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
