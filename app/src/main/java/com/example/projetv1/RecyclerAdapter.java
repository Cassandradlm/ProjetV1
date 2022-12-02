package com.example.projetv1;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private DBLike dbLike;
    private DBDejaVu dbDejavu;
    private DBDislike dbDislike;
    private DBSuggestion dbSuggestion;
    SQLiteDatabase sqLiteDatabase;
    private Context context;
    private ArrayList titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list, affichenoglide_list;
    boolean activate_like, activate_dislike, activate_dejavu, activate_supprimer;
    String nom_page = MainActivity.getPage();

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;


    public RecyclerAdapter(Context context, ArrayList titre_list, ArrayList annee_list, ArrayList categorie_list, ArrayList description_list, ArrayList duree_list, ArrayList affiche_list, ArrayList affichenoglide_list) {
        this.context = context;
        this.titre_list = titre_list;
        this.annee_list = annee_list;
        this.categorie_list = categorie_list;
        this.description_list = description_list;
        this.duree_list = duree_list;
        this.affiche_list = affiche_list;
        this.affichenoglide_list = affichenoglide_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titre.setText(String.valueOf(titre_list.get(position)));
        holder.annee.setText(String.valueOf(annee_list.get(position)));
        holder.description.setText(String.valueOf(description_list.get(position)));
        holder.duree.setText(String.valueOf(duree_list.get(position)));
        holder.categorie.setText(String.valueOf(categorie_list.get(position)));
        holder.affichenoglide.setText(String.valueOf(affichenoglide_list.get(position)));
        Glide.with(holder.affiche.getContext()).load(affiche_list.get(position)).into(holder.affiche);
    }

    @Override
    public int getItemCount() {
        return titre_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titre, description, annee, duree, categorie, affichenoglide;
        ImageView affiche;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.T_titrerecherche);
            description = itemView.findViewById(R.id.T_descriptionrecherche);
            annee = itemView.findViewById(R.id.T_anneerecherche);
            duree = itemView.findViewById(R.id.T_dureerecherche);
            affiche = itemView.findViewById(R.id.I_filmrecherche);
            categorie = itemView.findViewById(R.id.t_categorierecherche);
            affichenoglide = itemView.findViewById(R.id.T_affichenoglide);

            if (activate_like) {
                itemView.findViewById(R.id.Button_like).setVisibility(View.VISIBLE);
            } else {
                itemView.findViewById(R.id.Button_like).setVisibility(View.GONE);
            }
            if (activate_dislike) {
                itemView.findViewById(R.id.Button_dislike).setVisibility(View.VISIBLE);
            } else {
                itemView.findViewById(R.id.Button_dislike).setVisibility(View.GONE);
            }
            if (activate_dejavu) {
                itemView.findViewById(R.id.Button_dejavu).setVisibility(View.VISIBLE);
            } else {
                itemView.findViewById(R.id.Button_dejavu).setVisibility(View.GONE);
            }
            if (activate_supprimer) {
                itemView.findViewById(R.id.imageButton_supprimer).setVisibility(View.VISIBLE);
            } else {
                itemView.findViewById(R.id.imageButton_supprimer).setVisibility(View.GONE);
            }

            itemView.findViewById(R.id.Button_like).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbLike = new DBLike(view.getContext());
                    sqLiteDatabase = dbLike.getReadableDatabase();
                    String nom = titre.getText().toString();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT Nom from film_like WHERE Nom = '" + nom + "';", null);
                    if(cursor.getCount()>=1){
                        Toast.makeText(view.getContext(), "Le film est déjà présent dans la liste des films likés...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        dbLike.addNewLike(
                                titre.getText().toString(),
                                description.getText().toString(),
                                categorie.getText().toString(),
                                affichenoglide.getText().toString(),
                                duree.getText().toString(),
                                annee.getText().toString(),
                                affichenoglide.getText().toString());
                        if(nom_page == "Films pas aimés"){
                            supprimer(titre_list.get(getLayoutPosition()).toString(), view);
                            titre_list.remove(getLayoutPosition());
                            description_list.remove(getLayoutPosition());
                            categorie_list.remove(getLayoutPosition());
                            affiche_list.remove(getLayoutPosition());
                            duree_list.remove(getLayoutPosition());
                            annee_list.remove(getLayoutPosition());
                            affichenoglide_list.remove(getLayoutPosition());
                            notifyItemRemoved(getLayoutPosition());
                            Toast.makeText(view.getContext(), "Film déplacé dans la liste des films aimés !", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(view.getContext(), "Film ajouté !", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

            itemView.findViewById(R.id.Button_dejavu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbDejavu = new DBDejaVu(view.getContext());
                    sqLiteDatabase = dbDejavu.getReadableDatabase();
                    String nom = titre.getText().toString();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT Nom from film_dejavu WHERE Nom = '" + nom + "';", null);
                    if(cursor.getCount()>=1){
                        Toast.makeText(view.getContext(), "Le film est déjà présent dans la liste des films déjà vus...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        dbDejavu.addNewDejaVu(
                                titre.getText().toString(),
                                description.getText().toString(),
                                categorie.getText().toString(),
                                affichenoglide.getText().toString(),
                                duree.getText().toString(),
                                annee.getText().toString(),
                                affichenoglide.getText().toString());
                        Toast.makeText(view.getContext(), "Film ajouté !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.findViewById(R.id.Button_dislike).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbDislike = new DBDislike(view.getContext());
                    sqLiteDatabase = dbDislike.getReadableDatabase();
                    String nom = titre.getText().toString();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT Nom from film_dislike WHERE Nom = '" + nom + "';", null);
                    if(cursor.getCount()>=1){
                        Toast.makeText(view.getContext(), "Le film est déjà présent dans la liste des films dislikés...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        dbDislike.addNewDislike(
                                titre.getText().toString(),
                                description.getText().toString(),
                                categorie.getText().toString(),
                                affichenoglide.getText().toString(),
                                duree.getText().toString(),
                                annee.getText().toString(),
                                affichenoglide.getText().toString());
                        Toast.makeText(view.getContext(), "Film ajouté !", Toast.LENGTH_SHORT).show();
                        if(nom_page == "Films aimés"){
                            supprimer(titre_list.get(getLayoutPosition()).toString(), view);
                            titre_list.remove(getLayoutPosition());
                            description_list.remove(getLayoutPosition());
                            categorie_list.remove(getLayoutPosition());
                            affiche_list.remove(getLayoutPosition());
                            duree_list.remove(getLayoutPosition());
                            annee_list.remove(getLayoutPosition());
                            affichenoglide_list.remove(getLayoutPosition());
                            notifyItemRemoved(getLayoutPosition());
                            Toast.makeText(view.getContext(), "Film déplacé dans la liste des films pas aimés !", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(view.getContext(), "Film ajouté !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            itemView.findViewById(R.id.imageButton_detail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    afficheDescription(titre.getText().toString(),
                            description.getText().toString(),
                            categorie.getText().toString(),
                            affichenoglide.getText().toString(),
                            duree.getText().toString(),
                            annee.getText().toString());
                }
            });

            itemView.findViewById(R.id.imageButton_supprimer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    supprimer(titre_list.get(getLayoutPosition()).toString(), view);
                    titre_list.remove(getLayoutPosition());
                    description_list.remove(getLayoutPosition());
                    categorie_list.remove(getLayoutPosition());
                    affiche_list.remove(getLayoutPosition());
                    duree_list.remove(getLayoutPosition());
                    annee_list.remove(getLayoutPosition());
                    affichenoglide_list.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    Toast.makeText(view.getContext(), "Film supprimé de la liste", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void afficheDescription(String titre, String description, String categorie,String affiche,String duree,String annee){
        TextView titre_d, categorie_d, description_d, duree_d, annee_d;
        ImageView affiche_d;
        ImageButton bouton_fermer_d;

        dialogBuilder = new AlertDialog.Builder(context);
        final View DescriptionView = LayoutInflater.from(context).inflate(R.layout.fragment_description,null);

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
        Glide.with(context).load(affiche).into(affiche_d);

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


    public void supprimer(String nom_film, View view){
        if(nom_page == "Suggestions"){
            dbSuggestion = new DBSuggestion(view.getContext());
            sqLiteDatabase = dbSuggestion.getReadableDatabase();
            sqLiteDatabase.delete("film_suggestion", "Nom=?", new String[]{nom_film});
        }
        if(nom_page == "Films aimés"){
            dbLike = new DBLike(view.getContext());
            sqLiteDatabase = dbLike.getReadableDatabase();
            sqLiteDatabase.delete("film_like", "Nom=?", new String[]{nom_film});
        }
        if(nom_page == "Films déjà vus"){
            dbDejavu = new DBDejaVu(view.getContext());
            sqLiteDatabase = dbDejavu.getReadableDatabase();
            sqLiteDatabase.delete("film_dejavu", "Nom=?", new String[]{nom_film});
        }
        if(nom_page == "Films pas aimés"){
            dbDislike = new DBDislike(view.getContext());
            sqLiteDatabase = dbDislike.getReadableDatabase();
            sqLiteDatabase.delete("film_dislike", "Nom=?", new String[]{nom_film});
        }
        else {

        }
    }

    public void activate_like(boolean activate_like) {
        this.activate_like = activate_like;
        notifyDataSetChanged(); //need to call it for the child views to be re-created with buttons.
    }
    public void activate_dislike(boolean activate_dislike) {
        this.activate_dislike = activate_dislike;
        notifyDataSetChanged(); //need to call it for the child views to be re-created with buttons.
    }
    public void activate_dejavu(boolean activate_dejavu) {
        this.activate_dejavu = activate_dejavu;
        notifyDataSetChanged(); //need to call it for the child views to be re-created with buttons.
    }
    public void activate_supprimer(boolean activate_supprimer) {
        this.activate_supprimer = activate_supprimer;
        notifyDataSetChanged(); //need to call it for the child views to be re-created with buttons.
    }
}
