package com.example.pc.myapplicationmapaprueba;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CustomMapFragment extends com.google.android.gms.maps.SupportMapFragment implements OnMapReadyCallback {

    private final LatLng HAMBURG = new LatLng(53.558, 9.927);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GoogleMap googleMap = getMap();
        googleMap.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(36.80, -2.58);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Zoom in the Google Map
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(40));



        String otroValue = this.getArguments().getString("messagef1");

        Toast toast = Toast.makeText(getActivity(), otroValue, Toast.LENGTH_SHORT);
        toast.show();

    }

}
