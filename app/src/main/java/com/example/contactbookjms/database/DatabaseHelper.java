package com.example.contactbookjms.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    //Constante que indica el nombre de la base de datos
    private static final String DATABASE_NAME = "Diary APP";

    //Constante que sirve para indicar la version de la base de datos
    private static final int DATABASE_VERSION = 1;

    //Sentencia para crear la tabla Diary
    private static final String CREATE_DIARY_TABLE = "CREATE TABLE Diary (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surnames TEXT, number TEXT, email TEXT)";

    //Sentencia para eliminar la tabla Diary
    private static final String DROP_DIARY_TABLE = "DROP TABLE IF EXISTS Diary";

    //Constructor
    public DatabaseHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //Este metodo sirve para ejecutar la sentencia de creacion de la tabla Diary
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_DIARY_TABLE);

    }

    //Metodo para actualizar la tabla
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        //Se borra la base de datos con la sentencia vista anteriormente
        db.execSQL(DROP_DIARY_TABLE);
        //Se vuelve a crear con el metodo anterior
        onCreate(db);


    }


}
