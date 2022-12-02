package com.example.projetv1;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.yalantis.library.Koloda;

import java.util.ArrayList;

public class TirageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private SwipeAdapter adapter;
    private ArrayList<Integer> list;
    Koloda koloda;

    public TirageFragment() {
        // Required empty public constructor
    }

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
        return inflater.inflate(R.layout.fragment_tirage, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.koloda_like);
        view.findViewById(R.id.koloda_dejavu);
        view.findViewById(R.id.koloda_dislike);

        koloda = view.findViewById(R.id.koloda);
        list= new ArrayList<>();
        adapter = new SwipeAdapter(getContext(), list);
        koloda.setLeft(1000091);
        koloda.setRight(1000047);
        koloda.setTop(1000020);
        koloda.setBottom(1000020);

        koloda.setAdapter(adapter);



    }
}