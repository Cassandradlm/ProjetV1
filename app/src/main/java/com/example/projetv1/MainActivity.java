package com.example.projetv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_navigation);

        SharedPreferences pref = MainActivity.this.getSharedPreferences("FIRST",0);
        SharedPreferences.Editor editor= pref.edit();
        boolean firstRun = pref.getBoolean("firstRun", true);
        if(firstRun)
        {
            extract_and_save ex = new extract_and_save();
            ArrayList<Film> liste = ex.getListItems();
            DBHandler dbHandler = new DBHandler(this);
            for (int i = 0; i < liste.size(); i++) {
                dbHandler.addNewCourse(
                        liste.get(i).getNom(),
                        liste.get(i).getDescription(),
                        liste.get(i).getCategorie(),
                        liste.get(i).getAffiche(),
                        String.valueOf(liste.get(i).getDuree()),
                        String.valueOf(liste.get(i).getAnnee()));
                Log.d("OK FIRST","OKKK");
            editor.putBoolean("firstRun",false);
            editor.commit();
            }
        }

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
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    }
