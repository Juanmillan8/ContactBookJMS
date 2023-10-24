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

//Clase para editar contactos
public class EditContacts extends AppCompatActivity {

    private DataSource contactDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        //Aqui recupero toda la informacion del contacto que quiero editar y
        //la muestro en los campos de texto
        Intent viewMainIntent = getIntent();
        EditText ptName = (EditText) findViewById(R.id.ptName);
        EditText ptSurnames = (EditText) findViewById(R.id.ptSurnames);
        EditText ptNumber = (EditText) findViewById(R.id.ptNumber);
        EditText ptMail = (EditText) findViewById(R.id.ptMail);

        ptName.setText(viewMainIntent.getStringExtra("name"));
        ptSurnames.setText(viewMainIntent.getStringExtra("surnames"));
        ptNumber.setText(viewMainIntent.getStringExtra("number"));
        ptMail.setText(viewMainIntent.getStringExtra("email"));

        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        //Aqui me dirijo al metodo edit() o al metodo cancel() dependiendo del boton al que pulse
        btnSave.setOnClickListener(v -> {
            try {
                edit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnCancel.setOnClickListener(v -> cancel());


    }

    //Metodo edit() para editar contactos
    private void edit() throws SQLException {
        contactDataSource = new DataSource(this);

        Intent viewMainIntent = getIntent();

        EditText ptName = (EditText) findViewById(R.id.ptName);
        EditText ptSurnames = (EditText) findViewById(R.id.ptSurnames);
        EditText ptNumber = (EditText) findViewById(R.id.ptNumber);
        EditText ptMail = (EditText) findViewById(R.id.ptMail);
        TextView tvGrettings = (TextView) findViewById(R.id.tvGrettings);

        //Aqui almaceno en variables la informacion actualizada del contacto
        String name = String.valueOf(ptName.getText());
        String surnames = String.valueOf(ptSurnames.getText());
        String number = String.valueOf(ptNumber.getText());
        String email = String.valueOf(ptMail.getText());

        //Aqui recupero el id del contacto, lo recupero en este metodo porque me va a hacer falta
        int id = viewMainIntent.getIntExtra("id",0);

        //Si intentamos actualizar un contacto dejando todos sus campos vacios saltara el siguiente
        //error
        if(name.length() == 0 || surnames.length() == 0 || number.length() == 0 || email.length() == 0) {
            tvGrettings.setText("Error, debes insertar datos en los campos de texto");
            tvGrettings.setTextColor(Color.RED);

        }else {

            //Si no, recorremos la lista de contactos
            for (Contact contact : contactDataSource.getAllContacts()) {
                //Si encontramos un contacto con el mismo id del que estamos editando hacemos lo siguiente
                if (contact.getId() == id) {
                    //Usamos el metodo numberEquals para impedir que se puedan editar contactos
                    //y guardarlos con un numero que ya existe, si quiero guardar un contacto que ya
                    //existe muestro un mensaje de error
                    if (numberEquals(number, id)){
                        tvGrettings.setText("Error, no puedes añadir dos numeros iguales");
                        tvGrettings.setTextColor(Color.RED);
                        break;
                    }else {
                        //Si el numero que queremos editar no lo tenemos ya almacenado editamos el contacto
                        //con todos los datos que he insertado en los campos de texto y posteriormente
                        //vuelvo a la MainActivity para ver los cambios

                        contactDataSource.updateContact(id, name,surnames, number, email);
                        Intent viewNameIntent = new Intent(this, MainActivity.class);
                        startActivity(viewNameIntent);
                    }
                }
            }
        }
    }

    //Metodo para evitar editar añadir contactos con el mismo numero
    private boolean numberEquals(String number, int id) throws SQLException {

        //Boolean igual inicializada a false
        boolean igual = false;

        //Recorremos la lista de contacos
        for (Contact contact : contactDataSource.getAllContacts()) {
            //Si encontramos un contacto con el mismo numero hacemos lo siguiente
            if (contact.equals(number)) {
                //Si tiene el mismo id que el contacto que estoy editando significa que es el contacto que
                //estoy editando, con lo que no hay problema
                if (contact.getId()==id){
                    break;
                }else {
                    //Si nos encontramos un contacto con el mismo numero pero con diferente id significa que
                    //hay un contacto diferente pero con el mismo numero, por lo tanto cambiamos el booleano
                    //igual a true
                    igual=true;
                    break;
                }


            }

        }
        //Devolvemos el booleano
        return igual;

    }

    //Metodo cancel para volver a la MainActivity
    private void cancel(){


        Intent viewNameIntent = new Intent(this, MainActivity.class);
        startActivity(viewNameIntent);


    }


}