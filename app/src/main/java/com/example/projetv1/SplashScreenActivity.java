package com.example.projetv1;

import androidx.appcompat.app.AppCompatActivity;

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
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {

    SQLiteDatabase db_sql;
    DBAll db_all;
    static SharedPreferences mPrefs;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context mContext = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mPrefs = mContext.getSharedPreferences("myAppPrefs", 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getFirstRun()) {
                    getListItems();
                    ProgressDialog progressDialog = new ProgressDialog(mContext);
                    progressDialog.setTitle("Chargement des données");
                    progressDialog.setMessage("Veuillez patienter...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.create();

                    startActivity(new Intent(SplashScreenActivity.this, Choix_categorie.class));
                }
                else {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
                finish();
            }
        },2000);

    }

    public void getListItems() {
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


    public static boolean getFirstRun() {
        return mPrefs.getBoolean("firstRun", true);
    }

    public static void setRunned() {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putBoolean("firstRun", false);
        edit.commit();
    }
}