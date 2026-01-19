package com.example.sae302_application;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    public static List<Intervention> interventions = new ArrayList<>();

    static {
        // 1. Création des techniciens avec téléphone
        Technicien t1 = new Technicien(1, "Thomas Durand", "06 12 34 56 78");
        Technicien t2 = new Technicien(2, "Sarah Connor", "07 98 76 54 32");

        // 2. Création des sites avec adresse et contact complet
        Site s1 = new Site(1, "Bijouterie Or & Éclat", "12 Rue de la Paix, 75000 Paris", "M. Gold", "01 45 00 01 02");
        Site s2 = new Site(2, "Entrepôt Logistique Sud", "Zone Industrielle Nord, Hangar 4", "Mme. Stock", "04 67 00 00 00");
        Site s3 = new Site(3, "Maison M. Martin", "4 Allée des Lilas, Quartier Résidentiel", "M. Martin", "06 11 22 33 44");

        // 3. Création des interventions
        interventions.add(new Intervention(1, "Sabotage Détecteur", "08:00 - 09:30", 90, "En cours", "Haute", "Contact porte arrière arraché.", "Kit contacts, visseuse", t2, s1));
        interventions.add(new Intervention(2, "Batteries Centrale", "10:00 - 11:00", 60, "Planifiée", "Moyenne", "Batterie faible signalée.", "Batterie 12V 7Ah", t2, s3));
        interventions.add(new Intervention(3, "Installation Caméras", "13:00 - 17:00", 240, "Planifiée", "Haute", "Pose de 4 caméras IP.", "Caméras, Câble RJ45", t1, s2));
        interventions.add(new Intervention(4, "Réglage Sensibilité", "17:30 - 18:30", 60, "Terminée", "Basse", "Faux positifs animaux.", "PC Portable", t1, s3));
    }

    public static Intervention getById(int id) {
        for (Intervention i : interventions) {
            if (i.id == id) return i;
        }
        return null;
    }
}