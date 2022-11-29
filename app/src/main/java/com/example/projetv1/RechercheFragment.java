package com.example.projetv1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class RechercheFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<String> titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list;
    SQLiteDatabase sqLiteDatabase;
    DBHandler db;

    public RechercheFragment() { }

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

        return inflater.inflate(R.layout.fragment_recherche, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        SearchView searchView = view.findViewById(R.id.searchView);

        displaydata("");
        recyclerView = view.findViewById(R.id.listfilm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        Adapter adapter = new Adapter(getContext(), titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                displaydata(newText);
                Adapter adapter = new Adapter(getContext(), titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    private void displaydata(String search) {
        //Cursor cursor = db.getdata();
        db=new DBHandler(getContext());

        sqLiteDatabase = db.getReadableDatabase();
        String query;
        if(search ==""){
            query = "SELECT * FROM mycourses ;";
        }
        else{
            query = "SELECT * FROM mycourses"+
                    " WHERE Nom LIKE '%" + search + "%';";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Log.d("CURSOR",String.valueOf(cursor.getCount()));

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
    }

}