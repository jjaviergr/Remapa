package com.example.pc.myapplicationmapaprueba;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class TestFragment extends Fragment {
    private SupportMapFragment fragment;
    private GoogleMap map = null;
    private String datos;
    TextView t;
    private TextView et;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_test_fragment, container, false);


        CustomMapFragment mapFragment = new CustomMapFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map_container, mapFragment).commit();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        t = (TextView) getActivity().findViewById(R.id.textView3);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, fragment).commit();
        }


    }

    @Override
    public void onResume() {
        super.onResume();


        if (map == null) {
            map = fragment.getMap();
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            CameraUpdate camUpd;
            camUpd = CameraUpdateFactory.newLatLng(new LatLng(36.80, -2.58));
            map.moveCamera(camUpd);
           // map.addMarker(new MarkerOptions().position(new LatLng(36.809377810570936, -2.5822493709454135)));
           /* CameraUpdate camUpd;

            camUpd = CameraUpdateFactory.newLatLng(new LatLng(36.809377810570936, -2.5822493709454135));
            map.moveCamera(camUpd);
            // Zoom in the Google Map
            map.animateCamera(CameraUpdateFactory.zoomTo(16));*/

        }
    }


    public void onStart() {
        super.onStart();
        Button Salir = (Button) getActivity().findViewById(R.id.button12);

        Salir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        Button Grabar = (Button) getActivity().findViewById(R.id.button5);
        Grabar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Graba = new Intent(getActivity(), Grabar_Coordenadas.class);
                startActivity(Graba);
            }
        });

        Button Ver_Coord = (Button) getActivity().findViewById(R.id.button2);
        Ver_Coord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent recibe = new Intent(getActivity(), ReceptorActivity.class);
                startActivity(recibe);
            }
        });

        Button Ver_Mapa = (Button) getActivity().findViewById(R.id.button3);
        Ver_Mapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent recibe = new Intent(getActivity(), ReceptorActivity.class);
                // startActivity(recibe);

                try {


                    String cadena = ReceptorActivity.Coordenadas;
                    String[] coordenadas = cadena.split(" ");

                    String cad = "";
                    for (int i = 2; i < coordenadas.length; i = i + 6) {
                        cad += coordenadas[i] + " " + coordenadas[i + 1] + " ";

                    }
                    dibujarLineas(cad);
                } catch (Exception e) {

                     //t.setText("Excepcion al filtrar coordenadas :" + e.getMessage().toString());
                }

            }
        });

    }

    private void dibujarLineas(String coordenadas) {
        int contador = 0;
        String cadena = "";
        PolylineOptions lineas;
        ArrayList<LatLng> Lista_Puntos = null;
        try {
            // Dibujo con Lineas
            map = fragment.getMap();
            /*LatLng src = new LatLng(Double.parseDouble("36.809377810570936"), Double.parseDouble("-2.5822493709454135"));
            LatLng dest = new LatLng(Double.parseDouble("36.808646689010835"), Double.parseDouble("-2.581127027599894"));
            PolylineOptions lineas =new PolylineOptions()
                    .add(new LatLng(src.latitude, src.longitude))
                    .add(new LatLng(dest.latitude, dest.longitude));
            lineas.width(8);
            lineas.color(Color.RED);
            map.addPolyline(lineas);*/


            String[] coord = coordenadas.split(" ");

            CameraUpdate camUpd;

            camUpd = CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(coord[0]), Double.parseDouble(coord[ 1])));
            map.moveCamera(camUpd);
            // Zoom in the Google Map
            map.animateCamera(CameraUpdateFactory.zoomTo(16));


            Polyline line = null;
            Lista_Puntos = new ArrayList<LatLng>();


            for (int i = 0; i < coord.length; i = i + 2) {
                Lista_Puntos.add(new LatLng(Double.parseDouble(coord[i]), Double.parseDouble(coord[i + 1])));

                map.addMarker(new MarkerOptions().position(Lista_Puntos.get(Lista_Puntos.size() - 1)));
                contador++;
            }
            cadena += "Nº de puntos :" + contador;


            float contador_distancia = 0;
            for (int i = Lista_Puntos.size()%4; i < Lista_Puntos.size(); i = i + 4) {
                Location loc1 = new Location("");
                loc1.setLatitude(Lista_Puntos.get(i).latitude);
                loc1.setLongitude(Lista_Puntos.get(i + 1).longitude);

                Location loc2 = new Location("");
                loc2.setLatitude(Lista_Puntos.get(i + 2).latitude);
                loc2.setLongitude(Lista_Puntos.get(i + 3).longitude);

                contador_distancia += loc1.distanceTo(loc2);
            }

            cadena += "\nDistancia recorrida :" + contador_distancia + " metros";
            t.setText(cadena);

        } catch (Exception e) {
            t.setText("Excepcion pintando lineas :" + e.getMessage().toString());
        }


    }


    /////////////////////////////////////////////////////


}


