package com.example.contactbookjms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.contactbookjms.R;
import com.example.contactbookjms.models.Contact;

import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class ContactAdapter extends ArrayAdapter<Contact> {

    //Constructor del contactAdapter
    public ContactAdapter(Context context, SortedSet<Contact> contacts){
        super(context, 0, (List<Contact>) contacts.stream().collect(Collectors.toList()));


    }

    //Metodo getView que retorna una vista inflada de todos los elementos que vienen en cada posicion del
    //listView
    public View getView(int position, View convertView, ViewGroup parent){

        Contact contact = getItem(position);
        //Si la vista actual (convertView) es nula, crea una nueva
        //vista a partir del archivo XML llamado item_contact
        if (convertView==null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);

        }


        TextView nameTextView = convertView.findViewById(R.id.tvName);
        TextView surNamesTextView = convertView.findViewById(R.id.tvSurNames);

        //Inserto el nombre y apellidos del contacto en los campos de texto
        nameTextView.setText(contact.getName());
        surNamesTextView.setText(contact.getSurnames());

        //Devuelvo la vista del adapter
        return convertView;

    }


}
