package com.example.projetv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
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
            getListItems();
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Chargement des données");
            progressDialog.setMessage("Veuillez patienter...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.create();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRunned();
                    progressDialog.dismiss();
                }
            },5000);
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
                    case R.id.reglage:
                        fragment = new ReglagesFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
                return true;
            }
        });

    }

    private void getListItems() {
        ArrayList<Class_Film> film_list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DBAll dbHandler = new DBAll(this);
        db.collection("ProjetAndroid")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                            Class_Film film = document.toObject(Class_Film.class);
                            film_list.add(film);
                        }
                        for (int i = 0; i < film_list.size(); i++) {
                            dbHandler.addNewCourse(
                                    String.valueOf(film_list.get(i).getNom()),
                                    String.valueOf(film_list.get(i).getDescription()),
                                    String.valueOf(film_list.get(i).getCategorie()),
                                    String.valueOf(film_list.get(i).getAffiche()),
                                    String.valueOf(film_list.get(i).getDuree()),
                                    String.valueOf(film_list.get(i).getAnnee()),
                                    String.valueOf(film_list.get(i).getAffiche()));
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
