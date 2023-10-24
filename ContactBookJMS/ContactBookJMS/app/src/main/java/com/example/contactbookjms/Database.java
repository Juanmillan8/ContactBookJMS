package com.example.contactbookjms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

//Clase Database donde almacenaremos todos los contactos
public class Database {

    //SortedSet donde se almacenaran todos los contactos, es publico y
    //estatico para poderlo usar en todas las demas clases
    public static SortedSet<Contact> listContacts = new TreeSet<>();

    //Metodo para agregar contactos por defecto a la lista
    public static void fillOutListView(){

        //Con un for recorro de 0 a 20, voy almacenando en variables el nombre y el resto de
        //datos del contacto, posterirmente creo el contacto y lo añado a la lista
        for (int i=0;i<5;i++){
            String name = "Nombre " + i;
            String surnames = "Apellidos " + i;
            String number = "Numero " + i;
            String email = "Email@iescarrillosalcedo" + i + ".es";
            int id = i;
            Contact c = new Contact(name, surnames, number, email, id);
            listContacts.add(c);

        }


    }

    //Metodo getId para devolver el mayor id de la lista
    public static int getId(){
        int result = 0;
        for(Contact c:listContacts){

            if (result < c.getId()){
                result = c.getId();
            }
        }
        return result;
    }


}
