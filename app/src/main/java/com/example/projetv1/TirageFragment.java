package com.example.projetv1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.yalantis.library.Koloda;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;

public class TirageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private int pos;

    ArrayList<String> titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list, affichenoglide_list;
    SQLiteDatabase sqLiteDatabase;
    DBSuggestion dbSuggestion;

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

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
        addList();
        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {

            }

            @Override
            public void onCardSwiped(Direction direction) {
                if (direction == Direction.Right){
                    adapter.ajouter_film_aime(view, pos);
                    adapter.supprimer(view, pos);
                }
                if (direction == Direction.Top){
                    adapter.pass(view, pos);
                }
                if (direction == Direction.Left){
                    adapter.ajouter_film_pas_aime(view, pos);
                    adapter.supprimer(view, pos);
                }
                if (direction == Direction.Bottom){
                    adapter.ajouter_film_vu(view, pos);
                    adapter.supprimer(view, pos);
                }
            }

            @Override
            public void onCardRewound() {

            }

            @Override
            public void onCardCanceled() {

            }

            @Override
            public void onCardAppeared(View view, int position) {

            }

            @Override
            public void onCardDisappeared(View view, int position) {
                pos = position;
            }
        });
        manager.setStackFrom(StackFrom.None);
        if (titre_list != null){
            manager.setVisibleCount(titre_list.size());
        }
        else{
            manager.setVisibleCount(3);
        }
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list, affichenoglide_list);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void addList() {
        dbSuggestion = new DBSuggestion(getContext());

        sqLiteDatabase = dbSuggestion.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from film_suggestion ORDER BY RANDOM()", null);

        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "Il n'y a plus de films à suggérer", Toast.LENGTH_SHORT).show();
            return;
        } else {
            titre_list = new ArrayList<String>();
            annee_list = new ArrayList<String>();
            categorie_list = new ArrayList<String>();
            description_list = new ArrayList<String>();
            duree_list = new ArrayList<String>();
            affiche_list = new ArrayList<String>();
            affichenoglide_list = new ArrayList<>();

            while (cursor.moveToNext()) {
                titre_list.add(cursor.getString(1));
                annee_list.add(cursor.getString(6));
                categorie_list.add(cursor.getString(3));
                description_list.add(cursor.getString(2));
                duree_list.add(cursor.getString(5));
                affiche_list.add(cursor.getString(4));
                affichenoglide_list.add(cursor.getString(7));
            }
        }
    }
}