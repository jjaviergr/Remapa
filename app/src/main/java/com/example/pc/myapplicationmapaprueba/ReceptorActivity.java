package com.example.pc.myapplicationmapaprueba;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class ReceptorActivity extends AppCompatActivity {

    static String Coordenadas;
    private final String NAMESPACE = "http://rastreriza.esy.es";
    // private final String NAMESPACE = "http://192.168.1.10/receta_soap_wdsl";
    // private final String URL = "http://192.168.1.10/receta_soap_wdsl/servicio_con_wdsl.php?wsdl";
    private final String URL = "http://rastreriza.esy.es/servicio_con_wdsl.php?wsdl";
    private final String SOAP_ACTION_PREFIX = "/";
    private final String METHOD_NAME_1 = "obtener_todas_las_coordenadas";

    private String TAG = "Depuración:";
    //   private static String nombre_receta, tipo_receta, preparacion_receta, presentacion_receta;


    //private Gestiona_DB BD;
    private Button bt;
    private TextView tx;
    private String cadena_s;

    private String auxiliar;
    private String[][] Vcoordenadas;
    //String latitud, longitud, fecha;
    private double lat, lon;
    //private String fec;

    //private Wifi wifi;

    // Para almacenar la latitud y longitud

    private String latitud, longitud, fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptor);
        //tv1 = (TextView) findViewById(R.id.textView2);
        bt = (Button) findViewById(R.id.button2);
        //BD = new Gestiona_DB(this);
        tx = (TextView) findViewById(R.id.editText8);


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

    public String[][] peticiones(SoapSerializationEnvelope envelope, String metodo, String propiedad) {
        Object objeto;
        String[][] datos = null;
        //Create request
        SoapObject request = new SoapObject(NAMESPACE, metodo);
        //bodyOut is the body object to be sent out with this envelope
        //Add the property to request object
        request.addProperty(propiedad, "");
//
       /* envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.addMapping(NAMESPACE, "documentIds", new VdobleSpecial().getClass());*/
//
        ///***SIN ESTO NO ACEPTA String[][] ******///

        //MarshallArray vector = new MarshallArray();
        //vector.register(envelope);

        ///**************************************///


        envelope.bodyOut = request;
        HttpTransportSE transport = new HttpTransportSE(URL);
        try {
            transport.call(NAMESPACE + SOAP_ACTION_PREFIX + metodo, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //bodyIn is the body object received with this envelope
        if (envelope.bodyIn != null) {
            //getProperty() Returns a specific property at a certain index.
            SoapObject Table = (SoapObject) envelope.bodyIn;

            String[][] output = null;
            if (Table != null) {
                SoapObject row = (SoapObject) Table.getProperty(0);

                if (row != null) {
                    int rCount = Table.getPropertyCount();
                    int cCount = ((SoapObject) Table.getProperty(0)).getPropertyCount();
                    output = new String[rCount][cCount];
                    for (int i = 0; i < rCount; i++) {
                        for (int j = 0; j < cCount; j++)
                            output[i][j] = ((SoapObject) Table.getProperty(i)).getProperty(j).toString();
                    }

                }
            }
            return output;

        }

        return null;
    }

    public void getCoordenadas() {
        try {
            // SoapEnvelop.VER11 is SOAP Version 1.1 constant
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);


            Vcoordenadas = peticiones(envelope, METHOD_NAME_1, "cod_receta");


        } catch (Exception e) {
            e.printStackTrace();


        }

    }

    public String imprime_coordenada(String[] c) {
        String cad = "";
        cad = c[0];
        cad += c[1];
        cad += c[2];
        cad += c[3] + "\n";
        return (cad);
    }

    public String imprime_coordenadas(String[][] c) {
        String cad = "";
        for (int i = 0; i < c.length; i++) {
            cad += imprime_coordenada(c[i]);
        }
        return (cad);
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");


            //BD.insertarCoordenadas(getCoordenadas());
            getCoordenadas();
            //auxiliar=Integer.toString(getCoordenadas().length);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            //tx.setText(imprime_coordenadas(BD.getAllCoordenadas()));
            //tx.setText(BD.getAllCoordenadas().size());
            // tx.setText(imprime_coordenadas(Vcoordenadas));
            try {
                String cad = "";
                for (int i = 0; i < Vcoordenadas.length; i++) {

                    for (int j = 0; j < Vcoordenadas[0].length; j++) {
                        cad += Vcoordenadas[i][j];
                    }
                }
                String cadena = "";
                String[] corte1 = cad.split("stringArray\\{");

                ArrayList<ArrayList<String[]>> corte2 = new ArrayList<ArrayList<String[]>>();
                for (int i = 0; i < corte1.length; i++) {
                    ArrayList<String[]> Elemento = new ArrayList<String[]>();
                    Elemento.add(corte1[i].split(";"));
                    corte2.add(Elemento);
                }

                for (int i = 0; i < corte2.size(); i++) {
                    for (int j = 0; j < corte2.get(i).size(); j++) {
                        ArrayList<String[]> Elemento = corte2.get(i);
                        for (int z = 0; z < Elemento.get(j).length; z++)
                            cadena += Elemento.get(j)[z].toString().replaceAll("item=", "");
                        cadena = cadena.replaceAll("\\}", "");
                        cadena += ' ';

                    }
                }
                Coordenadas = cadena;


                /*************
                 * try {


                 String cadena = ReceptorActivity.Coordenadas;
                 String[] coordenadas = cadena.split(" ");

                 String cad = "";
                 for (int i = 2; i < coordenadas.length; i = i + 6) {
                 cad += coordenadas[i] + " " + coordenadas[i + 1] + " ";

                 }
                 dibujarLineas(cad);
                 } catch (Exception e) {

                 //t.setText("Excepcion al filtrar coordenadas :" + e.getMessage().toString());
                 }************/




                String[] cadena_s=cadena.split(" ");
                String cadena_muestra="";
                for(int i=0;i<cadena_s.length;i=i+6)
                {
                    cadena_muestra+=cadena_s[2]+" "+cadena_s[3]+" "+cadena_s[4]+" "+cadena_s[5]+"\n\n";
                }
                tx.setText(cadena_muestra);




            } catch (Exception e) {
                tx.setText("Excepcion :" + e.getMessage().toString() + Coordenadas);
            }
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

    public void onclickBotonMapa(View v) {
        /*lanza_activity.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("messagef2", "Mensage de fragment2");
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReceptorActivity.class);

                getActivity().startActivity(intent);
            }
        });
*/

        Intent intent = new Intent();


        intent.setClass(this, TestFragmentActivity.class);
        startActivity(intent);
    }

}
