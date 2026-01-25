package com.example.sae302_application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import java.util.*;

public class MainActivity extends AppCompatActivity
        implements InterventionAdapter.OnItemClickListener {
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

        adapter = new InterventionAdapter(new ArrayList<Intervention>(), this);
        recyclerView.setAdapter(adapter);

        // Bouton 1
        findViewById(R.id.btnAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = "Toutes";
                loadData();
            }
        });
        // Bouton 2
        findViewById(R.id.btnPlan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = "Planifiée";
                loadData();
            }
        });
        // Bouton 3
        findViewById(R.id.btnEnc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = "En cours";
                loadData();
            }
        });
        // Bouton 4
        findViewById(R.id.btnTerm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = "Terminée";
                loadData();
            }
        });
        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AjoutIntervention.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    @Override
    public void onItemClick(Intervention item) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("ID", item.id);
        startActivity(intent);
    }
    private void loadData() {
        List<Intervention> all = DataRepository.interventions;
        List<Intervention> filtered = new ArrayList<Intervention>();

        for (Intervention i : all) {
            if (currentFilter.equals("Toutes") || i.statut.equals(currentFilter)) {
                filtered.add(i);
            }
        }
        // Tri par priorité
        Collections.sort(filtered, new Comparator<Intervention>() {
            @Override
            public int compare(Intervention o1, Intervention o2) {
                return Integer.compare(
                        o2.getPrioriteValue(),
                        o1.getPrioriteValue()
                );
            }
        });

        if (adapter != null) {
            adapter.updateList(filtered);
        }
        if (tvStats != null) {
            tvStats.setText(filtered.size() + " intervention(s)");
        }
    }
}