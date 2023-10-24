package com.example.contactbookjms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.SortedSet;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        ListView lvContacts = findViewById(R.id.lvContacts);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);



        SortedSet<Contact> contacts = Database.listContacts;

        //Llamamos al metodo para tener contactos por defecto en el listview
        Database.fillOutListView();

        //Instanciamos el ContactAdapter
        ContactAdapter adapter = new ContactAdapter((Context) this, contacts);

        //Aqui asignamos el adaptador al listView para que muestre los contactos
        lvContacts.setAdapter(adapter);

        lvContacts.setOnItemClickListener((parent, view, position, id) -> {

            //Si clicamos un contacto obtenemos el contacto seleccionado en la posición "position" y
            // lo almacenamos en una variable
            Contact contact = (Contact) parent.getItemAtPosition(position);

            //Aqui creamos un intent para dirigirnos a la actividad EditContacts
            Intent intent = new Intent(this, EditContacts.class);

            //Con esto pasamos los datos del contacto que he clicado a la vista
            intent.putExtra("name", contact.getName());
            intent.putExtra("surnames", contact.getSurnames());
            intent.putExtra("number", contact.getNumber());
            intent.putExtra("email", contact.getEmail());
            intent.putExtra("id", contact.getId());


            //Aqui por ultimo nos dirigimos a la vista
            startActivity(intent);

        });



        //Aqui indicamos que al hacer click en el btnAdd nos dirigamos a la ventana para añadir los contactos
        btnAdd.setOnClickListener(v -> {

            Intent viewNameIntent = new Intent(this, ActivityAddContacts.class);
            startActivity(viewNameIntent);



        });

        //Aqui indicamos que al pulsar el boton btnDelete se abra la ventana DeleteContacts
        btnDelete.setOnClickListener(v -> {

            Intent viewNameIntent = new Intent(this, DeleteContacts.class);
            startActivity(viewNameIntent);



        });



    }
}