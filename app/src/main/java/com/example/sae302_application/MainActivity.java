package com.example.sae302_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private InterventionAdapter adapter;
    private TextView tvStats;
    private String currentFilter = "Toutes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataRepository.loadFromCsv(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        tvStats = findViewById(R.id.tvStatsTotal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new InterventionAdapter(new ArrayList<>(), item -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("ID", item.id);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Création des écouteurs
        findViewById(R.id.btnAll).setOnClickListener(v -> { currentFilter = "Toutes"; loadData(); });
        findViewById(R.id.btnPlan).setOnClickListener(v -> { currentFilter = "Planifiée"; loadData(); });
        findViewById(R.id.btnEnc).setOnClickListener(v -> { currentFilter = "En cours"; loadData(); });
        findViewById(R.id.btnTerm).setOnClickListener(v -> { currentFilter = "Terminée"; loadData(); });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    private void loadData() {
        List<Intervention> all = DataRepository.interventions;
        List<Intervention> filtered = new ArrayList<>();

        for (Intervention i : all) {
            if (currentFilter.equals("Toutes") || i.statut.equals(currentFilter)) {
                filtered.add(i);
            }
        }

        // Tri par Priorité
        Collections.sort(filtered, new Comparator<Intervention>() {
            @Override
            public int compare(Intervention o1, Intervention o2) {
                return Integer.compare(o2.getPrioriteValue(), o1.getPrioriteValue());
            }
        });

        if (adapter != null) adapter.updateList(filtered);
        if (tvStats != null) tvStats.setText(filtered.size() + " intervention(s)");
    }
}