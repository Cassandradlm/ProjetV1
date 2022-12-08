package com.example.projetv1;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    SQLiteDatabase sqLiteDatabase;
    DBAll db;
    DBLike dbLike;
    DBDislike dbDislike;
    DBDejaVu dbDejaVu;
    DBSuggestion dbSuggestion;

    private ArrayList titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list, affichenoglide_list;

    public CardStackAdapter(ArrayList titre_list, ArrayList annee_list, ArrayList categorie_list, ArrayList description_list, ArrayList duree_list, ArrayList affiche_list, ArrayList affichenoglide_list) {
        this.titre_list = titre_list;
        this.annee_list = annee_list;
        this.categorie_list = categorie_list;
        this.description_list = description_list;
        this.duree_list = duree_list;
        this.affiche_list = affiche_list;
        this.affichenoglide_list = affichenoglide_list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView titre, annee, categorie, description, duree, affichenoglide;
        LinearLayout click_titre;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            titre = itemView.findViewById(R.id.item_nom);
            annee = itemView.findViewById(R.id.item_annee);
            categorie = itemView.findViewById(R.id.item_categorie);
            description = itemView.findViewById(R.id.item_description);
            duree = itemView.findViewById(R.id.item_duree);
            affichenoglide = itemView.findViewById(R.id.item_affichenoglide);

            itemView.findViewById(R.id.linearLayout_titre_click).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    afficheDescription(
                            String.valueOf(titre.getText()),
                            String.valueOf(description.getText()),
                            String.valueOf(categorie.getText()),
                            String.valueOf(affichenoglide.getText()),
                            String.valueOf(duree.getText()),
                            String.valueOf(annee.getText()), itemView);
                }
            });


        }
        void setData(String titre_s, String annee_s, String categorie_s, String description_s, String duree_s, String affiche_s, String affichenoglide_s){
            Glide.with(image.getContext()).load(affiche_s).into(image);
            titre.setText(titre_s);
            annee.setText(annee_s);
            categorie.setText(categorie_s);
            description.setText(description_s);
            duree.setText(duree_s);
            affichenoglide.setText(affiche_s);
        }
    }

    @NonNull
    @Override
    public CardStackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardStackAdapter.ViewHolder holder, int position) {
        holder.setData(
                String.valueOf(titre_list.get(position)),
                String.valueOf(annee_list.get(position)),
                String.valueOf(categorie_list.get(position)),
                String.valueOf(description_list.get(position)),
                String.valueOf(duree_list.get(position)),
                String.valueOf(affiche_list.get(position)),
                String.valueOf(affichenoglide_list.get(position)));
    }

    @Override
    public int getItemCount() {
        if(titre_list != null){
            return titre_list.size();
        }
        else{
            return 0;
        }
    }

    public void afficheDescription(String titre, String description, String categorie,String affiche,String duree,String annee, View view){
        TextView titre_d, categorie_d, description_d, duree_d, annee_d;
        ImageView affiche_d;
        ImageButton bouton_fermer_d;

        dialogBuilder = new AlertDialog.Builder(view.getContext());
        final View DescriptionView = LayoutInflater.from(view.getContext()).inflate(R.layout.fragment_description,null);

        titre_d = DescriptionView.findViewById(R.id.T_titrefilmdescrp);
        categorie_d = DescriptionView.findViewById(R.id.T_categoriedescription);
        description_d = DescriptionView.findViewById(R.id.T_resume2description);
        duree_d = DescriptionView.findViewById(R.id.T_dureedescription);
        annee_d = DescriptionView.findViewById(R.id.t_anneedescription);
        affiche_d = DescriptionView.findViewById(R.id.I_filmdescription);
        bouton_fermer_d =  DescriptionView.findViewById(R.id.fermer_description);

        description_d.setMovementMethod(new ScrollingMovementMethod());

        titre_d.setText(titre);
        categorie_d.setText(categorie);
        description_d.setText(description);
        duree_d.setText(duree);
        annee_d.setText(annee);
        Glide.with(view.getContext()).load(affiche).into(affiche_d);

        dialogBuilder.setView(DescriptionView);
        dialog = dialogBuilder.create();
        dialog.show();

        bouton_fermer_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void ajouter_film_aime(View view, int pos) {
        dbLike = new DBLike(view.getContext());
        sqLiteDatabase = dbLike.getReadableDatabase();

        Log.d("AJOUT","AJOUTT");

        String nom = titre_list.get(pos).toString().replace("'", "''");
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT Nom from film_like WHERE Nom = '" + nom + "';", null);
        if(cursor.getCount()>=1){

        }
        else{
            dbLike.addNewLike(
                    titre_list.get(pos).toString(),
                    description_list.get(pos).toString(),
                    categorie_list.get(pos).toString(),
                    affichenoglide_list.get(pos).toString(),
                    duree_list.get(pos).toString(),
                    annee_list.get(pos).toString(),
                    affichenoglide_list.get(pos).toString());
        }

    }

    public void ajouter_film_pas_aime(View view, int pos) {
        dbDislike = new DBDislike(view.getContext());
        sqLiteDatabase = dbDislike.getReadableDatabase();

        String nom = titre_list.get(pos).toString().replace("'","''");
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT Nom from film_dislike WHERE Nom = '" + nom + "';", null);
        if(cursor.getCount()>=1){

        }
        else{
            dbDislike.addNewDislike(
                    titre_list.get(pos).toString(),
                    description_list.get(pos).toString(),
                    categorie_list.get(pos).toString(),
                    affichenoglide_list.get(pos).toString(),
                    duree_list.get(pos).toString(),
                    annee_list.get(pos).toString(),
                    affichenoglide_list.get(pos).toString());
        }
    }

    public void ajouter_film_vu(View view, int pos){

        dbDejaVu = new DBDejaVu(view.getContext());
        sqLiteDatabase = dbDejaVu.getReadableDatabase();

        String nom = titre_list.get(pos).toString().replace("'","''");
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT Nom from film_dejavu WHERE Nom = '" + nom + "';", null);
        if(cursor.getCount()>=1){

        }
        else{
            dbDejaVu.addNewDejaVu(
                    titre_list.get(pos).toString(),
                    description_list.get(pos).toString(),
                    categorie_list.get(pos).toString(),
                    affichenoglide_list.get(pos).toString(),
                    duree_list.get(pos).toString(),
                    annee_list.get(pos).toString(),
                    affichenoglide_list.get(pos).toString());
        }
    }

    public void supprimer(View view, int pos){
        String nom_film = titre_list.get(pos).toString();
        dbSuggestion = new DBSuggestion(view.getContext());
        sqLiteDatabase = dbSuggestion.getReadableDatabase();
        sqLiteDatabase.delete("film_suggestion", "Nom=?", new String[]{nom_film});
        titre_list.remove(pos);
        description_list.remove(pos);
        categorie_list.remove(pos);
        affiche_list.remove(pos);
        duree_list.remove(pos);
        annee_list.remove(pos);
        affichenoglide_list.remove(pos);
        notifyDataSetChanged();
    }

    public void pass(View view, int pos){
        titre_list.remove(pos);
        description_list.remove(pos);
        categorie_list.remove(pos);
        affiche_list.remove(pos);
        duree_list.remove(pos);
        annee_list.remove(pos);
        affichenoglide_list.remove(pos);
        notifyDataSetChanged();
    }
}