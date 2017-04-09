package com.example.fuerm.couchbase01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String nombreBD = "app";
        //Objeto opciones de la base de datos
        DatabaseOptions options = new DatabaseOptions();
        //Ponemos la opción crear a verdadero
        options.setCreate(true);//Esta opción es la que dice si abriendo una base  de datos que no existe la crea

        // Creamos un manager el objeto de mayor nivel en la jerarquía de couchbase, maneja las bases de datos

        Manager manager = null;

        try{
            manager = new Manager(new AndroidContext(getApplicationContext()),Manager.DEFAULT_OPTIONS);
            System.out.println("El directorio de almacenamiento es: " + manager.getDirectory().getAbsolutePath());
        }catch(IOException e){
            e.printStackTrace();
        }

        //Creamos o abrimos la base de datos
        Database database = null;

        try{
            //Con openDatabase abro si existe o la creo sino existe
            database = manager.openDatabase(nombreBD,options);
        } catch(CouchbaseLiteException e){
            e.printStackTrace();
        } catch (AbstractMethodError a){
            a.getCause();
            a.getMessage();
        }

        //Las  Propiedades que serán, salvadas en el documento
        Map<String, Object> propiedades = new HashMap<String, Object>();

        propiedades.put("titulo","Couchbase Mobile");
        propiedades.put("sdk", "Java");

        //Creamos un nuevo documento
        Document documento = database.createDocument();

        //Grabamos el documento a la base de datos
        try{
            documento.putProperties(propiedades);
        }catch (CouchbaseLiteException e){
            e.printStackTrace();
        }

        //Logeo la ID del documento creado en la base de datos y las propiedades
        Log.d("app", String.format("Documento ID :: %s", documento.getId()));
        Log.d("app", String.format("Aprendiendo %s con %s",
                (String) documento.getProperty("titulo"), (String) documento.getProperty("sdk")));

    }
}
