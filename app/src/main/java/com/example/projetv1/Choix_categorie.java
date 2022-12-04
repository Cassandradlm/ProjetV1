package com.example.projetv1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class Choix_categorie extends AppCompatActivity {

    private ArrayList<String> list_categorie;
    SQLiteDatabase sqLiteDatabase, sqLiteDatabase2;
    DBAll dbAll;
    DBSuggestion dbSuggestion;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        list_categorie = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_choix_categorie);

        CardView card_All = findViewById(R.id.All);
        CardView card_Action = findViewById(R.id.Action);
        CardView card_Animé = findViewById(R.id.Animé);
        CardView card_Comédie = findViewById(R.id.Comédie);
        CardView card_Documentaire = findViewById(R.id.Documentaire);
        CardView card_Drame = findViewById(R.id.Drame);
        CardView card_Fantastique = findViewById(R.id.Fantastique);
        CardView card_Français = findViewById(R.id.Français);
        CardView card_Horreur = findViewById(R.id.Horreur);
        CardView card_Jeunesse = findViewById(R.id.Jeunesse);
        CardView card_Noël = findViewById(R.id.Noël);
        CardView card_Policier = findViewById(R.id.Policier);
        CardView card_Romance = findViewById(R.id.Romance);
        CardView card_Sciencefiction = findViewById(R.id.Sciencefiction);
        CardView card_Thriller = findViewById(R.id.Thriller);

        int white = getResources().getColor(R.color.white);
        int rose = getResources().getColor(R.color.rose);

        card_All.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(list_categorie.contains("All")){
                    list_categorie.clear();
                    card_All.setCardBackgroundColor(white);
                    card_Action.setCardBackgroundColor(white);
                    card_Animé.setCardBackgroundColor(white);
                    card_Comédie.setCardBackgroundColor(white);
                    card_Documentaire.setCardBackgroundColor(white);
                    card_Drame.setCardBackgroundColor(white);
                    card_Fantastique.setCardBackgroundColor(white);
                    card_Français.setCardBackgroundColor(white);
                    card_Horreur.setCardBackgroundColor(white);
                    card_Jeunesse.setCardBackgroundColor(white);
                    card_Noël.setCardBackgroundColor(white);
                    card_Policier.setCardBackgroundColor(white);
                    card_Romance.setCardBackgroundColor(white);
                    card_Sciencefiction.setCardBackgroundColor(white);
                    card_Thriller.setCardBackgroundColor(white);
                }
                else{
                    list_categorie.clear();
                    list_categorie.add("All");
                    card_All.setCardBackgroundColor(rose);
                    card_Action.setCardBackgroundColor(white);
                    card_Animé.setCardBackgroundColor(white);
                    card_Comédie.setCardBackgroundColor(white);
                    card_Documentaire.setCardBackgroundColor(white);
                    card_Drame.setCardBackgroundColor(white);
                    card_Fantastique.setCardBackgroundColor(white);
                    card_Français.setCardBackgroundColor(white);
                    card_Horreur.setCardBackgroundColor(white);
                    card_Jeunesse.setCardBackgroundColor(white);
                    card_Noël.setCardBackgroundColor(white);
                    card_Policier.setCardBackgroundColor(white);
                    card_Romance.setCardBackgroundColor(white);
                    card_Sciencefiction.setCardBackgroundColor(white);
                    card_Thriller.setCardBackgroundColor(white);
                }
            }
        });
        card_Action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Action, "Action");
            }
        });
        card_Animé.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Animé, "Anime");
            }
        });
        card_Comédie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Comédie, "Comedie");
            }
        });
        card_Documentaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Documentaire, "Documentaire");
            }
        });
        card_Drame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Drame, "Drame");
            }
        });
        card_Fantastique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Fantastique, "Fantastique");
            }
        });
        card_Français.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Français, "Français");
            }
        });
        card_Horreur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Horreur, "Horreur");
            }
        });
        card_Jeunesse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Jeunesse, "Jeunesse");
            }
        });
        card_Noël.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Noël, "Noël");
            }
        });
        card_Policier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Policier, "Policier");
            }
        });
        card_Romance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Romance, "Romance");
            }
        });
        card_Sciencefiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Sciencefiction, "Science-fiction");
            }
        });
        card_Thriller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_and_list(card_Thriller, "Thriller");
            }
        });
        findViewById(R.id.button_cparti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog loadingDialog = new loadingDialog(Choix_categorie.this);
                loadingDialog.startLoadingDialog();
                if (list_categorie.contains("All")) {
                    recupdata_all();
                }
                else{
                    Log.d("TAILLE_LIST", String.valueOf(list_categorie.get(0)));
                    for(int i=0; i < list_categorie.size(); i++){
                        recupdata_categorie(list_categorie.get(i));
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SplashScreenActivity.setRunned();
                        loadingDialog.dismisDialog();
                        startActivity(new Intent(Choix_categorie.this, MainActivity.class));
                        finish();
                    }
                },5000);
            }
        });
    }

    public void recupdata_all() {
        dbAll=new DBAll(this);
        dbSuggestion=new DBSuggestion(this);

        sqLiteDatabase = dbAll.getReadableDatabase();
        sqLiteDatabase2 = dbSuggestion.getWritableDatabase();
        dbSuggestion.onCreate(sqLiteDatabase2);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from mycourses", null);

        if(cursor.getCount()==0){
            Toast.makeText(this, "No entry", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            sqLiteDatabase = dbSuggestion.getWritableDatabase();
            while(cursor.moveToNext()){
                dbSuggestion.addNewSuggestion(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7));
            }
        }
    }

    public void recupdata_categorie(String categorie) {
        dbAll=new DBAll(this);
        dbSuggestion=new DBSuggestion(this);

        sqLiteDatabase = dbAll.getReadableDatabase();
        sqLiteDatabase2 = dbSuggestion.getWritableDatabase();
        dbSuggestion.onCreate(sqLiteDatabase2);
        categorie.replace("'","''");
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from mycourses WHERE Categorie LIKE '" + categorie + "';", null);

        if(cursor.getCount()==0){
            Toast.makeText(this, "No entry", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext()){
                dbSuggestion.addNewSuggestion(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7));
            }
        }
    }

    private void color_and_list(CardView card, String categorie){
        int white = getResources().getColor(R.color.white);
        int rose = getResources().getColor(R.color.rose);
        if(list_categorie.contains(categorie)){
            list_categorie.remove(categorie);
            card.setCardBackgroundColor(white);
        }
        else if(!list_categorie.contains("All")){
            list_categorie.add(categorie);
            card.setCardBackgroundColor(rose);
        }
    }
}
