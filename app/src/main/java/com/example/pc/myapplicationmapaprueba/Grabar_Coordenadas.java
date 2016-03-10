package com.example.pc.myapplicationmapaprueba;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Grabar_Coordenadas extends AppCompatActivity implements LocationListener {


    private final String NAMESPACE = "http://rastreriza.esy.es";
    // private final String NAMESPACE = "http://192.168.1.10/receta_soap_wdsl";
    // private final String URL = "http://192.168.1.10/receta_soap_wdsl/servicio_con_wdsl.php?wsdl";
    private final String URL = "http://rastreriza.esy.es/servicio_con_wdsl.php?wsdl";
    private final String SOAP_ACTION_PREFIX = "/";
    private final String METHOD_NAME_1 = "insertar_coordenadas";

    private String TAG = "Depuración:";
    //   private static String nombre_receta, tipo_receta, preparacion_receta, presentacion_receta;


    private EditText tv1, tv5, tv6, tv7;
    private Button bt;


    private String[][] Vcoordenadas;
    //String latitud, longitud, fecha;
    private double lat, lon;
    //private String fec;


    LocationManager locationManager;
    // Para almacenar la latitud y longitud

    private String latitud,longitud,fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grabar_coordenadas);
        //tv1 = (TextView) findViewById(R.id.textView2);
        bt = (Button) findViewById(R.id.button);
        tv5 = (EditText) findViewById(R.id.editText5);
        tv6 = (EditText) findViewById(R.id.editText6);
        tv7 = (EditText) findViewById(R.id.editText7);
        //     obtenemos el servicio de posicionamiento
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (this.locationManager == null) {
            Toast.makeText(this, "Error al recuperar el GPS", Toast.LENGTH_LONG)
                    .show();
        }
        // registramos la recepción de datos del GPS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Problema de permisos para el GPS", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 20, (LocationListener) this);

        // wifi = new Wifi();

        // if (wifi.Detecta_Wifi()) {
        //while (true) // El tiempo en milisegundos{
        //{

        AsyncCallWS task = new AsyncCallWS();
        //Call execute
        task.execute();
        //}
        //  }

    }


    public void onclickbutton(View v) {
        finish();
    }

    public String peticiones(SoapSerializationEnvelope envelope, String metodo, String propiedad, String[][] coordenadas) {
        String cadena = "";
        //Create request
        SoapObject request = new SoapObject(NAMESPACE, metodo);
        //bodyOut is the body object to be sent out with this envelope
        //Add the property to request object
        request.addProperty(propiedad, coordenadas);

        ///***SIN ESTO NO ACEPTA String[][] ******///

        MarshallArray vector = new MarshallArray();
        vector.register(envelope);

        ///**************************************///

        envelope.bodyOut = request;
        HttpTransportSE transport = new HttpTransportSE(URL);
        try {
            transport.call(NAMESPACE + SOAP_ACTION_PREFIX + metodo, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //bodyIn is the body object received with this envelope
        /*if (envelope.bodyIn != null) {
            //getProperty() Returns a specific property at a certain index.
            cadena=((SoapObject) envelope.bodyIn).getProperty(0).toString();
            // nombre_receta= envelope.bodyIn.toString();
        }
        else cadena="Error";*/
        return cadena;
    }

    public void setCoordenadas(String[][] coordenadas) {
        try {
            // SoapEnvelop.VER11 is SOAP Version 1.1 constant
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);


            peticiones(envelope, METHOD_NAME_1, "cod_receta", coordenadas);


        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    private class AsyncCallWS extends AsyncTask<String[][], Void, Void> {
        @Override
        protected Void doInBackground(String[][]... params) {
            Log.i(TAG, "doInBackground");
            try {
                while (true)
                {
                    latitud = Double.toString(lat);
                    longitud = Double.toString(lon);
                    fecha = getFecha();


                    Vcoordenadas = new String[1][3];
                    Vcoordenadas[0][0] = latitud;
                    Vcoordenadas[0][1] = longitud;
                    Vcoordenadas[0][2] = fecha;



                    setCoordenadas(Vcoordenadas);
                    Thread.sleep(60000);
                }
            } catch (Exception e) {
                // Tratamiento de la excepción
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");

        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");




        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
            tv5.setText(latitud);
            tv6.setText(longitud);
            tv7.setText(fecha);

        }

    }

    public String getFecha() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String fec = dateFormat.format(date);
        return fec;
    }







    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

        // guardamos los valores de latitud y longitud
        this.lat = location.getLatitude();
        this.lon = location.getLongitude();
        tv5.setText(Double.toString(this.lat));
        tv6.setText(Double.toString(this.lon));
        tv7.setText(getFecha());

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }


}
