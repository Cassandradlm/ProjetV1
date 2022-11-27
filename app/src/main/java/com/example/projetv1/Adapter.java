package com.example.projetv1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Adapter extends FirestoreRecyclerAdapter<Film,Adapter.ViewHolder> {

    public Adapter(@NonNull FirestoreRecyclerOptions<Film> options) {super(options); }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Film model) {
        holder.titre.setText(model.getNom());
        holder.annee.setText(String.valueOf(model.getAnnee()));
        holder.description.setText(model.getDescription());
        holder.duree.setText(String.valueOf(model.getDuree())+" min");
        Glide.with(holder.affiche.getContext()).load(model.getAffiche()).into(holder.affiche);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

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
