package com.example.sae302_application;
import android.content.Context;
import java.io.*;
import java.util.*;
public class DataRepository {
    public static List<Intervention> interventions = new ArrayList<>();
    // Liste statique contenant toutes les interventions chargées en mémoire
    private static final String FILE_NAME = "interventions.csv";
    public static void loadFromCsv(Context context) {
        if (!interventions.isEmpty()) return; // Vérifie si la liste est vide

        File file = new File(context.getFilesDir(), FILE_NAME);
        // Construction d’un objet File pointant vers le fichier CSV dans le stockage interne

        if (!file.exists()) {
            copyAssetsToStorage(context);
            // …on copie la version fournie dans les assets du projet si le fichier n'existe pas
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            // Ouverture d’un flux de lecture pour parcourir le fichier ligne par ligne

            String line; // variable local

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(";");
                // Découpage de la ligne CSV en colonnes séparées par des points-virgules

                int z = 15;
                // Nombre minimal de colonnes attendues dans une ligne valide

                if (parts.length >= z) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        // Conversion de la première colonne en entier (ID)

                        String date = parts[1];
                        // Récupération de la date (nouvel ajout dans ton CSV)

                        String titre = parts[2];
                        String heure = parts[3];
                        int duree = Integer.parseInt(parts[4]);
                        String statut = parts[5];
                        String priorite = parts[6];
                        String desc = parts[7];
                        String mat = parts[8];
                        // Extraction des champs principaux de l’intervention

                        Technicien t = new Technicien(id, parts[9], parts[10]);
                        // Création de l’objet Technicien à partir des colonnes 9 et 10

                        Site s = new Site(id, parts[11], parts[12], parts[13], parts[14]);
                        // Création de l’objet Site à partir des colonnes 11 à 14

                        interventions.add(new Intervention(id, date, titre, heure, duree, statut, priorite, desc, mat, t, s));
                        // Instanciation complète de l’intervention et ajout à la liste

                    } catch (NumberFormatException e) {
                        // Gestion d’une erreur de conversion numérique (ID ou durée invalide)
                        e.printStackTrace();
                    }
                }
            }
            reader.close(); // Fermeture du flux de lecture

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addIntervention(Context context, Intervention i) {
        interventions.add(i);
        // Ajout immédiat dans la liste en mémoire

        // Construction de la ligne CSV correspondant à l’intervention
        String csvLine = i.id + ";" + i.date + ";" + i.titre + ";" + i.heure + ";" + i.dureeMin + ";" +
                i.statut + ";" + i.priorite + ";" + i.description + ";" + i.materiel + ";" +
                i.technicien.nom + ";" + i.technicien.telephone + ";" +
                i.site.nom + ";" + i.site.adresse + ";" +
                i.site.contactNom + ";" + i.site.contactTel;

        try {
            File file = new File(context.getFilesDir(), FILE_NAME);
            // Récupération du fichier CSV dans le stockage interne

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            // Ouverture du fichier en mode "append" (ajout en fin de fichier)

            writer.newLine(); // Ajout d’un saut de ligne avant la nouvelle entrée
            writer.write(csvLine); // Écriture de la nouvelle ligne CSV
            writer.close(); // Fermeture du flux d’écriture

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyAssetsToStorage(Context context) {
        try {
            InputStream in = context.getAssets().open(FILE_NAME);
            // Ouverture du fichier CSV situé dans le dossier assets

            File outFile = new File(context.getFilesDir(), FILE_NAME);
            // Création du fichier de destination dans le stockage interne

            OutputStream out = new FileOutputStream(outFile);
            // Ouverture d’un flux d’écriture vers le fichier de destination

            byte[] buffer = new byte[1024]; // Buffer temporaire pour copier les données par blocs
            int read; // Nombre d’octets lus à chaque itération

            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
                // Copie des octets lus dans le fichier de sortie
            }

            in.close();
            out.close(); // Fermeture des flux pour libérer les ressources

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Intervention getById(int id) { // Recherche d’une intervention par son identifiant
        for (Intervention i : interventions) {
            if (i.id == id) return i;
            // Retour immédiat dès que l’ID correspond
        }
        return null;
    }
    public static void deleteIntervention(Context context, Intervention interventionToDelete) {
        interventions.remove(interventionToDelete);  // Suppression de l’intervention dans la liste en mémoire
        File file = new File(context.getFilesDir(), FILE_NAME); // Récupération du fichier CSV existant

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            // Ouverture du fichier en mode écrasement (false = réécriture complète)

            for (Intervention i : interventions) {
                // Réécriture de chaque intervention encore présente dans la liste

                String csvLine = i.id + ";" + i.date + ";" + i.titre + ";" + i.heure + ";" + i.dureeMin + ";" + i.statut + ";" + i.priorite
                        + ";" + i.description + ";" + i.materiel + ";" + i.technicien.nom + ";" + i.technicien.telephone + ";" + i.site.nom
                        + ";" + i.site.adresse + ";" + i.site.contactNom + ";" + i.site.contactTel;

                writer.write(csvLine); // Écriture de la ligne CSV
                writer.newLine(); // Ajout d’un saut de ligne
            }
            writer.close(); // Fermeture du flux d’écriture

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}