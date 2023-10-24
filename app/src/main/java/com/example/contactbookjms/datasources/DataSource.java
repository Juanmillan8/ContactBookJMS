package com.example.contactbookjms.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.contactbookjms.database.DatabaseHelper;
import com.example.contactbookjms.models.Contact;

import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

public class DataSource {


    //Variables que usaremos posteriormente
    private SQLiteDatabase database;

    private DatabaseHelper dbHelper;

    //Constructor
    public DataSource(Context context){

        dbHelper = new DatabaseHelper(context);


    }

    //Metodo para conectarnos a la base de datos en modo de escritura
    public void openWritableDatabase() throws SQLException{

        database = dbHelper.getWritableDatabase();

    }

    //Metodo para conectarnos a la base de datos en modo de lectura
    public void openReadableDatabase() throws SQLException{

        database = dbHelper.getReadableDatabase();

    }

    //Metodo para cerrar la conexion
    public void close(){

        dbHelper.close();
    }

    //Metodo para insertar contactos a la base de datos
    public void insertContact(String name, String surnames, String number, String email) throws SQLException {

        //Abrimos una conexion con la base de datos en modo de escritura
        openWritableDatabase();

        //Objeto ContentValues que sirve para almacenar pares clave-valor que posteriormente
        //se almacenaran en la base de datos
        ContentValues values = new ContentValues();
        //Aqui insertamos las claves con sus valores
        values.put("name", name);
        values.put("surnames", surnames);
        values.put("number", number);
        values.put("email", email);

        //Aqui insertamos en la base de datos los datos almacenados en el
        // objeto values indicando que el valor por defecto para las columnas
        //nulas sea null
        database.insert("Diary", null, values);

        //Cerramos la conexion
        close();
    }

    //Metodo para editar contactos
    public void updateContact(int id, String name, String surnames, String number, String email) throws SQLException {

        //Abrimos una conexion con la base de datos en modo de escritura
        openWritableDatabase();

        //Objeto ContentValues para almacenar la informacion actualizada del contacto
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("surnames", surnames);
        values.put("number", number);
        values.put("email", email);

        //Array de Strings que contendra la id del contacto que quiero editar
        String[] args = new String[]{String.valueOf(id)};

        //Aqui indicamos que para la tabla Diary le quiero actualizar los datos
        //a la informacion que le paso en el objeto ContentValues al contacto
        //cuya id sea la misma que tengo almacenada en el array
        database.update("Diary", values, "id=?", args);
        //Cierro la conexion con la base de datos
        close();

    }

    //Metodo para eliminar contactos de la base de datos
    public void deleteContact(String number) throws SQLException {

        //Abro una conexion con la base de datos en modo de escritura
        openWritableDatabase();

        //Array de Strings que contendra el numero del contacto que quiero eliminar
        String[] args= new String[]{number};

        //Aqui indicamos que para la tabla Diary quiero eliminar el contacto cuyo
        //numero sea el mismo que le paso en el array
        database.delete("Diary", "number=?", args);

        //Cerramos la conexion con la base de datos
        close();

    }

    //Metodo para devolver todos los contactos de la base de datos
    public SortedSet<Contact> getAllContacts() throws SQLException {
        SortedSet<Contact> listContacts = new TreeSet<>();

        openReadableDatabase();

        String[] columns = {"id", "name", "surnames", "number", "email"};
        Cursor contactCursor = database.query("diary", columns, null, null, null, null, null);

        if (contactCursor !=null && contactCursor.moveToFirst()){
            do{
                int idIndex = contactCursor.getColumnIndex("id");
                int nameIndex = contactCursor.getColumnIndex("name");
                int surnamesIndex = contactCursor.getColumnIndex("surnames");
                int numberIndex = contactCursor.getColumnIndex("number");
                int emailIndex = contactCursor.getColumnIndex("email");

                int id = contactCursor.getInt(idIndex);
                String name = contactCursor.getString(nameIndex);
                String surnames = contactCursor.getString(surnamesIndex);
                String number = contactCursor.getString(numberIndex);
                String email = contactCursor.getString(emailIndex);

                Contact contact = new Contact(name, surnames, number, email, id);
                listContacts.add(contact);

            }while (contactCursor.moveToNext());



        }
        close();

        return listContacts;

    }


}
