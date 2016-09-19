package com.example.mariocifuentes.proyectoiteracion1;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class History extends AppCompatActivity {
    private final static int ESCOGER_CONTACTO = 1;
    private Button btnContactos;
    private String nombreContacto;
    private String numeroTelefonico;
    private FloatingActionButton btnEnviarAContacto;
    private String SMS;
    private HeartPattern pattern;

    private static HeartRateDB mydb;
    private static ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new HeartRateDB(this);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pattern = new HeartPattern(mydb.getAllRates());

        btnContactos  = (Button) findViewById(R.id.button3);
        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), ESCOGER_CONTACTO);
            }
        });

        String strReporte = pattern.respuesta;
        SMS = strReporte;

        btnEnviarAContacto = (FloatingActionButton) findViewById(R.id.fab);
        btnEnviarAContacto.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                enviarSMS();
                Snackbar.make(view, "Tu mensaje fue enviado exitosamente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        list = (ListView) findViewById(R.id.listView);

        ArrayAdapter adap = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, mydb.getAllRates());
        list.setAdapter(adap);
    }

    /**
     * Envía un mensaje de texto al contacto seleccionado
     */
    public void enviarSMS() {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numeroTelefonico,null,SMS,null,null);
    }

    public void mostrarSeleccionContactos(View v) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI),ESCOGER_CONTACTO);
        //startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.LAST_TIME_CONTACTED),ESCOGER_CONTACTO);

    }
    /**
     * Espera el resultado de la selección de contacto
     */
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        nombreContacto = "";
        if(resultCode == RESULT_OK){
            if(reqCode == ESCOGER_CONTACTO){
                Uri uriContacto = data.getData();
                if(uriContacto != null ){
                    try {
                        String[] cols = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                        Cursor cursor =  managedQuery(uriContacto, cols, null, null, null);
                        cursor.moveToFirst();
                        nombreContacto = cursor.getString(0);

                        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        String[] columnas = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                        String seleccion = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "='" + nombreContacto + "'";
                        Cursor c = managedQuery(phoneUri,columnas,seleccion,null, null );
                        if(c.moveToFirst()){
                            numeroTelefonico = c.getString(0);
                        }

                    } catch (Exception e) {
                        numeroTelefonico = e.getMessage();
                     }
                    btnContactos.setText(nombreContacto + "(Cambiar)");
                }
            }
        }

    }



}
