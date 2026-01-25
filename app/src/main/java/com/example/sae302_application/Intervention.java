package com.example.sae302_application;

import java.io.Serializable;

public class Intervention implements Serializable {
    public int id;
    public String date; // <--- NOUVEAU CHAMP
    public String titre;
    public String heure;
    public int dureeMin;
    public String statut;
    public String priorite;
    public String description;
    public String materiel;
    public Technicien technicien;
    public Site site;

    public Intervention(int id, String date, String titre, String heure, int dureeMin, String statut, String priorite, String description, String materiel, Technicien technicien, Site site) {
        this.id = id;
        this.date = date; // <--- AJOUT
        this.titre = titre;
        this.heure = heure;
        this.dureeMin = dureeMin;
        this.statut = statut;
        this.priorite = priorite;
        this.description = description;
        this.materiel = materiel;
        this.technicien = technicien;
        this.site = site;
    }

    public int getPrioriteValue() {
        if ("Haute".equals(priorite)) return 3;
        if ("Moyenne".equals(priorite)) return 2;
        return 1;
    }
}