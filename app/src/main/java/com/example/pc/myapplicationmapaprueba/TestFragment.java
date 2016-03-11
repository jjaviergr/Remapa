package com.example.pc.myapplicationmapaprueba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TestFragment extends Fragment {
    private SupportMapFragment fragment;
    private GoogleMap map;
    private String datos;
    private Receptor_Coordenadas r;
    private TextView et;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_test_fragment, container, false);

        Receptor_Coordenadas r = new Receptor_Coordenadas();
        r.lanzar();

        CustomMapFragment mapFragment = new CustomMapFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map_container, mapFragment).commit();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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


        //  datos="Error con el receptor :"+e.toString();

        //}
      /*  Toast toast = Toast.makeText(getActivity(), datos.length(), Toast.LENGTH_SHORT);
        toast.show();*/

        if (map == null) {
            map = fragment.getMap();
            map.addMarker(new MarkerOptions().position(new LatLng(36.80, -2.58)));

            // Zoom in the Google Map
            //map.animateCamera(CameraUpdateFactory.zoomTo(25));
        }

        Toast toast = Toast.makeText(getActivity(), ReceptorActivity.C, Toast.LENGTH_LONG);
        toast.show();

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

        Button Recibir = (Button) getActivity().findViewById(R.id.button2);
        Recibir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent recibe = new Intent(getActivity(), ReceptorActivity.class);
                startActivity(recibe);
            }
        });

        Button Ver_Mapa = (Button) getActivity().findViewById(R.id.button3);
        Recibir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent recibe = new Intent(getActivity(), ReceptorActivity.class);
                startActivity(recibe);
            }
        });

    }


}

