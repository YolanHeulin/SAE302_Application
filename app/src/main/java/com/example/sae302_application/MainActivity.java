package com.example.sae302_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private InterventionAdapter adapter;
    private TextView tvStats;
    private String currentFilter = "Toutes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. On charge le design XML corrigé
        setContentView(R.layout.activity_main);

        // --- SUPPRESSION DU BLOC "ViewCompat" QUI POSAIT PROBLÈME ---
        // Nous passons directement à la logique de l'application

        // 2. Récupération des éléments de l'écran
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        tvStats = findViewById(R.id.tvStatsTotal);

        // 3. Configuration de la liste (RecyclerView)
        // C'est ici qu'on évite le crash si la liste est vide au départ
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new InterventionAdapter(new ArrayList<>(), item -> {
            // Action au clic sur une carte : Ouvrir les détails
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("ID", item.id);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // 4. Configuration des boutons de filtre
        setupFilters();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Recharger les données quand on revient sur l'écran
    }

    private void setupFilters() {
        // On attache les actions aux boutons
        // Note: Si ces IDs n'existent pas dans le XML, l'app plantera.
        // Mais ils sont présents dans le code XML que je vous ai donné précédemment.
        findViewById(R.id.btnAll).setOnClickListener(v -> { currentFilter = "Toutes"; loadData(); });
        findViewById(R.id.btnPlan).setOnClickListener(v -> { currentFilter = "Planifiée"; loadData(); });
        findViewById(R.id.btnEnc).setOnClickListener(v -> { currentFilter = "En cours"; loadData(); });
        findViewById(R.id.btnTerm).setOnClickListener(v -> { currentFilter = "Terminée"; loadData(); });
    }

    private void loadData() {
        // Chargement et tri des données
        List<Intervention> all = DataRepository.interventions;
        List<Intervention> filtered = new ArrayList<>();

        for (Intervention i : all) {
            if (currentFilter.equals("Toutes") || i.statut.equals(currentFilter)) {
                filtered.add(i);
            }
        }

        // Tri par priorité
        Collections.sort(filtered, new Comparator<Intervention>() {
            @Override
            public int compare(Intervention o1, Intervention o2) {
                return Integer.compare(o2.getPrioriteValue(), o1.getPrioriteValue());
            }
        });

        // Mise à jour de l'affichage
        if (adapter != null) {
            adapter.updateList(filtered);
        }

        if (tvStats != null) {
            tvStats.setText(filtered.size() + " intervention(s)");
        }
    }
}