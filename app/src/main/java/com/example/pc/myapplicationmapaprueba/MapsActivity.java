package com.example.pc.myapplicationmapaprueba;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String[] datos = null;

    private Intent Graba, Recibe;
    private String latitud, longitud, fecha;



    private final String NAMESPACE = "http://rastreriza.esy.es";
    // private final String NAMESPACE = "http://192.168.1.10/receta_soap_wdsl";
    // private final String URL = "http://192.168.1.10/receta_soap_wdsl/servicio_con_wdsl.php?wsdl";
    private final String URL = "http://rastreriza.esy.es/servicio_con_wdsl.php?wsdl";
    private final String SOAP_ACTION_PREFIX = "/";
    private final String METHOD_NAME_1 = "obtener_todas_las_coordenadas";

    String[] Coordenadas;

    private String TAG = "Depuración:";

    private String[][] Vcoordenadas;
    private double lat, lon;

    private String matriz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_fragment_activity);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(36.80, -2.58);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));



    }

    private void dibujarLineas() {

        String[] coordenadas=new String[ReceptorActivity.Coordenadas.length()];
        String[] matriz=ReceptorActivity.Coordenadas.split("|");

        for (int i=0;i<matriz.length;i++)
        {
            coordenadas[i]=matriz[i].split(" ")[1]+" "+matriz[i].split(" ")[2];
        }

        try {
            // Dibujo con Lineas
            /*PolylineOptions lineas = new PolylineOptions()
                    .add(new LatLng(45.0, -12.0)).add(new LatLng(45.0, 5.0))
                    .add(new LatLng(34.5, 5.0)).add(new LatLng(34.5, -12.0))
                    .add(new LatLng(45.0, -12.0));*/
            Polyline line=null;
            for (int i = 0; i < coordenadas.length; i=i+2) {
                LatLng src = new LatLng(Double.parseDouble(coordenadas[i].split(" ")[0]),Double.parseDouble(coordenadas[i].split(" ")[1]));
                LatLng dest=new LatLng(Double.parseDouble(coordenadas[i+1].split(" ")[0]),Double.parseDouble(coordenadas[i+1].split(" ")[1]));

                // mMap is the Map Object
                 line = mMap.addPolyline(
                        new PolylineOptions().add(
                                new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude,dest.longitude)
                        ).width(2).color(Color.BLUE).geodesic(true)
                );
            }

            /*lineas.width(8);
            lineas.color(Color.RED);*/

           // mMap.addPolyline(line);
        } catch (Exception e) {
            e.printStackTrace();
        }

//		// Dibujo con polígonos
//		PolygonOptions rectangulo = new PolygonOptions().add(new LatLng(45.0,
//				-12.0), new LatLng(45.0, 5.0), new LatLng(34.5, 5.0),
//				new LatLng(34.5, -12.0), new LatLng(45.0, -12.0));
//
//		rectangulo.strokeWidth(8);
//		rectangulo.strokeColor(Color.RED);
//
//		mapa.addPolygon(rectangulo);
    }


    /////////////////////////////////////////////////////



}
