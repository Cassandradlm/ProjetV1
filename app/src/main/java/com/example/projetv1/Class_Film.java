package com.example.projetv1;

public class Class_Film {
    private String Nom, Description, Categorie, Affiche;
    private int Duree, Annee;

    public Class_Film() {

    }

    public Class_Film(String Nom, String Description, String Categorie, String Affiche, int Duree, int Annee) {
        this.Nom = Nom;
        this.Description = Description;
        this.Categorie = Categorie;
        this.Affiche = Affiche;
        this.Duree = Duree;
        this.Annee = Annee;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String Categorie) {
        this.Categorie = Categorie;
    }

    public String getAffiche() {
        return Affiche;
    }

    public void setAffiche(String Affiche) {
        this.Affiche = Affiche;
    }

    public int getDuree() {
        return Duree;
    }

    public void setDuree(int Duree) {
        this.Duree = Duree;
    }

    public int getAnnee() {
        return Annee;
    }

    public void setAnnee(int Annee) {
        this.Annee = Annee;
    }
}
