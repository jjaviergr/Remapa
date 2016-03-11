package com.example.pc.myapplicationmapaprueba;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment2 extends android.support.v4.app.Fragment {
    private String myValue;
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myValue = this.getArguments().getString("message");
        rootView = inflater.inflate(R.layout.activity_test_fragment, container, false);


        return inflater.inflate(R.layout.fragment2, container, false);
    }

    public void onStart() {
        super.onStart();
        //--- Vista del botón ---
        Button btnGetText = (Button) getActivity().findViewById(R.id.btnGetText);
        Button mandar_mensaje=(Button)getActivity().findViewById(R.id.button7);
        TextView uno=(TextView)getActivity().findViewById(R.id.textView);

        Button lanza_activity=(Button)getActivity().findViewById(R.id.button8);

        Button lanza_mapa=(Button)getActivity().findViewById(R.id.button9);

        Button fragment_mapa=(Button)getActivity().findViewById(R.id.button10);

        fragment_mapa.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("messagef2", "Mensage de fragment2");
                Intent intent = new Intent();
                CustomMapFragment m= new CustomMapFragment();
                m.setArguments(bundle);

                CustomMapFragment mapFragment = new CustomMapFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.add(R.id.map_container, mapFragment).commit();
               // return rootView;

            }
        });

        lanza_mapa.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("messagef2", "Mensage de fragment2");
                Intent intent = new Intent();
                intent.setClass(getActivity(), TestFragmentActivity.class);

                getActivity().startActivity(intent);
            }
        });


        lanza_activity.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("messagef2", "Mensage de fragment2");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReceptorActivity.class);

                getActivity().startActivity(intent);
            }
        });

        mandar_mensaje.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("messagef2", "Mensage de fragment2");
                Fragment1 fragment1 = new Fragment1();
                fragment1.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(android.R.id.content, fragment1);
                fragmentTransaction.commit();

            }
        });

        btnGetText.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                TextView lbl = (TextView) getActivity().findViewById(R.id.lblfragment2);

                Toast toast = Toast.makeText(getActivity(), lbl.getText(), Toast.LENGTH_SHORT);
                toast.show();

            }
        });


        Toast toast = Toast.makeText(getActivity(), myValue, Toast.LENGTH_SHORT);
        toast.show();

        String otroValue = this.getArguments().getString("messagef1");


        uno.setText(otroValue);


    }


}
