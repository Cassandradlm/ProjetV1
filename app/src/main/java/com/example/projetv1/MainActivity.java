package com.example.projetv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    BottomNavigationView navigationView;
    static SharedPreferences mPrefs;

    SQLiteDatabase db_sql;
    DBAll db_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context mContext = this.getApplicationContext();

        mPrefs = mContext.getSharedPreferences("myAppPrefs", 0);

        setContentView(R.layout.page_navigation);

        mPrefs.getString("page", "Top");

        navigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new TirageFragment()).commit();
        navigationView.setSelectedItemId(R.id.Suggestions);

        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString("page", "Suggestions");
        edit.commit();

        findViewById(R.id.reglagebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afficheReglages();
            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment =null;
                TextView title = findViewById(R.id.nomPage);

                switch (item.getItemId()){
                    case R.id.Suggestions:
                        title.setText("Suggestions");
                        edit.putString("page", "Suggestions");
                        edit.commit();
                        fragment = new TirageFragment();
                        break;
                    case R.id.Films_aimes:
                        title.setText("Films aimés");
                        edit.putString("page", "Films aimés");
                        edit.commit();
                        fragment = new LikeFragment();
                        break;
                    case R.id.Deja_vus:
                        title.setText("Films déjà vus");
                        edit.putString("page", "Films déjà vus");
                        edit.commit();
                        fragment = new DejaVuFragment();
                        break;
                    case R.id.dislike:
                        title.setText("Films pas aimés");
                        edit.putString("page", "Films pas aimés");
                        edit.commit();
                        fragment = new DislikeFragment();
                        break;
                    case R.id.recherche:
                        title.setText("Recherche");
                        edit.putString("page", "Recherche");
                        edit.commit();
                        fragment = new RechercheFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
                return true;
            }
        });

    }

    private void afficheReglages(){
        ImageButton bouton_fermer_r;
        Button maj_bdd;
        Button modif_categorie;
        Button ajouter_film;

        dialogBuilder = new AlertDialog.Builder(this);
        final View ReglagesView = LayoutInflater.from(this).inflate(R.layout.activity_reglages_fragment,null);
        dialogBuilder.setView(ReglagesView);
        dialog = dialogBuilder.create();
        dialog.setCancelable(false);
        dialog.show();

        bouton_fermer_r = ReglagesView.findViewById(R.id.fermer_reglages);
        maj_bdd = ReglagesView.findViewById(R.id.button_maj_bdd);
        modif_categorie = ReglagesView.findViewById(R.id.modif_categories);
        ajouter_film = ReglagesView.findViewById(R.id.button_ajoutfilm_bdd);

        bouton_fermer_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dialog.dismiss(); }
        });
        maj_bdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListItems();
            }
        });
        modif_categorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Choix_categorie.class));
                finish();
            }
        });
        ajouter_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    private void getListItems() {
        ArrayList<Class_Film> film_list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db_all = new DBAll(this);
        db_sql = db_all.getWritableDatabase();
        db_all.onCreate(db_sql);
        db.collection("ProjetAndroid")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("OK", "It's all good");
                        for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                            Class_Film film = document.toObject(Class_Film.class);
                            film_list.add(film);
                        }
                        for (int i = 0; i < film_list.size(); i++) {
                            db_all.addNewCourse(
                                    String.valueOf(film_list.get(i).getNom()),
                                    String.valueOf(film_list.get(i).getDescription()),
                                    String.valueOf(film_list.get(i).getCategorie()),
                                    String.valueOf(film_list.get(i).getAffiche()),
                                    String.valueOf(film_list.get(i).getDuree()),
                                    String.valueOf(film_list.get(i).getAnnee()),
                                    String.valueOf(film_list.get(i).getAffiche()));
                            Log.d("ADD", "Adding movie : "+film_list.get(i).getNom());
                        }
                    }
                });
    }


    public static String getPage(){
        return mPrefs.getString("page","");
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    }
