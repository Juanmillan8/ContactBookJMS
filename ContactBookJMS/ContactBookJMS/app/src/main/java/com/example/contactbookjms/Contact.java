package com.example.contactbookjms;

import android.util.Log;

//Clase contacto
public class Contact implements Comparable<Contact>{

    //Atributos del objeto contacto
    private String name;
    private String surnames;
    private String number;
    private String email;
    private int id;


    //Constructor de contacto
    public Contact(String name, String surnames, String number, String email, int id){

        this.name=name;
        this.surnames=surnames;
        this.number=number;
        this.email=email;
        this.id=id;

    }

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurnames() {
        return surnames;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    //Metodo compareTo que compara por nombre, apellidos y numero
    @Override
    public int compareTo(Contact c) {
        int result =0;

        Log.i("compareTo", c.getSurnames());

        if (this.getName().compareTo(c.getName()) == 0 ){

            if (this.getSurnames().compareTo(c.getSurnames())==0){
                result = this.getNumber().compareTo(c.getNumber());
            }else {
                result = this.getSurnames().compareTo(c.getSurnames());
            }

        }else {

            result = this.getName().compareTo(c.getName());

        }


        return result;
    }

    //Metodo equals para comparar si dos numeros son iguales
    public boolean equals(String number){

        return this.getNumber().equals(number);


    }

}
