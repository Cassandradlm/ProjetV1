package com.example.projetv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    // below variable is for our database name.
    private static final String DB_NAME = "FILMS";
    // below int is our database version
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "mycourses";
    private static final String ID_COL = "id";
    private static final String NOM_COL = "Nom";
    private static final String DESCRIPTION_COL = "Description";
    private static final String CATEGORIE_COL = "Categorie";
    private static final String AFFICHE_COL = "Affiche";
    private static final String DUREE_COL = "Duree";
    private static final String ANNEE_COL = "Annee";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOM_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + CATEGORIE_COL + " TEXT,"
                + AFFICHE_COL + " TEXT,"
                + DUREE_COL + " TEXT,"
                + ANNEE_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewCourse(String filmNom,
                             String filmDescription,
                             String filmCategorie,
                             String filmAffiche,
                             String filmDuree,
                             String filmAnnee) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NOM_COL, filmNom);
        values.put(DESCRIPTION_COL, filmDescription);
        values.put(CATEGORIE_COL, filmCategorie);
        values.put(AFFICHE_COL, filmAffiche);
        values.put(DUREE_COL, filmDuree);
        values.put(ANNEE_COL, filmAnnee);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
