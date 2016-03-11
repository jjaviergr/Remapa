package com.example.pc.myapplicationmapaprueba;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment1 extends android.support.v4.app.Fragment
{
   @Override
   public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) 
   {

      // ---establecer el diseño para este fragmento ---
      return inflater.inflate(R.layout.fragment1, container,false);
   }

   public interface OnDataPass {
      public void onDataPass(String data);
   }

   public void onStart(){
   super.onStart();
      String myValue = this.getArguments().getString("message");
   Toast toast = Toast.makeText(getActivity(), myValue, Toast.LENGTH_SHORT);
   toast.show();

      String otroValue = this.getArguments().getString("messagef2");

      TextView uno=(TextView)getActivity().findViewById(R.id.textView2);
      uno.setText(otroValue);

      Button mandar_mensaje=(Button)getActivity().findViewById(R.id.button6);

      mandar_mensaje.setOnClickListener(new View.OnClickListener() {

         public void onClick(View v) {

            Bundle bundle = new Bundle();
            bundle.putString("messagef1", "Mensage de fragment1");
            Fragment2 fragment2 = new Fragment2();
            fragment2.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(android.R.id.content, fragment2);
            fragmentTransaction.commit();

         }
      });
}
}
