package com.example.pc.myapplicationmapaprueba;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by pc on 12/03/2016.
 */
public class BorraCoordenadas
{
    static String Coordenadas;
    private final String NAMESPACE = "http://rastreriza.esy.es";
    // private final String NAMESPACE = "http://192.168.1.10/receta_soap_wdsl";
    // private final String URL = "http://192.168.1.10/receta_soap_wdsl/servicio_con_wdsl.php?wsdl";
    private final String URL = "http://rastreriza.esy.es/servicio_con_wdsl.php?wsdl";
    private final String SOAP_ACTION_PREFIX = "/";
    private final String METHOD_NAME_1 = "borrar_todas";

    private String TAG = "Depuración:";
    //   private static String nombre_receta, tipo_receta, preparacion_receta, presentacion_receta;




    protected void Ejecuta() {


        AsyncCallWS task = new AsyncCallWS();

        task.execute();

    }

    public void peticiones(SoapSerializationEnvelope envelope, String metodo, String propiedad) {

        //Create request
        SoapObject request = new SoapObject(NAMESPACE, metodo);

        request.addProperty(propiedad, "");

        envelope.bodyOut = request;
        HttpTransportSE transport = new HttpTransportSE(URL);
        try {
            transport.call(NAMESPACE + SOAP_ACTION_PREFIX + metodo, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void LanzaBorrado() {
        try {
            // SoapEnvelop.VER11 is SOAP Version 1.1 constant
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);


            peticiones(envelope, METHOD_NAME_1, "cod_receta");


        } catch (Exception e) {
            e.printStackTrace();


        }

    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");



            Ejecuta();



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


        }

    }
}
