package com.example.pc.myapplicationmapaprueba;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;

public class Fragmentos extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentos);

        /** Código que añadimos. */

//Para manejar fragmentos mediante transacciones 
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//---obtiene información sobre la vista o View actual---
        WindowManager wm = getWindowManager();

        Display d = wm.getDefaultDisplay();

        if (d.getWidth() > d.getHeight()) //se puede sustiuir a partir API 13 por (ver nota**)
        {
//---si modo horizontal, crea el fragmento1---
            Bundle bundle = new Bundle();
            bundle.putString("message", "mensaje de activity");
            Fragment1 fragment1 = new Fragment1();
            fragment1.setArguments(bundle);
// android.R.id.content hace referencia a la vista de contenido de la actividad 
            fragmentTransaction.replace(android.R.id.content, fragment1);
        } else {
//---si modo vertical: crea el fragmento2---
            Bundle bundle = new Bundle();
            bundle.putString("message", "mensaje 2 de activity");
            Fragment2 fragment2 = new Fragment2();
            fragment2.setArguments(bundle);
            fragmentTransaction.replace(android.R.id.content, fragment2);
        }
//---añadir a la pila de nuevo---
        fragmentTransaction.addToBackStack(null);

//Para asegurar que los cambios sufren efecto, ejecutamos commit 
        fragmentTransaction.commit();
    }
}
