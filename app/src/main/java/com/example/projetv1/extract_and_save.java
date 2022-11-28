package com.example.projetv1;


import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class extract_and_save {
    private static ArrayList<Film> mArrayList = new ArrayList<>();

    private DBHandler dbHandler;
    dbHandler = new DBHandler(MainActivity.this);

    ArrayList<Film> getListItems() {
        //a faire q'une fois
        ArrayList<Film> film_list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("ProjetAndroid")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override

                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                            Film film = document.toObject(Film.class);
                            film_list.add(film);
                            Log.d("5G", film.getNom());
                        }
                    }
                });
        return film_list;}

}
