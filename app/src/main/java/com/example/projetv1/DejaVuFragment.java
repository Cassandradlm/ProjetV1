package com.example.projetv1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DejaVuFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<String> titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list;
    SQLiteDatabase sqLiteDatabase;
    DBHandler db;

    public DejaVuFragment() { }

    public static RechercheFragment newInstance(String param1, String param2) {
        RechercheFragment fragment = new RechercheFragment();
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

        return inflater.inflate(R.layout.fragment_deja_vu, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        displaydata();
        recyclerView = view.findViewById(R.id.listfilm_dejavu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        Adapter adapter = new Adapter(getContext(), titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void displaydata() {
        db=new DBHandler(getContext());

        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from mycourses", null);

        if(cursor.getCount()==0){
            Toast.makeText(getContext(), "No entry", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            titre_list = new ArrayList<String>();
            annee_list = new ArrayList<String>();
            categorie_list = new ArrayList<String>();
            description_list = new ArrayList<String>();
            duree_list = new ArrayList<String>();
            affiche_list = new ArrayList<String>();

            while(cursor.moveToNext()){
                titre_list.add(cursor.getString(1));
                annee_list.add(cursor.getString(6));
                categorie_list.add(cursor.getString(3));
                description_list.add(cursor.getString(2));
                duree_list.add(cursor.getString(5));
                affiche_list.add(cursor.getString(4));
            }
        }
    }}

