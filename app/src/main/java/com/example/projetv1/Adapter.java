package com.example.projetv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private ArrayList titre_list, annee_list, categorie_list, description_list, duree_list, affiche_list;

    public Adapter(Context context, ArrayList titre_list, ArrayList annee_list, ArrayList categorie_list,ArrayList description_list, ArrayList duree_list, ArrayList affiche_list) {
        this.context = context;
        this.titre_list = titre_list;
        this.annee_list = annee_list;
        this.categorie_list = categorie_list;
        this.description_list = description_list;
        this.duree_list = duree_list;
        this.affiche_list = affiche_list;
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
        holder.duree.setText(String.valueOf(duree_list.get(position))+" min");
        Glide.with(holder.affiche.getContext()).load(affiche_list.get(position)).into(holder.affiche);
    }

    @Override
    public int getItemCount() {
        return titre_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titre, description, annee, duree;
        ImageView affiche;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.T_titrerecherche);
            description = itemView.findViewById(R.id.T_descriptionrecherche);
            annee = itemView.findViewById(R.id.T_anneerecherche);
            duree = itemView.findViewById(R.id.T_dureerecherche);
            affiche = itemView.findViewById(R.id.I_filmrecherche);
        }
    }



}
