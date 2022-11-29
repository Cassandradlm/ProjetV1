package com.example.projetv1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private DBLike dbLike;
    private DBDejaVu dbDejavu;
    private DBDislike dbDislike;
    SQLiteDatabase sqLiteDatabase;
    private Context context;
    private ArrayList titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list, affichenoglide_list;
    boolean activate_like, activate_dislike, activate_dejavu;

    public Adapter(Context context, ArrayList titre_list, ArrayList annee_list, ArrayList categorie_list,ArrayList description_list, ArrayList duree_list, ArrayList affiche_list, ArrayList affichenoglide_list) {
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
                itemView.findViewById(R.id.Button_like).setVisibility(View.INVISIBLE);
            }
            if (activate_dislike) {
                itemView.findViewById(R.id.Button_dislike).setVisibility(View.VISIBLE);
            } else {
                itemView.findViewById(R.id.Button_dislike).setVisibility(View.INVISIBLE);
            }
            if (activate_dejavu) {
                itemView.findViewById(R.id.Button_dejavu).setVisibility(View.VISIBLE);
            } else {
                itemView.findViewById(R.id.Button_dejavu).setVisibility(View.INVISIBLE);
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
                        Toast.makeText(view.getContext(), "Film ajouté !", Toast.LENGTH_SHORT).show();
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
                    }
                }
            });
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
}
