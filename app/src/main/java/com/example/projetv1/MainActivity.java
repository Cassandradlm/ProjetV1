package com.example.projetv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Integer> liste_film_like;
    private ArrayList<Integer> liste_film_dislike;
    private ArrayList<Integer> liste_film_dejavu;

    BottomNavigationView navigationView;
    static SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_navigation);

        Context mContext = this.getApplicationContext();

        mPrefs = mContext.getSharedPreferences("myAppPrefs", 0);

        if(MainActivity.getFirstRun()) {
            getListItems();
            setRunned();
            }
        else { }

        navigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new TirageFragment()).commit();
        navigationView.setSelectedItemId(R.id.Suggestions);


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment =null;
                switch (item.getItemId()){
                    case R.id.Suggestions:
                        fragment = new TirageFragment();
                        break;
                    case R.id.Films_aimes:
                        fragment = new LikeFragment();
                        break;
                    case R.id.Deja_vus:
                        fragment = new DejaVuFragment();
                        break;
                    case R.id.categorie:
                        fragment = new ListeFragment();
                        break;
                    case R.id.recherche:
                        fragment = new RechercheFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
                return true;
            }
        });

    }

    private void getListItems() {
        Log.d("5G", "blalba");
        ArrayList<Film> film_list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DBHandler dbHandler = new DBHandler(this);
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
                        Log.d("LISTE_FILM", String.valueOf(film_list.size()));
                        for (int i = 0; i < film_list.size(); i++) {
                            dbHandler.addNewCourse(
                                    String.valueOf(film_list.get(i).getNom()),
                                    String.valueOf(film_list.get(i).getDescription()),
                                    String.valueOf(film_list.get(i).getCategorie()),
                                    String.valueOf(film_list.get(i).getAffiche()),
                                    String.valueOf(film_list.get(i).getDuree()),
                                    String.valueOf(film_list.get(i).getAnnee()),
                                    String.valueOf(film_list.get(i).getAffiche()));
                            Log.d("OK FIRST","OKKK");
                        }
                    }
                });

    }

    public static boolean getFirstRun() {
        return mPrefs.getBoolean("firstRun", true);
    }

    public static void setRunned() {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putBoolean("firstRun", false);
        edit.commit();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myAppPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("like", null);
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        liste_film_like = gson.fromJson(json, type);
        if (liste_film_like == null) {
            liste_film_like = new ArrayList<>();
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(liste_film_like);
        editor.putString("like", json);
        editor.apply();
        //Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    public void click_on_like(){

    }

    }
