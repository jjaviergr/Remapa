package com.example.pc.myapplicationmapaprueba;

import android.content.Intent;
import android.graphics.Color;
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

public class TestFragment extends Fragment {
    private SupportMapFragment fragment;
    private GoogleMap map=null;
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
            map.addMarker(new MarkerOptions().position(new LatLng(36.809377810570936, -2.5822493709454135)));
            CameraUpdate camUpd;

            camUpd = CameraUpdateFactory.newLatLng(new LatLng(36.809377810570936, -2.5822493709454135));
            map.moveCamera(camUpd);
            // Zoom in the Google Map
            map.animateCamera(CameraUpdateFactory.zoomTo(16));

          /*  LatLng src = new LatLng(Double.parseDouble("36.809377810570936"), Double.parseDouble("-2.5822493709454135"));
            LatLng dest = new LatLng(Double.parseDouble("36.808646689010835"), Double.parseDouble("-2.581127027599894"));
            PolylineOptions lineas =new PolylineOptions()
                    .add(new LatLng(src.latitude, src.longitude))
                    .add(new LatLng(dest.latitude, dest.longitude));
            lineas.width(8);
            lineas.color(Color.RED);
            map.addPolyline(lineas);*/
        }






        /*    String myValue = this.getArguments().getString("datos");
        if (myValue!=null) {
            et = (TextView) getActivity().findViewById(R.id.textView3);
            et.setText(myValue.length());

            myValue = this.getActivity().getIntent().getDataString();

            Toast toast = Toast.makeText(getActivity(), myValue, Toast.LENGTH_LONG);
            toast.show();
        }
        else{

        Toast toast = Toast.makeText(getActivity(), "Sin datos", Toast.LENGTH_LONG);
        toast.show();}*/

    }


    public void onStart() {
        super.onStart();
        Button Salir = (Button) getActivity().findViewById(R.id.button4);

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

        Button Recibir = (Button) getActivity().findViewById(R.id.button3);
        Recibir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent recibe = new Intent(getActivity(), ReceptorActivity.class);
                startActivity(recibe);
            }
        });

        Button Ver_Mapa = (Button) getActivity().findViewById(R.id.button2);

        Recibir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent recibe = new Intent(getActivity(), ReceptorActivity.class);
                // startActivity(recibe);

                try {


                    String cadena = ReceptorActivity.Coordenadas;
                    // String texto="Longitud de cadena"+cadena.length()+" nº coordenadas "+ cadena.split("\\|").length;
                    // t.setText(String.valueOf(cadena.split("\\|").length));
                    /////filtro solo coordenadas///
                    String[] coordenadas = cadena.split(" ");


                    String[] otrascoordenadas = new String[coordenadas.length];

                    //t.setText(coordenadas[0] + " " + coordenadas[1] + " " + coordenadas[2] + " " + coordenadas[3] + " " + coordenadas[4]);
                    String cad = "";
                    for (int i = 2; i < coordenadas.length; i = i + 6) {
                        cad += coordenadas[i] + " " + coordenadas[i + 1] + " ";
                        //otrascoordenadas[i] = coordenadas[i].split(" ")[1]+" "+coordenadas[i].split(" ")[2]+" "; //Ya he separado todas coordenadas y sus 4 campos.
                    }
                    //t.setText(cad);
                    dibujarLineas(cad);
      /*  Toast toast = Toast.makeText(getActivity(), datos.length(), Toast.LENGTH_SHORT);
        toast.show();*/
                } catch (Exception e) {
                    //  Toast toast = Toast.makeText(getActivity(), datos., Toast.LENGTH_SHORT);
                    //  toast.show();

                    t.setText("Excepcion al filtrar coordenadas :" + e.getMessage().toString());
                }

            }
        });

    }

    private void dibujarLineas(String coordenadas) {
        int contador = 0;
        String cadena = "";
        PolylineOptions lineas;
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
            int numero_pares = Math.round(coord.length / 4);
            int numero_despreciamos = coord.length % 4;
            cadena+="numero de datos :"+coord.length+" ";
            cadena+="numero de puntos(4 datos) :" + Math.round(coord.length / 4)+" ";
            cadena+="numero de puntos despreciados "+(coord.length % 4)+" ";
            Polyline line = null;
            LatLng src = null;
            LatLng dest = null;

            for (int i = numero_despreciamos; i < coord.length; i = i + 4) {

                try {
                    if ((coord[0] != "") && (coord[1] != "")&&(coord[0] != " ") && (coord[1] != " ")) {
                        src = new LatLng(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
                    }
                } catch (Exception e)
                {
                    cadena+="fallo al parsear "+e.getMessage().toString();
                }
                try {
                    if ((coord[2] != "") && (coord[3] != "")&&(coord[2] != " ") && (coord[3] != " ")) {
                        dest = new LatLng(Double.parseDouble(coord[2]), Double.parseDouble(coord[3]));
                    }
                } catch (Exception e)
                {
                    cadena+="fallo al parsear "+e.getMessage().toString();
                }

                map = fragment.getMap();
                //        contador++;

                try {
                    if ((src != null) && (dest != null)) {
                        // mMap is the Map Object
                         lineas =new PolylineOptions()
                                .add(new LatLng(src.latitude, src.longitude))
                                .add(new LatLng(dest.latitude, dest.longitude));
                        lineas.width(8);
                        lineas.color(Color.RED);

                        contador++;
                    }
                } catch (Exception e) {
                    cadena+=e.getMessage().toString();
                }
            }
            cadena+="nº de polylineas "+contador;
            //      t.setText(contador);
            /*lineas.width(8);
            lineas.color(Color.RED);*//*
*/
            // mMap.addPolyline(line);
        } catch (Exception e) {
            t.setText("Excepcion pintando lineas :" + e.getMessage().toString());
        }
        map.addPolyline(lineas);
        t.setText(cadena+" Nº de vueltas :" + contador);
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


