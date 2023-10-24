package com.example.contactbookjms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.contactbookjms.adapters.ContactAdapter;
import com.example.contactbookjms.datasources.DataSource;
import com.example.contactbookjms.models.Contact;

import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {


    private DataSource contactDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        ListView lvContacts = findViewById(R.id.lvContacts);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);


        contactDataSource = new DataSource(this);

        //Al iniciar esta actividad nos traemos todos los contactos almacenados actualmente
        SortedSet<Contact> contacts = new TreeSet<>();
        try {
            contacts = contactDataSource.getAllContacts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        //Instanciamos el ContactAdapter
        ContactAdapter adapter = new ContactAdapter((Context) this, contacts);

        //Aqui asignamos el adaptador al listView para que muestre los contactos
        lvContacts.setAdapter(adapter);

        lvContacts.setOnItemClickListener((parent, view, position, id) -> {

            //Si clicamos un contacto obtenemos el contacto seleccionado en la posición "position" y
            // lo almacenamos en una variable
            Contact contact = (Contact) parent.getItemAtPosition(position);

            //Creamos un intent para ir a la actividad de editar contactos
            Intent intent = new Intent(this, EditContacts.class);

            //Con esto pasamos los datos del contacto que he clicado a la vista
            intent.putExtra("name", contact.getName());
            intent.putExtra("surnames", contact.getSurnames());
            intent.putExtra("number", contact.getNumber());
            intent.putExtra("email", contact.getEmail());
            intent.putExtra("id", contact.getId());


            //Aqui por ultimo nos dirigimos a la actividad
            startActivity(intent);




        });

        //Si pulso el boton btnAdd pasamos a la actividad de inserrtar contactos
        btnAdd.setOnClickListener(v -> {

            Intent viewNameIntent = new Intent(this, ActivityAddContacts.class);



            startActivity(viewNameIntent);



        });

        //Si pulsamos el boton btnDelete pasamos a la actividad de eliminar contactos
        btnDelete.setOnClickListener(v -> {

            Intent viewNameIntent = new Intent(this, DeleteContacts.class);



            startActivity(viewNameIntent);



        });

    }
}