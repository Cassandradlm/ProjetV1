package com.example.projetv1;

public class Film {
    private String Nom, Description, Categorie, Affiche;
    private int Duree, Annee;

    public Film() {

    }

    public Film(String nom, String description, String categorie, String affiche, int duree, int annee) {
        Nom = nom;
        Description = description;
        Categorie = categorie;
        Affiche = affiche;
        Duree = duree;
        Annee = annee;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public String getAffiche() {
        return Affiche;
    }

    public void setAffiche(String affiche) {
        Affiche = affiche;
    }

    public int getDuree() {
        return Duree;
    }

    public void setDuree(int duree) {
        Duree = duree;
    }

    public int getAnnee() {
        return Annee;
    }

    public void setAnnee(int annee) {
        Annee = annee;
    }
}
