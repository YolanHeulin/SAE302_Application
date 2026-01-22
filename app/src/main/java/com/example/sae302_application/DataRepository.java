package com.example.sae302_application;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    public static List<Intervention> interventions = new ArrayList<>();

    public static void loadFromCsv(Context context) {
        interventions.clear();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("interventions.csv"))
            );
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 14) {
                    // Création des objets temporaires (Technicien, Site...)
                    Technicien t = new Technicien(Integer.parseInt(parts[0]), parts[8], parts[9]);
                    Site s = new Site(Integer.parseInt(parts[0]), parts[10], parts[11], parts[12], parts[13]);

                    interventions.add(new Intervention(
                            Integer.parseInt(parts[0]), parts[1], parts[2],
                            Integer.parseInt(parts[3]), parts[4], parts[5],
                            parts[6], parts[7], t, s
                    ));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Intervention getById(int id) {
        for (Intervention i : interventions) {
            if (i.id == id) return i;
        }
        return null;
    }
}