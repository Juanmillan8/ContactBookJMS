package com.example.contactbookjms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.contactbookjms.datasources.DataSource;
import com.example.contactbookjms.models.Contact;

import java.sql.SQLException;

//Clase para añdir contactos nuevos a la lista
public class ActivityAddContacts extends AppCompatActivity {

    private DataSource contactDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);


        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);


        //Aqui me dirijo al metodo saveContact() o al metodo cancel() dependiendo de cual boton pulse,
        // debo introducir en un try catch en el boton saveContact porque dicho metodo puede lanzar una
        // excepcion SQLException
        btnSave.setOnClickListener(v -> {
            try {
                saveContact();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        btnCancel.setOnClickListener(v -> cancel());

    }

    //Metodo saveContact que se usara para añadir contactos
    private void saveContact() throws SQLException {
        contactDataSource = new DataSource(this);

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

        //Si no he insertado nada en los campos de texto muestro un mensaje de error
        if(name.length() == 0 || surnames.length() == 0 || number.length() == 0 || mail.length() == 0) {
            tvGrettings.setText("Error, debes insertar datos en los campos de texto");
            tvGrettings.setTextColor(Color.RED);
        }else{

                //Booleano igual para hacer una cosa u otra en caso de tener un contacto repetido
                boolean igual = false;

                //Aqui recorremos la lista de contactos y si encontramos un contacto con el mismo numero que el
                //numero del contacto que queremos añadir mostramos un mensaje de error y cambiamos el booleano
                //repetido a true
                for (Contact contact : contactDataSource.getAllContacts()) {


                    if (contact.equals(number)) {
                        tvGrettings.setText("Error, no puedes añadir dos numeros iguales");
                        tvGrettings.setTextColor(Color.RED);
                        igual=true;
                        break;
                    }

                }

                //Si el booleano repetido esta a false añadimos el contacto a la lista y
                // volvemos a la actividad principal para ver los cambios
                if (!igual){

                    contactDataSource.insertContact(name, surnames, number, mail);

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