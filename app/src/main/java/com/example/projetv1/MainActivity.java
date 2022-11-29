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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    static SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_navigation);

        Context mContext = this.getApplicationContext();

        mPrefs = mContext.getSharedPreferences("myAppPrefs", 0);
        if(MainActivity.getFirstRun()) {
            setRunned();
            getListItems();
            }
        else { }

        navigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new TirageFragment()).commit();
        navigationView.setSelectedItemId(R.id.Suggestions);


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment =null;
                ImageButton bouton_like = findViewById(R.id.Button_like);
                ImageButton bouton_dislike = findViewById(R.id.Button_dislike);
                ImageButton bouton_dejavu = findViewById(R.id.Button_dejavu);
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
                if(fragment == item.getItemId(R.id.Suggestions)){
                    bouton_like.setVisibility(View.INVISIBLE);
                    bouton_dislike.setVisibility(View.VISIBLE);
                    bouton_dejavu.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

    }

    private void getListItems() {
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
                                    String.valueOf(film_list.get(i).getAnnee()));
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

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    }
