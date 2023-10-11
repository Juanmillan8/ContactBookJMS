package com.example.contactbookjms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Clase para añdir contactos nuevos a la lista
public class ActivityAddContacts extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);


        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);


        //Aqui me dirijo al metodo saveContact() o al metodo cancel() dependiendo de cual boton pulse
        btnSave.setOnClickListener(v -> saveContact());
        btnCancel.setOnClickListener(v -> cancel());

    }

    //Metodo saveContact que se usara para añadir contactos
    private void saveContact() {


        EditText ptName = (EditText) findViewById(R.id.ptName);
        EditText ptSurnames = (EditText) findViewById(R.id.ptSurnames);
        EditText ptNumber = (EditText) findViewById(R.id.ptNumber);
        EditText ptMail = (EditText) findViewById(R.id.ptMail);
        TextView tvGrettings = (TextView) findViewById(R.id.tvGrettings);

        //Almacenamos en variables la informacion que he insertado en los campos de texto
        String name = String.valueOf(ptName.getText());
        String surnames = String.valueOf(ptSurnames.getText());
        String number = String.valueOf(ptNumber.getText());
        String mail = String.valueOf(ptMail.getText());
        //Llamamos al metodo getId de la clase Database y le sumamos 1, para que no nos devuelva la id
        //del ultimo contacto añadido existente, ya que nos daria error
        int id = Database.getId() + 1;

        //Si no he insertado nada en los campos de texto muestro un mensaje de error
        if (name.length() == 0 || surnames.length() == 0 || number.length() == 0 || mail.length() == 0) {
            tvGrettings.setText("Error, debes insertar datos en los campos de texto");
            tvGrettings.setTextColor(Color.RED);

        } else {

            //En caso contrario creamos un contacto nuevo con los datos que he insertado en los campos
            //de texto
            Contact c = new Contact(name, surnames, number, mail, id);

            //Booleano repetido para hacer una cosa u otra en caso de tener un contacto repetido
            boolean repetido=false;

            //Aqui recorremos la lista de contactos y si encontramos un contacto con el mismo numero que el
            //numero del contacto que queremos añadir mostramos un mensaje de error y cambiamos el booleano
            //repetido a true
                for (Contact contact : Database.listContacts) {
                    if (contact.equals(number)) {
                        tvGrettings.setText("Error, no puedes añadir dos numeros iguales");
                        tvGrettings.setTextColor(Color.RED);
                        repetido=true;
                        break;
                        }


                    }
            //Si el booleano repetido esta a false añadimos el contacto a la lista y
            // volvemos a la actividad principal para ver los cambios
            if (!repetido){

                Database.listContacts.add(c);
                Intent viewNameIntent = new Intent(this, MainActivity.class);
                startActivity(viewNameIntent);
            }

                }




            }

    //Metodo cancel para volver a la MainActivity
    private void cancel(){


        Intent viewNameIntent = new Intent(this, MainActivity.class);
        startActivity(viewNameIntent);


    }


}