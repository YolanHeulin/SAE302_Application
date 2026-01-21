package com.example.sae302_application;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    public static List<Intervention> interventions = new ArrayList<>();

    public static void loadFromCsv(Context context) {

    }

    // Méthode de recherche basique
    public static Intervention getById(int id) {
        return null;
    }
}