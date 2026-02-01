package com.example.sae302_application;
import android.content.Context;
import java.io.*;
import java.util.*;

public class DataRepository {

    public static List<Intervention> interventions = new ArrayList<>();
    private static final String FILE_NAME = "interventions.csv";

    public static void loadFromCsv(Context context) {
        if (!interventions.isEmpty()) return;

        File file = new File(context.getFilesDir(), FILE_NAME);
        if (!file.exists()) {
            copyAssetsToStorage(context);
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 15) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String date = parts[1]; // <--- NOUVEAU

                        String titre = parts[2];
                        String heure = parts[3];
                        int duree = Integer.parseInt(parts[4]);
                        String statut = parts[5];
                        String priorite = parts[6];
                        String desc = parts[7];
                        String mat = parts[8];

                        Technicien t = new Technicien(id, parts[9], parts[10]);
                        Site s = new Site(id, parts[11], parts[12], parts[13], parts[14]);

                        interventions.add(new Intervention(
                                id, date, titre, heure, duree, statut, priorite, desc, mat, t, s
                        ));
                    } catch (NumberFormatException e) {
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addIntervention(Context context, Intervention i) {
        interventions.add(i);

        // Ajout de la date dans la ligne de sauvegarde
        String csvLine = i.id + ";" + i.date + ";" + i.titre + ";" + i.heure + ";" + i.dureeMin + ";" +
                i.statut + ";" + i.priorite + ";" + i.description + ";" + i.materiel + ";" +
                i.technicien.nom + ";" + i.technicien.telephone + ";" +
                i.site.nom + ";" + i.site.adresse + ";" +
                i.site.contactNom + ";" + i.site.contactTel;

        try {
            File file = new File(context.getFilesDir(), FILE_NAME);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.newLine();
            writer.write(csvLine);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyAssetsToStorage(Context context) {
        try {
            InputStream in = context.getAssets().open(FILE_NAME);
            File outFile = new File(context.getFilesDir(), FILE_NAME);
            OutputStream out = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) { out.write(buffer, 0, read); }
            in.close();
            out.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static Intervention getById(int id) {
        for (Intervention i : interventions) { if (i.id == id) return i; }
        return null;
    }
    // Nouvelle méthode pour supprimer et sauvegarder
    public static void deleteIntervention(Context context, Intervention interventionToDelete) {
        interventions.remove(interventionToDelete);

        File file = new File(context.getFilesDir(), FILE_NAME);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

            for (Intervention i : interventions) {
                String csvLine = i.id + ";" + i.date + ";" + i.titre + ";" + i.heure + ";" + i.dureeMin + ";" +
                        i.statut + ";" + i.priorite + ";" + i.description + ";" + i.materiel + ";" +
                        i.technicien.nom + ";" + i.technicien.telephone + ";" +
                        i.site.nom + ";" + i.site.adresse + ";" +
                        i.site.contactNom + ";" + i.site.contactTel;

                writer.write(csvLine);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}