package com.example.projetv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAll extends SQLiteOpenHelper {

    public DBAll(Context context) {
        super(context, "mycourses", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS mycourses;");
        db.execSQL("CREATE TABLE mycourses(id INTEGER PRIMARY KEY AUTOINCREMENT, Nom TEXT, Description TEXT, Categorie TEXT, Affiche TEXT, Duree TEXT, Annee TEXT, Affichenoglide TEXT);");
    }

    public void addNewCourse(String filmNom, String filmDescription, String filmCategorie,
                             String filmAffiche, String filmDuree, String filmAnnee, String filmAffichenoglide) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Nom", filmNom);
        values.put("Description", filmDescription);
        values.put("Categorie", filmCategorie);
        values.put("Affiche", filmAffiche);
        values.put("Duree", filmDuree);
        values.put("Annee", filmAnnee);
        values.put("Affichenoglide", filmAffichenoglide);

        db.insert("mycourses", null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS mycourses");
        onCreate(db);
    }

}
