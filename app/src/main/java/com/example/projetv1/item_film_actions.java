package com.example.projetv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class item_film_actions extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static SharedPreferences mPrefs;

    private String mParam1;
    private String mParam2;

    public item_film_actions() { }

    public static TirageFragment newInstance(String param1, String param2) {
        TirageFragment fragment = new TirageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.item_film, container, false);
    }

    public void onClick(View v) {
        Intent intent;
        DBLike dbLike = new DBLike(getContext());

        String nom = v.findViewById(R.id.T_titrerecherche).toString();
        String description = v.findViewById(R.id.T_descriptionrecherche).toString();
        String categorie = v.findViewById(R.id.t_categorierecherche).toString();
        String affiche = v.findViewById(R.id.I_filmrecherche).toString();
        String duree = v.findViewById(R.id.T_dureerecherche).toString();
        String annee = v.findViewById(R.id.T_anneerecherche).toString();
        switch (v.getId()) {
            case R.id.Button_like:
                dbLike.addNewLike(nom, description, categorie, affiche, duree, annee, affiche);
                Toast.makeText(getContext(), "AJOUTE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Button_dislike:
                // selected question 2
                break;
            case R.id.Button_dejavu:
                // selected question 3
                break;
        }
    }

}