package com.example.contactbookjms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Clase DeleteContacts para borrar contactos
public class DeleteContacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contacts);

        Button btnDeleteContact = (Button) findViewById(R.id.btnDeleteContact);

        //Aqui indico que al pulsar el boton btnDeleteContact me dirija al metodo delete()
        btnDeleteContact.setOnClickListener(v -> delete());



    }

    //Metodo para borrar contactos
    private void delete(){

        EditText ptNumber = (EditText) findViewById(R.id.ptNumberDelete);
        TextView tvGrettings = (TextView) findViewById(R.id.tvGrettings);
        //Booleano inicializado a false para saber si he eliminado el contacto
        boolean eliminado=false;
        //Aqui almaceno lo que he escrito en el EditText ptNumber, osea el numero
        //que quiero borrar
        String number = String.valueOf(ptNumber.getText());

        //Recorremos con un for la lista de contactos
        for (Contact contact : Database.listContacts) {
            //Si hemos encontrado un contacto con el mismo numero lo eliminamos
            //y volvemos a la actividad principal para ver los cambios
            if (contact.equals(number)) {
                Database.listContacts.remove(contact);
                //Si eliminamos el contacto cambiamos la variable eliminado a true
                eliminado=true;
                Intent viewNameIntent = new Intent(this, MainActivity.class);
                startActivity(viewNameIntent);

            }

        }
        if (!eliminado){

            //En cambio, si eliminado es false significa que no hemos eliminado el contacto
            //por lo que hay dos posibles razones una de ellas es que no hayamos insertado
            // nada en el ptNumber, si es asi mostramos el siguiente mensaje de error
            if (ptNumber.length()==0){
                tvGrettings.setText("Error, debes insertar un numero de telefono");
                tvGrettings.setTextColor(Color.RED);

            }else {
                //Si el motivo no es el anterior entonces sera que el contacto no existe
                tvGrettings.setText("Error, el contacto no existe");
                tvGrettings.setTextColor(Color.RED);
            }
        }





    }



}